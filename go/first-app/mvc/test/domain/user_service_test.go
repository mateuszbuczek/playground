package domain

import (
	"github.com/mateuszbuczek/playground/go/first-app/mvc/domain"
	"testing"
)

func TestGetUser(t *testing.T) {
	user, applicationError := domain.UsersService.GetUser(0)

	if user != nil {
		t.Error("we were not expecting a user with id 0")
	}

	if applicationError == nil {
		t.Error("we were expecting an error when user id is 0")
	}

	if applicationError.Code != "404" {
		t.Error("we were expecting 404 error code")
	}
}
