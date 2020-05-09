package utils

import (
	"github.com/stretchr/testify/assert"
	"sort"
	"testing"
)

func TestBubbleSort(t *testing.T) {
	// given
	elements := []int{9, 8, 7, 6}

	// when
	BubbleSort(elements)

	// then
	assert.NotNil(t, elements)
	assert.EqualValues(t, len(elements), 4)
	assert.EqualValues(t, 6, elements[0])
	assert.EqualValues(t, 7, elements[1])
	assert.EqualValues(t, 8, elements[2])
	assert.EqualValues(t, 9, elements[3])
}

func TestBubbleSortAlreadySorted(t *testing.T) {
	// given
	elements := []int{6, 7, 8, 9}

	// when
	BubbleSort(elements)

	// then
	assert.NotNil(t, elements)
	assert.EqualValues(t, len(elements), 4)
	assert.EqualValues(t, 6, elements[0])
	assert.EqualValues(t, 7, elements[1])
	assert.EqualValues(t, 8, elements[2])
	assert.EqualValues(t, 9, elements[3])
}

func TestBubbleSortNilSlice(t *testing.T) {
	BubbleSort(nil)
}

func getElements(n int) []int {
	result := make([]int, n)
	for j := n - 1; j >= 0; j-- {
		result[n-j-1] = j
	}
	return result
}

func BenchmarkBubbleSort1000(b *testing.B) {
	elements := getElements(1000)

	for i := 0; i < b.N; i++ {
		BubbleSort(elements)
	}
}

func BenchmarkBubbleSort100000(b *testing.B) {
	elements := getElements(100000)

	for i := 0; i < b.N; i++ {
		BubbleSort(elements)
	}
}

func BenchmarkNativeSort100000(b *testing.B) {
	elements := getElements(100000)

	for i := 0; i < b.N; i++ {
		sort.Ints(elements)
	}
}
