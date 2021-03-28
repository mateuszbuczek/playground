package calculator

import "math"

// example: 371 == 3^3 + 7^3 + 3^3
func CalculateIsArmstrong(n int) bool {
	a := n / 100
	b := n % 100 / 10
	c := n % 10
	return n == int(math.Pow(float64(a), 3)+math.Pow(float64(b), 3)+math.Pow(float64(c), 3))
}
