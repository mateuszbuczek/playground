package developer

import "testing"
import "github.com/stretchr/testify/assert"

func TestFilterUnique(t *testing.T) {
	developers := []Developer{
		{Name: "a"},
		{Name: "a"},
		{Name: "b"},
		{Name: "c"},
		{Name: "d"},
		{Name: "c"},
	}

	expected := []string{
		"a",
		"b",
		"c",
		"d",
	}

	assert.Equal(t, expected, FilterUnique(developers))
}

func TestNegativeFilterUnique(t *testing.T) {
	developers := []Developer{
		{Name: "a"},
		{Name: "a"},
		{Name: "b"},
		{Name: "c"},
		{Name: "d"},
		{Name: "c"},
	}

	expected := []string{
		"a",
		"b",
		"d",
	}

	assert.NotEqual(t, expected, FilterUnique(developers))
}
