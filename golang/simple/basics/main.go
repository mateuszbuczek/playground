package main

import (
	"github.com/mateuszbuczek/playground/golang/basics/controllers"
	"net/http"
)

func main() {
	controllers.RegisterControllers()

	http.ListenAndServe(":3000", nil)
}
