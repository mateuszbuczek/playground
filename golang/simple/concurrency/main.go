package main

import (
	"concurrency/utils"
	"fmt"
)

func main() {
	a := utils.Generate("some-message")
	for i := 0; i < 5; i++ {
		fmt.Printf("received: %q\n", <-a)
	}
}
