package main

import (
	"fmt"
	"regexp"
	"strings"
)

type messageType int

const (
	INFO messageType = 0 + iota
	WARNING
	ERROR
)

const (
	InfoColor    = "\033[1;34m%s\033[0m"
	WarningColor = "\033[1;33m%s\033[0m"
	ErrorColor   = "\033[1;31m%s\033[0m"
)

func main() {
	showMessage(INFO, "show me string")
}

func showMessage(messageType messageType, message string) {
	switch messageType {
	case INFO:
		printMessage := fmt.Sprintf("\nInformation: \n%s\n", message)
		fmt.Printf(InfoColor, printMessage)
	case WARNING:
		printMessage := fmt.Sprintf("\nWarning: \n%s\n", message)
		fmt.Printf(WarningColor, printMessage)
	case ERROR:
		printMessage := fmt.Sprintf("\nError: \n%s\n", message)
		fmt.Printf(ErrorColor, printMessage)
	}
}

func CompareCaseIns(a, b string) bool {
	if len(a) == len(b) {
		if strings.ToLower(a) == strings.ToLower(b) {
			return true
		}
	}

	return false
}

func FindSubstring(search, string string) bool {
	return strings.Contains(string, search)
}

func ReplaceAll(string, searchPhrase, replacePhrase string) string {
	return strings.Replace(string, searchPhrase, replacePhrase, -1)
}

func find(string string) bool {
	r, _ := regexp.Compile(`s(\w[a-z]+)g\b`)

	r.FindAllStringSubmatch(string, -1)
	return r.MatchString(string)
}
