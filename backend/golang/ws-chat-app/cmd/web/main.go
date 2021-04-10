package main

import (
	"log"
	"net/http"
	"ws-chat-app/internal/handlers"
)

func main() {
	mux := routes()

	go handlers.ListenToWsChannel()

	log.Println("Starting web server on port 8080")

	_ = http.ListenAndServe(":8080", mux)
}
