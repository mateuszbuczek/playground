package main

import (
	"fmt"
	"github.com/mateuszbuczek/playground/golang/basics/types/models"
)

func main() {
	u := models.User{
		Id:        2,
		FirstName: "tom",
		LastName:  "asd",
	}
	fmt.Println(u)
}
