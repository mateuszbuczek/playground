package main

import (
	"fmt"
	"os"
	"strconv"
)

func main() {
	args := os.Args[1:]

	if len(args) != 2 {
		fmt.Println("Please enter args")
	} else {
		mealTotal, _ := strconv.ParseFloat(args[0], 32)
		tipTotal, _ := strconv.ParseFloat(args[1], 32)
		fmt.Printf("\nYour meal total will be %.2f\n\n", calculateTotal(float32(mealTotal), float32(tipTotal)))
	}
}

func calculateTotal(total float32, total2 float32) float32 {
	return total + (total + (total2 / 100))
}
