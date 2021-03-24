package main

import (
	"concurrency/search"
	"concurrency/utils"
	"fmt"
	"math/rand"
	"time"
)

func main() {
	//simpleExample()
	harderExample()
}

func harderExample() {
	rand.Seed(time.Now().UnixNano())
	start := time.Now()

	var result string
	timeout := time.After(100 * time.Millisecond)
	nodes := getNodesAddresses(100)
	c := make(chan string)
	go func(result chan<- string) { result <- getFastest("web", nodes) }(c)
	go func(result chan<- string) { result <- getFastest("video", nodes) }(c)
	go func(result chan<- string) { result <- getFastest("games", nodes) }(c)

	for i := 0; i < 3; i++ {
		select {
		case s := <-c:
			result += s
		case <-timeout:
			fmt.Println(time.Since(start))
			fmt.Println(result)
			return
		}
	}

	elapsed := time.Since(start)
	fmt.Println(elapsed)
	fmt.Println(result)
}

func getNodesAddresses(length int) []int {
	arr := make([]int, length)
	for i := range arr {
		arr[i] = i
	}
	return arr
}

func getFastest(query string, nodes []int) string {
	c := make(chan string)
	searchQuery := func(i int, query string) { c <- fmt.Sprintf("%d - %s\n", i, search.Search(query)) }
	for nodeAddress := range nodes {
		go searchQuery(nodeAddress, query)
	}
	return <-c
}

func simpleExample() {
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
