package main

import (
	"fmt"
	"log"
	"math/rand"
	"os"
	"runtime/trace"
	"time"
)

func main() {
	file, err := os.Create("trace.out")
	if err != nil {
		log.Fatalf("We did not create a trace file! %v\n", err)
	}

	defer func() {
		if err := file.Close(); err != nil {
			log.Fatalf("Failed to close trace file")
		}
	}()

	if err := trace.Start(file); err != nil {
		log.Fatalf("We failed to start a trace")
	}

	defer trace.Stop()

	AddRandomNumber()
}

func AddRandomNumber() {
	first := rand.Intn(100)
	second := rand.Intn(100)

	time.Sleep(2 * time.Second)

	result := first * second

	fmt.Printf("Result of 2 numbers is %d\n", result)
}
