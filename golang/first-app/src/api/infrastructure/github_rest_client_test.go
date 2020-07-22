package infrastructure

import (
	"github.com/stretchr/testify/assert"
	"testing"
)

func TestCreateRepo(t *testing.T) {
	header := getAuthorizationHeader("abc123")
	assert.EqualValues(t, "token abc123", header)
}
