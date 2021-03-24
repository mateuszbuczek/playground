package main

import (
	"bufio"
	"fmt"
	"os"
	"runtime"
)

func main() {
	reader := bufio.NewReader(os.Stdin)
	fmt.Print("whats name:")
	readString, _ := reader.ReadString('\n')
	fmt.Printf("Hello %v", readString)

	fmt.Printf("Our version of go is %v\n running in %v", runtime.Version(), runtime.GOOS)
}
