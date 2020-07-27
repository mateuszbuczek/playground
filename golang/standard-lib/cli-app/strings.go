package main

import "fmt"

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
