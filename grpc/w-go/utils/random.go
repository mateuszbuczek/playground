package utils

import "math/rand"

func randomBool() bool {
	return rand.Intn(2) == 1
}

func randomStringFromSet(a ...string) string {
	n := len(a)
	if n == 0 {
		return ""
	} else {
		return a[rand.Intn(n)]
	}
}

func randomInt(min, max int) int {
	return min + rand.Intn(max-min+1)
}

func randomFloat(min, max float64) float64 {
	return min + rand.Float64()*(max-min)
}
