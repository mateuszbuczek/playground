package domain

import (
	"encoding/json"
	"github.com/stretchr/testify/assert"
	"testing"
)

func TestCreateRepoRequestAsJson(t *testing.T) {
	request := CreateRepoRequest{
		Name:        "introduction-hello-world",
		Description: "introduction repository",
		Homepage:    "https://github.com",
		Private:     true,
		HasIssues:   true,
		HasProjects: true,
		HasWiki:     true,
	}

	bytes, err := json.Marshal(request)

	assert.Nil(t, err)
	assert.NotNil(t, bytes)
	assert.EqualValues(t, string(bytes), `{"name":"introduction-hello-world","description":"introduction repository","homepage":"https://github.com","private":true,"has_issues":true,"has_projects":true,"has_wiki":true}`)

	var target CreateRepoRequest
	err = json.Unmarshal(bytes, &target)
	assert.EqualValues(t, target.Name, request.Name)
}
