package main

import (
	"fmt"
	"sync"
)

func main() {
	var counter int
	var wg sync.WaitGroup
	var mutex sync.Mutex
	wg.Add(3)
	go increment(&counter, &wg, &mutex)
	go increment(&counter, &wg, &mutex)
	go increment(&counter, &wg, &mutex)
	wg.Wait()
	fmt.Println(counter)
}

func increment(counter *int, wg *sync.WaitGroup, mutex *sync.Mutex) {
	mutex.Lock()
	*counter++
	mutex.Unlock()
	wg.Done()
}
