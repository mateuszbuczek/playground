package search

import (
	"fmt"
	"math/rand"
	"time"
)

func Search(query string) string {
	time.Sleep(time.Duration(rand.Intn(1e3)) * time.Millisecond)
	return fmt.Sprintf("%s - done", query)
}
