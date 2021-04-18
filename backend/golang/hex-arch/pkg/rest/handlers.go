package rest

import "hex-arch/pkg/adding"
import "github.com/go-chi/chi"

func Handler(as adding.Service) *chi.Mux {
	router := chi.NewRouter()

	router.Get("/api/users", handleAdding(as))

	return router
}
