package main

import (
	"bufio"
	"fmt"
	"os"
	"time"
)

func main1() {
	//channel := make(chan int, 2)
	//
	//counter := 5
	//
	//channel <- counter
	//close(channel)
	//response, open := <-channel
	//fmt.Println(response, open)

	go func() {
		var i int
		for {
			time.Sleep(time.Second / 2)
			i++
			fmt.Println("Goroutine1: ", i)
		}
	}()

	go func() {
		var i int
		for {
			time.Sleep(time.Second)
			i++
			fmt.Println("Goroutine2: ", i)
		}
	}()

	bufio.NewScanner(os.Stdin).Scan()
}
