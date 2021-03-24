package domain

import (
	"fmt"
	"github.com/mateuszbuczek/playground/go/first-app/mvc/utils"
)

var (
	users = map[int64]*User{
		123: {
			Id:        123,
			FirstName: "Tom",
			LastName:  "Asd",
			Email:     "@example.com",
		},
	}
)

type usersService struct {
}

var (
	UsersService usersService
)

func (u *usersService) GetUser(userId int64) (*User, *utils.ApplicationError) {
	if user := users[userId]; user != nil {
		return user, nil
	}

	return nil, &utils.ApplicationError{
		Message: fmt.Sprintf("user %v was not found", userId),
		Code:    "404",
	}
}
