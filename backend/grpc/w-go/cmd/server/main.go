package main

import (
	"flag"
	"fmt"
	"github.com/mateuszbuczek/playground/grpc/w-go/pb"
	"github.com/mateuszbuczek/playground/grpc/w-go/service"
	"golang.org/x/net/context"
	"google.golang.org/grpc"
	"log"
	"net"
	"time"
)

func loggingUnaryInterceptor(
	ctx context.Context,
	req interface{},
	info *grpc.UnaryServerInfo,
	handler grpc.UnaryHandler) (resp interface{}, err error) {
	log.Println("->> unary interceptor: ", info.FullMethod)
	return handler(ctx, req)
}

func loggingStreamInterceptor(
	srv interface{},
	stream grpc.ServerStream,
	info *grpc.StreamServerInfo,
	handler grpc.StreamHandler,
) error {
	log.Println("->> stream interceptor: ", info.FullMethod)
	return handler(srv, stream)
}

const (
	secretKey     = "secret"
	tokenDuration = 15 * time.Minute
)

func seedUsers(userStore service.UserStore) error {
	_ = createUser(userStore, "admin", "secret", "admin")
	return createUser(userStore, "user", "secret", "user")
}

func createUser(userSTore service.UserStore, username, password, role string) error {
	user, err := service.NewUser(username, password, role)
	if err != nil {
		return err
	}

	return userSTore.Save(user)
}

func main() {
	port := flag.Int("port", 8090, "the server port")
	flag.Parse()
	log.Printf("start server on port %d", *port)

	userStore := service.NewInMemoryUserStore()
	_ = seedUsers(userStore)
	jwtManager := service.NewJwtManager(secretKey, tokenDuration)
	authServer := service.NewAuthServer(userStore, jwtManager)

	interceptor := service.NewAuthInterceptor(jwtManager, accessibleRoles())
	grpcServer := grpc.NewServer(
		grpc.UnaryInterceptor(interceptor.Unary()),
		grpc.StreamInterceptor(interceptor.Stream()),
	)

	laptopStore := service.NewInMemoryLaptopStore()
	diskImageStore := service.NewDiskImageStore("img")
	ratingStore := service.NewInMemoryRatingStore()
	laptopServer := service.NewLaptopServer(laptopStore, diskImageStore, ratingStore)
	pb.RegisterLaptopServiceServer(grpcServer, laptopServer)
	pb.RegisterAuthServiceServer(grpcServer, authServer)
	address := fmt.Sprintf("0.0.0.0:%d", *port)
	listener, _ := net.Listen("tcp", address)
	grpcServer.Serve(listener)
}

func accessibleRoles() map[string][]string {
	const laptopServicePath = "/pb/"

	return map[string][]string{
		laptopServicePath + "CreateLaptop": {"admin"},
		laptopServicePath + "UploadImage":  {"admin"},
		laptopServicePath + "RateLaptop":   {"admin", "user"},
	}
}
