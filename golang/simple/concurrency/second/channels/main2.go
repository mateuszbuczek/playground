package main

import (
	"fmt"
	"time"
)

func main() {
	out := make(chan int)
	go count(out)
	for v := range out {
		fmt.Println(v)
	}
}

func count(out chan int) {
	for i := 1; i <= 10; i++ {
		time.Sleep(time.Second / 2)
		out <- i
	}
	close(out)
}
