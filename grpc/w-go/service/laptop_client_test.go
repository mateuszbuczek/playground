package service

import (
	"context"
	"github.com/mateuszbuczek/playground/grpc/w-go/pb"
	"github.com/mateuszbuczek/playground/grpc/w-go/utils"
	"github.com/stretchr/testify/require"
	"google.golang.org/grpc"
	"net"
	"testing"
)

func TestClientCreateLaptop(t *testing.T) {
	t.Parallel()

	laptopServer, serverAddress := startTestLaptopServer(t)
	client := newTestLaptopClient(t, serverAddress)

	laptop := utils.NewLaptop()
	expectedId := laptop.Id

	request := &pb.CreateLaptopRequest{
		Laptop: laptop,
	}

	response, err := client.CreateLaptop(context.Background(), request)
	require.NoError(t, err)
	require.NotNil(t, response)
	require.Equal(t, expectedId, response.Id)

	other, err := laptopServer.Store.Find(response.Id)
	require.NoError(t, err)
	require.NotNil(t, other)
}

func startTestLaptopServer(t *testing.T) (*LaptopServer, string) {
	laptopServer := NewLaptopServer(NewInMemoryLaptopStore())
	grpcServer := grpc.NewServer()

	pb.RegisterLaptopServiceServer(grpcServer, laptopServer)

	listener, err := net.Listen("tcp", ":0") // random available port
	require.NoError(t, err)

	go grpcServer.Serve(listener) // non block server

	return laptopServer, listener.Addr().String()
}

func newTestLaptopClient(t *testing.T, serverAddress string) pb.LaptopServiceClient {
	conn, err := grpc.Dial(serverAddress, grpc.WithInsecure())
	require.NoError(t, err)
	return pb.NewLaptopServiceClient(conn)
}
