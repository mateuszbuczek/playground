package main

import (
	"context"
	"github.com/mateuszbuczek/playground/grpc/w-go/pb"
	"google.golang.org/grpc"
	"time"
)

type AuthClient struct {
	service  pb.AuthServiceClient
	username string
	password string
}

func NewAuthClient(cc *grpc.ClientConn, username, password string) *AuthClient {
	client := pb.NewAuthServiceClient(cc)
	return &AuthClient{
		service:  client,
		username: username,
		password: password,
	}
}

func (client *AuthClient) Login() (string, error) {
	ctx, cancel := context.WithTimeout(context.Background(), 5*time.Second)
	defer cancel()

	request := &pb.LoginRequest{
		Username: client.username,
		Password: client.password,
	}

	res, err := client.service.Login(ctx, request)
	if err != nil {
		return "", err
	}

	return res.GetAccessToken(), nil
}
