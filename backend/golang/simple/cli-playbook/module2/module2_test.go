package module2

import (
	"bufio"
	"log"
	"os"
	"testing"
)

func TestModule2GoFormatContent(t *testing.T) {
	numOfLines := OpenFileAndCountLines("./module2_code.go")
	if numOfLines == 8 {
		t.Errorf("it looks your 'go fmt' does not work as we expected for ./module2_code.go")
	}

	// TODO: add more tests
}

func TestModule2GoFormatHello(t *testing.T) {
	numOfLines := OpenFileAndCountLines("./module2_hello.go")
	if numOfLines == 5 {
		t.Errorf("it looks your 'go fmt' does not work as we expected for ./module2_hello.go")
	}

	// TODO: add more tests
}

// OpenFileAndCountLines opens a file and returns number of lines in the file
func OpenFileAndCountLines(filename string) int {
	f, err := os.Open(filename)
	if err != nil {
		log.Fatal(err)
	}
	defer f.Close()

	scanner := bufio.NewScanner(f)
	number := 0
	for scanner.Scan() {
		number++
	}

	return number
}
