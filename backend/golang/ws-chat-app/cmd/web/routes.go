package main

import (
	"net/http"
	"ws-chat-app/handlers"
)
import "github.com/bmizerany/pat"

func routes() http.Handler {
	mux := pat.New()

	mux.Get("/", http.HandlerFunc(handlers.Home))

	return mux
}
