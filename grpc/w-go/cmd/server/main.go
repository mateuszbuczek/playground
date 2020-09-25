package main

import (
	"flag"
	"fmt"
	"github.com/mateuszbuczek/playground/grpc/w-go/pb"
	"github.com/mateuszbuczek/playground/grpc/w-go/service"
	"google.golang.org/grpc"
	"log"
	"net"
)

func main() {
	port := flag.Int("port", 0, "the server port")
	flag.Parse()
	log.Printf("start server on port %d", *port)

	grpcServer := grpc.NewServer()

	laptopStore := service.NewInMemoryLaptopStore()
	diskImageStore := service.NewDiskImageStore("img")
	laptopServer := service.NewLaptopServer(laptopStore, diskImageStore)
	pb.RegisterLaptopServiceServer(grpcServer, laptopServer)

	address := fmt.Sprintf("0.0.0.0:%d", *port)
	listener, _ := net.Listen("tcp", address)
	grpcServer.Serve(listener)
}
