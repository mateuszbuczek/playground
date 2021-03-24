package main

import (
	"log"
	"sync"
)

func main() {
	log.Println("Starting all services")

	wg := new(sync.WaitGroup)
	wg.Add(1)
	go startServer(wg, events.StartServer)
}
