package main

import (
	"bufio"
	"fmt"
	"os"
	"strings"
)

func main() {
	stringChan := make(chan string)
	tower1Chan := make(chan string)
	tower2Chan := make(chan string)

	var offset int32 = 3

	/** go routine */
	go tower1(stringChan, tower1Chan, offset)
	go tower2(stringChan, tower2Chan, offset)

	for i := 0; i < 2; i++ {
		select {
		case msg := <-tower1Chan:
			fmt.Printf("\nControler Tower: Message from Tower 1 - %v", msg)
		case msg := <-tower2Chan:
			fmt.Printf("\nControler Tower: Message from Tower 2 - %v", msg)
		}
	}
}

func tower1(s chan string, t1 chan string, offset int32) {
	is := bufio.NewReader(os.Stdin)
	fmt.Println("Tower1: Enter your Message for Tower 2: ")
	input, _ := is.ReadString('\n')
	input = strings.Replace(input, "\r\n", "", -1)

	fmt.Printf("\nTower1: Original String: %s", input)

	var secretString string

	for _, c := range input {
		secretString += string(c + offset)
	}

	fmt.Printf("\nTower 1: Encrypted String: %s", secretString)

	s <- secretString
	t1 <- "Msg send to Tower 2"
}

func tower2(s chan string, t2 chan string, offset int32) {
	secretString := <-s
	var orgString string

	for _, c := range secretString {
		orgString += string(c - offset)
	}

	fmt.Printf("\nTower2: Decrypted MEssage: %s", orgString)
	t2 <- "Msg received from Tower1"
}
