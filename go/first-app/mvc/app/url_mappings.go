package app

import (
	"github.com/mateuszbuczek/playground/go/first-app/mvc/interfaces"
)

func mapUrls() {
	router.GET("/users/:user_id", interfaces.GetUser)
}
