package main

import (
	"context"
	"fmt"
	"time"
)

func main() {
	ctx := context.Background()
	//ctx, cancel := context.WithCancel(ctx)

	//d := time.Now().Add(5 * time.Millisecond)
	//ctx, cancel := context.WithDeadline(ctx, d)
	ctx, cancel := context.WithTimeout(ctx, 5*time.Millisecond)

	ctx = context.WithValue(ctx, "requestID", "123456")

	for i := range gen(ctx) {
		time.Sleep(1 * time.Millisecond)
		fmt.Println("Got from gen:", i)
		if i == 10 {
			break
		}
	}
	cancel()
	time.Sleep(1 * time.Second)
}

func gen(ctx context.Context) <-chan int {
	out := make(chan int)
	var i int
	go func() {
		for {
			select {
			case <-ctx.Done():
				fmt.Println("stopping goroutine...")
				fmt.Println("request id", ctx.Value("requestID"))
				fmt.Println(ctx.Err())
				close(out)
				return
			case out <- i:
				i++
			}
		}
	}()

	return out
}
