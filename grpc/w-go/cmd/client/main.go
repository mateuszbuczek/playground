package main

import (
	"context"
	"flag"
	"github.com/mateuszbuczek/playground/grpc/w-go/pb"
	"github.com/mateuszbuczek/playground/grpc/w-go/utils"
	"google.golang.org/grpc"
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
