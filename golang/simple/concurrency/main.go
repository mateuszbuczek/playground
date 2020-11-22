package main

import (
	"concurrency/utils"
	"fmt"
	"time"
)

func main() {
	a := utils.FanIn(utils.Generate("a"), utils.Generate("b"))
	timeout := time.After(5 * time.Second)

	for {
		select {
		case s := <-a:
			fmt.Println(s)
		//case <- time.After(1.9e3 * time.Millisecond): fmt.Println("too slow"); return
		case <-timeout:
			fmt.Println("timeout")
			return
		}
	}
}
