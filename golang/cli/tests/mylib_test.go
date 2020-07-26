package mylib

import "testing"

func Test_BasicChecks(t *testing.T) {
	t.Run("Go can add", func(t *testing.T) {
		if 1+1 != 2 {
			t.Fail()
		}
	})

	t.Run("Go can concat", func(t *testing.T) {
		if "Hello"+"go" != "Hellogo" {
			t.Fail()
		}
	})
}
