package module7

import (
	"bufio"
	"log"
	"os"
	"strings"
	"testing"
)

func TestModule7gogenerate(t *testing.T) {
	found := OpenFileAndFindString("module7.go", "//go:generate goimports -w module7_code.go")

	if !found {
		t.Errorf("proper use of `goimports` for `module7_code.go` is not found")
	}
}

func TestModule7Import(t *testing.T) {
	found := OpenFileAndFindString("module7_code.go", "import (")
	if !found {
		t.Errorf("import not found")
		return
	}

	found = OpenFileAndFindString("module7_code.go", "	\"fmt\"")
	if !found {
		t.Errorf("package 'fmt' not imported")
		return
	}

	found = OpenFileAndFindString("module7_code.go", "	\"runtime\"")

	if !found {
		t.Errorf("package 'runtime' not imported")
		return
	}
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
