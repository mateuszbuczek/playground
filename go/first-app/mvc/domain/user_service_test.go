package domain

import "testing"

func TestGetUser(t *testing.T) {
	user, applicationError := GetUser(0)

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
