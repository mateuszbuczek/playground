package main

import (
	"context"
	"flag"
	"github.com/mateuszbuczek/playground/grpc/w-go/pb"
	"github.com/mateuszbuczek/playground/grpc/w-go/utils"
	"google.golang.org/grpc"
	"io"
	"log"
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

	for i := 0; i < 10; i++ {
		createLaptop(laptopServiceClient)
	}

	searchLaptop(laptopServiceClient, &pb.Filter{
		MaxPriceUsd: 400,
		MinCpuCores: 2,
		MinCpuGhz:   2,
		MinRam:      nil,
	})
}

func createLaptop(laptopServiceClient pb.LaptopServiceClient) {
	laptop := utils.NewLaptop()
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
