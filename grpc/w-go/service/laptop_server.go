package service

import (
	"bytes"
	"context"
	"github.com/google/uuid"
	"github.com/mateuszbuczek/playground/grpc/w-go/pb"
	"google.golang.org/grpc/codes"
	"google.golang.org/grpc/status"
	"io"
	"log"
)

type LaptopServer struct {
	LaptopStore LaptopStore
	imageStore  ImageStore
	ratingStore RatingStore
}

//max 1 MB
const maxImageSize = 1 << 20

func NewLaptopServer(laptopStore LaptopStore, imageStore ImageStore, ratingStore RatingStore) *LaptopServer {
	return &LaptopServer{
		LaptopStore: laptopStore,
		imageStore:  imageStore,
		ratingStore: ratingStore,
	}
}

// unary
func (server *LaptopServer) CreateLaptop(ctx context.Context, req *pb.CreateLaptopRequest) (*pb.CreateLaptopResponse, error) {
	laptop := req.GetLaptop()
	if len(laptop.Id) > 0 {
		_, err := uuid.Parse(laptop.Id)
		if err != nil {
			return nil, status.Errorf(codes.InvalidArgument, "laptop ID is not a valid UUID: %v", err)
		}
	} else {
		id, err := uuid.NewRandom()
		if err != nil {
			return nil, status.Errorf(codes.Internal, "cannot generate a new laptop ID: %v", err)
		}
		laptop.Id = id.String()
	}

	// some heavy processing
	//time.Sleep(6 * time.Second)

	if ctx.Err() == context.Canceled {
		log.Print("client has cancelled request")
		return nil, status.Error(codes.Canceled, "interrupted")
	}

	if ctx.Err() == context.DeadlineExceeded {
		log.Print("client context deadline exceeded, stop processing")
		return nil, status.Error(codes.DeadlineExceeded, "deadline exceeded")
	}

	// save to in-memory storage
	err := server.LaptopStore.Save(laptop)
	if err != nil {
		return nil, status.Errorf(codes.Internal, "cannot save laptop to the store: %v", err)
	}

	log.Printf("saved laptop with id: %s", laptop.Id)
	res := &pb.CreateLaptopResponse{Id: laptop.Id}
	return res, nil
}

// server streaming
func (server *LaptopServer) SearchLaptop(req *pb.SearchLaptopRequest, stream pb.LaptopService_SearchLaptopServer) error {
	filter := req.GetFilter()
	err := server.LaptopStore.Search(filter, func(laptop *pb.Laptop) error {
		res := &pb.SearchLaptopResponse{Laptop: laptop}

		err := stream.Send(res)
		if err != nil {
			return err
		}

		log.Printf("sent laptop with id: %s", laptop.GetId())
		return nil
	})

	if err != nil {
		return status.Errorf(codes.Internal, "unexpected error: %v", err)
	}

	return nil
}

// client streaming
func (server *LaptopServer) UploadImage(stream pb.LaptopService_UploadImageServer) error {
	req, err := stream.Recv()
	if err != nil {
		return logError(status.Errorf(codes.Unknown, "cannot receive image info"))
	}

	laptopId := req.GetInfo().GetLaptopId()
	imageType := req.GetInfo().GetImageType()
	log.Printf("receive an upload-image request for laptop %s with image type %s", laptopId, imageType)

	foundLaptop, err := server.LaptopStore.Find(laptopId)
	if err != nil {
		return logError(status.Errorf(codes.Internal, "cannot find laptop: %v", err))
	}
	if foundLaptop == nil {
		return logError(status.Errorf(codes.InvalidArgument, "laptop %s does not exist", laptopId))
	}

	imageData := bytes.Buffer{}
	imageSize := 0

	for {
		log.Print("waiting to receive more data")

		req, err := stream.Recv()
		if err == io.EOF {
			log.Print("no more data")
			break
		}

		if err != nil {
			return logError(status.Errorf(codes.Unknown, "cannot receive chunk data: %v", err))
		}

		chunk := req.GetChunkData()
		size := len(chunk)

		log.Printf("received a chunk with size: %d", size)

		imageSize += size
		if imageSize > maxImageSize {
			return logError(status.Errorf(codes.InvalidArgument, "image is too large"))
		}

		imageData.Write(chunk)
	}

	imageId, err := server.imageStore.Save(laptopId, imageType, imageData)
	if err != nil {
		return logError(status.Errorf(codes.Internal, "cannot save image to the store: %v", err))
	}
	err = stream.SendAndClose(&pb.UploadImageResponse{
		Id:   imageId,
		Size: uint32(imageSize),
	})
	if err != nil {
		return logError(status.Errorf(codes.Internal, "cannot close request"))
	}

	return nil
}

//bidirectional streaming
func (server *LaptopServer) RateLaptop(stream pb.LaptopService_RateLaptopServer) error {
	for {
		if stream.Context().Err() != nil {
			return logError(stream.Context().Err())
		}

		req, err := stream.Recv()
		if err == io.EOF {
			log.Print("no more data")
			break
		}

		if err != nil {
			return logError(status.Errorf(codes.Unknown, "cannot receive stream request: %v", err))
		}

		laptopId := req.GetLaptopId()
		score := float64(req.GetScore())
		log.Printf("received a rate laptop request: id %s, score %.2f", laptopId, score)
		foundLaptop, err := server.LaptopStore.Find(laptopId)
		if err != nil {
			return logError(status.Errorf(codes.Internal, "cannot find laptop: %v", err))
		}

		if foundLaptop == nil {
			{
				return logError(status.Errorf(codes.NotFound, "laptopId %s is not found", laptopId))
			}
		}

		rating, _ := server.ratingStore.Add(laptopId, score)
		response := &pb.RateLaptopResponse{
			LaptopId:     laptopId,
			RatedCount:   rating.Count,
			AverageScore: rating.Sum / float64(rating.Count),
		}

		err = stream.Send(response)
		if err != nil {
			return logError(status.Errorf(codes.NotFound, "cannot send stream response", laptopId))
		}
	}

	return nil
}

func logError(err error) error {
	if err != nil {
		log.Print(err)
	}

	return err
}
