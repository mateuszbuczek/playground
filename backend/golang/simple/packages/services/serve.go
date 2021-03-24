package services

import (
	"github.com/mateuszbuczek/playground/golang/packages/services/internal/ports"
	"net/http"
	"strconv"
)

var port = 3001

func StartServer() error {
	sm := http.NewServeMux()
	//sm.Handle("/", new(eventHandler))
	return http.ListenAndServe(":"+strconv.Itoa(port), sm)
}

func init() {
	port = ports.EventService
}
