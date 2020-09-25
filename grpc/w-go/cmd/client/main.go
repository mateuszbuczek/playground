package main

import (
	"bufio"
	"context"
	"flag"
	"github.com/mateuszbuczek/playground/grpc/w-go/pb"
	"github.com/mateuszbuczek/playground/grpc/w-go/utils"
	"google.golang.org/grpc"
	"io"
	"log"
	"os"
	"path/filepath"
	"time"
)

func main() {
	serverAddress := flag.String("address", "", "the server address")
	flag.Parse()
	log.Printf("dial server %s", *serverAddress)

	conn, err := grpc.Dial(*serverAddress, grpc.WithInsecure())
	if err != nil {
		log.Fatal(err)
	}

	laptopServiceClient := pb.NewLaptopServiceClient(conn)
	testUploadImage(laptopServiceClient)
}

func testUploadImage(laptopClient pb.LaptopServiceClient) {
	laptop := utils.NewLaptop()
	createLaptop(laptopClient, laptop)
	uploadImage(laptopClient, laptop.Id, "tmp/test_image.jpg")
}

func testSearchLaptop(laptopServiceClient pb.LaptopServiceClient) {
	for i := 0; i < 10; i++ {
		createLaptop(laptopServiceClient, utils.NewLaptop())
	}

	searchLaptop(laptopServiceClient, &pb.Filter{
		MaxPriceUsd: 400,
		MinCpuCores: 2,
		MinCpuGhz:   2,
		MinRam:      nil,
	})
}

func testCreateLaptop(laptopServiceClient pb.LaptopServiceClient) {
	createLaptop(laptopServiceClient, utils.NewLaptop())
}

func createLaptop(laptopServiceClient pb.LaptopServiceClient, laptop *pb.Laptop) {
	request := &pb.CreateLaptopRequest{Laptop: laptop}

	ctx, cancel := context.WithTimeout(context.Background(), 5*time.Second)
	defer cancel()

	response, err := laptopServiceClient.CreateLaptop(ctx, request)
	if err != nil {
		log.Fatal(err)
	}

	log.Printf("laptop created with id: %s", response.Id)
}

func searchLaptop(laptopClient pb.LaptopServiceClient, filter *pb.Filter) {
	log.Print("search filter: ", filter)

	ctx, cancelFunc := context.WithTimeout(context.Background(), 5*time.Second)
	defer cancelFunc()

	request := &pb.SearchLaptopRequest{Filter: filter}
	stream, err := laptopClient.SearchLaptop(ctx, request)
	if err != nil {
		log.Fatal("cannot search laptop: ", err)
	}

	for {
		res, err := stream.Recv()
		if err == io.EOF {
			return
		}
		if err != nil {
			log.Fatal("cannot reveice response: ", err)
		}

		laptop := res.GetLaptop()
		log.Printf("got laptop: %+v", laptop)
	}
}

func uploadImage(laptopClient pb.LaptopServiceClient, laptopId string, imagePath string) {
	file, err := os.Open(imagePath)
	if err != nil {
		log.Fatal("cannot open image file: ", err)
	}
	defer file.Close()

	ctx, cancel := context.WithTimeout(context.Background(), 5*time.Second)
	defer cancel()

	stream, err := laptopClient.UploadImage(ctx)
	if err != nil {
		log.Fatal("cannot upload image: ", err)
	}

	req := &pb.UploadImageRequest{Data: &pb.UploadImageRequest_Info{Info: &pb.ImageInfo{
		LaptopId:  laptopId,
		ImageType: filepath.Ext(imagePath),
	}}}

	err = stream.Send(req)
	if err != nil {
		log.Fatal("cannot send image info: ", err, stream.RecvMsg(nil))
	}

	reader := bufio.NewReader(file)
	buffer := make([]byte, 1024)

	for {
		n, err := reader.Read(buffer)
		if err == io.EOF {
			break
		}
		if err != nil {
			log.Fatal("cannot read chunk of data to buffer: ", err)
		}

		req := &pb.UploadImageRequest{
			Data: &pb.UploadImageRequest_ChunkData{
				ChunkData: buffer[:n],
			}}

		err = stream.Send(req)
		if err != nil {
			log.Fatal("canno send chunk to server: ", err, stream.RecvMsg(nil))
		}
	}

	res, err := stream.CloseAndRecv()
	if err != nil {
		log.Fatal("cannot receive response: ", err)
	}

	log.Printf("image uploaded with od: %s, size: %d", res.GetId(), res.GetSize())
}
