package main

import (
	"hex-arch/pkg/adding"
	"hex-arch/pkg/rest"
	"hex-arch/pkg/storage"
	"log"
	"net/http"
)

func main() {
	r, err := storage.SetupStorage()
	if err != nil {
		log.Fatal(err)
	}

	as := adding.NewService(r)
	router := rest.Handler(as)

	log.Fatal(http.ListenAndServe(":8080", router))
}
