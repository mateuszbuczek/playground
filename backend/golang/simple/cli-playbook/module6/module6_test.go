package module6

import (
	"bufio"
	"log"
	"os"
	"strings"
	"testing"
)

func TestModule6DocumentTest(t *testing.T) {
	found := OpenFileAndFindString("module6.txt", "func FunctionForModule6GoDoc()")

	if !found {
		t.Errorf("go doc does not generate the comment properly")
	}

	// TODO: add more tests
}

// OpenFileAndFindString opens a file and return if the given string is found or not
func OpenFileAndFindString(filename string, expected string) bool {
	f, err := os.Open(filename)
	if err != nil {
		log.Fatal(err)
	}
	defer f.Close()

	scanner := bufio.NewScanner(f)
	scanner.Split(bufio.ScanLines)

	for scanner.Scan() {
		t := scanner.Text()
		trimmed := strings.Trim(t, " ")
		if trimmed == "" {
			continue
		}

		// matching logic
		if trimmed == expected {
			return true
		}
	}

	return false
}
