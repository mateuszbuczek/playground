package rest

import (
	"hex-arch/pkg/adding"
	"net/http"
)

func handleAdding(as adding.Service) func(w http.ResponseWriter, r *http.Request) {
	return func(w http.ResponseWriter, r *http.Request) {

	}
}
