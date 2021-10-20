package main

import (
	"fmt"
	"sync"
	"sync/atomic"
)

func main() {
	// goroutine
	go func() {
		fmt.Println("New goroutine")
	}()

	// channel
	ch := make(chan string)
	go func() {
		ch <- "Channel message"
	}()

	fmt.Println(<-ch)

	chBuffered := make(chan string, 2)
	chBuffered <- "1"
	chBuffered <- "2"

	fmt.Println(<-chBuffered)
	fmt.Println(<-chBuffered)

	// wait group
	var wg sync.WaitGroup
	wg.Add(1)
	go func() {
		fmt.Println("done")
		wg.Done()
	}()

	wg.Wait()

	// mutex
	iterations := 1000
	sum := 0
	wg.Add(iterations)
	var mu sync.Mutex
	for i := 0; i < iterations; i++ {
		go func() {
			mu.Lock()
			sum++
			mu.Unlock()
			wg.Done()
		}()
	}
	wg.Wait()
	fmt.Println(sum)

	// once
	iterations = 1000
	sum = 0
	wg.Add(iterations)
	var once sync.Once
	for i := 0; i < iterations; i++ {
		go func() {
			once.Do(func() {
				sum++
			})
		}()
		wg.Done()
	}
	wg.Wait()
	fmt.Println(sum)

	// pool
	memPool := &sync.Pool{New: func() interface{} {
		mem := make([]byte, 1024)
		return &mem
	}}

	mem := memPool.Get().(*[]byte)
	memPool.Put(mem)

	// cond (signal & broadcast)
	c := sync.NewCond(&sync.Mutex{})
	go func() {
		c.Signal()
	}()
	c.L.Lock()
	c.Wait()
	c.L.Unlock()

	// thread safe map
	syncMap := sync.Map{}
	wg.Add(iterations)
	for i := 0; i < iterations; i++ {
		go func() {
			syncMap.Store(0, 1)
			wg.Done()
		}()
	}
	wg.Wait()

	// atomic
	var i int64
	atomic.AddInt64(&i, 1)

}
