package main

import (
	"errors"
	"github.com/mateuszbuczek/playground/golang/functions/sv"
	"math"
)

func main() {
	numbers := []float64{12.2, 14, 16}
	println(sum(numbers...))

	s := sv.NewSemanticVersion(1, 2, 3)
	println(s.ToString())
}

func divide(p1, p2 float64) (float64, error) {
	if p2 == 0 {
		return math.NaN(), errors.New("cannot divide by zero")
	}

	return p1 / p2, nil
}

//variadic func
func sum(values ...float64) float64 {
	total := 0.0
	for _, value := range values {
		total += value
	}
	return total
}
