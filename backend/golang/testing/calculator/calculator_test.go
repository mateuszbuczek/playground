package calculator

import "testing"

type TestCase struct {
	value    int
	expected bool
	actual   bool
}

func TestCalculateIsArmstrong(t *testing.T) {
	t.Run("should return true for 371", func(t *testing.T) {
		testCase := TestCase{
			value:    371,
			expected: true,
		}
		testCase.actual = CalculateIsArmstrong(testCase.value)
		if testCase.actual != testCase.expected {
			t.Fail()
		}
	})
	t.Run("should return true for 370", func(t *testing.T) {
		testCase := TestCase{
			value:    370,
			expected: true,
		}
		testCase.actual = CalculateIsArmstrong(testCase.value)
		if testCase.actual != testCase.expected {
			t.Fail()
		}
	})
}

func TestNegativeCalculateIsArmstrong(t *testing.T) {
	t.Run("shoud fail for case 350", func(t *testing.T) {
		testCase := TestCase{
			value:    350,
			expected: false,
		}

		testCase.actual = CalculateIsArmstrong(testCase.value)
		if testCase.actual != testCase.expected {
			t.Fail()
		}
	})
}

func TestCalculateIsArmstrong_2(t *testing.T) {
	type args struct {
		n int
	}
	tests := []struct {
		name string
		args args
		want bool
	}{
		{name: "returns true if is armstrong value", args: struct{ n int }{n: 371}, want: true},
		{name: "returns false if is not armstrong value", args: struct{ n int }{n: 350}, want: false},
	}
	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			if got := CalculateIsArmstrong(tt.args.n); got != tt.want {
				t.Errorf("CalculateIsArmstrong() = %v, want %v", got, tt.want)
			}
		})
	}
}
