package service

import (
	"context"
	"github.com/google/uuid"
	"github.com/mateuszbuczek/playground/grpc/w-go/pb"
	"google.golang.org/grpc/codes"
	"google.golang.org/grpc/status"
	"log"
	"time"
)

type LaptopServer struct {
	Store LaptopStore
}

func NewLaptopServer(store LaptopStore) *LaptopServer {
	return &LaptopServer{store}
}

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
	time.Sleep(6 * time.Second)

	if ctx.Err() == context.Canceled {
		log.Print("client has cancelled request")
		return nil, status.Error(codes.Canceled, "interrupted")
	}

	if ctx.Err() == context.DeadlineExceeded {
		log.Print("client context deadline exceeded, stop processing")
		return nil, status.Error(codes.DeadlineExceeded, "deadline exceeded")
	}

	// save to in-memory storage
	err := server.Store.Save(laptop)
	if err != nil {
		return nil, status.Errorf(codes.Internal, "cannot save laptop to the store: %v", err)
	}

	log.Printf("saved laptop with id: %s", laptop.Id)
	res := &pb.CreateLaptopResponse{Id: laptop.Id}
	return res, nil
}
