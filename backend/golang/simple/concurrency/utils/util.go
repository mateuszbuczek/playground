package utils

import (
	"fmt"
	"math/rand"
	"time"
)

//generator pattern
func Generate(msg string) <-chan string {
	c := make(chan string)
	go func() {
		for i := 0; ; i++ {
			c <- fmt.Sprintf("%s %d", msg, i)
			time.Sleep(time.Duration(rand.Intn(2e3)) * time.Millisecond)
		}
	}()
	return c
}

//merge into one chan
func FanIn(first, second <-chan string) <-chan string {
	c := make(chan string)
	go func() {
		for {
			select {
			case s := <-first:
				c <- s
			case s := <-second:
				c <- s
			}
		}
	}()
	return c
}
