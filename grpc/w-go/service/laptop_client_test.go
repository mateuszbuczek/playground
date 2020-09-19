package service

import (
	"context"
	"github.com/mateuszbuczek/playground/grpc/w-go/pb"
	"github.com/mateuszbuczek/playground/grpc/w-go/utils"
	"github.com/stretchr/testify/require"
	"google.golang.org/grpc"
	"io"
	"net"
	"testing"
)

func TestClientCreateLaptop(t *testing.T) {
	t.Parallel()

	laptopServer, serverAddress := startTestLaptopServer(t, NewInMemoryLaptopStore())
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

func TestClientSearchLaptop(t *testing.T) {
	t.Parallel()

	filter := &pb.Filter{
		MaxPriceUsd: 500,
		MinCpuCores: 2,
		MinCpuGhz:   3,
		MinRam:      nil,
	}

	store := NewInMemoryLaptopStore()
	expectedIds := make(map[string]bool)

	for i := 0; i < 5; i++ {
		laptop := utils.NewLaptop()

		switch i {
		case 0:
			laptop.PriceUsd = 150
		case 1:
			laptop.Cpu.NumberCores = 1
		case 2:
			laptop.Cpu.MinGhz = 2
		case 3:
			laptop.PriceUsd = 120
			laptop.Cpu.NumberCores = 5
			laptop.Cpu.MinGhz = 4
			expectedIds[laptop.Id] = true
		case 4:
			laptop.PriceUsd = 121
			laptop.Cpu.NumberCores = 51
			laptop.Cpu.MinGhz = 41
			expectedIds[laptop.Id] = true
		}

		err := store.Save(laptop)
		require.NoError(t, err)
	}

	_, serverAddress := startTestLaptopServer(t, store)
	client := newTestLaptopClient(t, serverAddress)

	request := &pb.SearchLaptopRequest{Filter: filter}
	stream, err := client.SearchLaptop(context.Background(), request)
	require.NoError(t, err)

	found := 0
	for {
		res, err := stream.Recv()
		if err == io.EOF {
			break
		}

		require.NoError(t, err)
		require.Contains(t, expectedIds, res.GetLaptop().GetId())

		found += 1
	}

	require.Equal(t, len(expectedIds), found)
}

func startTestLaptopServer(t *testing.T, store LaptopStore) (*LaptopServer, string) {
	laptopServer := NewLaptopServer(store)
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
