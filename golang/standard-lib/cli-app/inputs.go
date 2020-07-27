package main

import (
	"bufio"
	"fmt"
	"os"
)

func main() {
	/*	var name string
		fmt.Println("whats name:")
		inputs, _ := fmt.Scanf("%q", &name)

		switch inputs {
		case 0:
			fmt.Println("u must enter name")
		case 1:
			fmt.Println("Hello")
		}*/

	scanner := bufio.NewScanner(os.Stdin)
	for scanner.Scan() {
		if (scanner.Text()) == "/quit" {
			fmt.Println("Quitting")
			os.Exit(3)
		} else {
			fmt.Println("You typed " + scanner.Text())
		}
	}

	if err := scanner.Err(); err != nil {
		fmt.Println(err)
	}
}

func ReadFile(filename string) {
	file, err := os.Open(filename)
	if err != nil {
		fmt.Println(err)
	}

	defer file.Close()

	scanner := bufio.NewScanner(file)
	for scanner.Scan() {
		fmt.Println(scanner.Text())
	}

	if err := scanner.Err(); err != nil {
		fmt.Println(err)
	}
}
