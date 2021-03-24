package infrastructure

import (
	"bytes"
	"encoding/json"
	"fmt"
	"github.com/mateuszbuczek/playground/go/first-app/src/api/domain"
	"io/ioutil"
	"log"
	"net/http"
)

const (
	headerAuthorization       = "Authorization"
	headerAuthorizationFormat = "token %s"
	urlCreateRepo             = "https://api.github.com/user/repos"
)

func getAuthorizationHeader(accessToken string) string {
	return fmt.Sprintf(headerAuthorizationFormat, accessToken)
}

func CreateRepo(request domain.CreateRepoRequest, accessToken string) (*domain.CreateRepoResponse, *domain.ErrorResponse) {
	headers := http.Header{}
	headers.Set(headerAuthorization, getAuthorizationHeader(accessToken))

	call, err := makePostCall(urlCreateRepo, request, headers)
	if err != nil {
		log.Println("error when trying to create new repo in github: %s", err.Error())
		return nil, &domain.ErrorResponse{
			StatusCode: 500,
			Message:    err.Error(),
		}
	}

	bytes, err := ioutil.ReadAll(call.Body)
	if err != nil {
		return nil, &domain.ErrorResponse{
			StatusCode: 500,
			Message:    "invalid response body",
		}
	}
	defer call.Body.Close()

	if call.StatusCode > 299 {
		var errResponse domain.ErrorResponse
		if err := json.Unmarshal(bytes, &errResponse); err != nil {
			return nil, &domain.ErrorResponse{
				StatusCode: 500,
				Message:    "invalid json response body",
			}
		}
		errResponse.StatusCode = call.StatusCode
		return nil, &errResponse
	}

	var result domain.CreateRepoResponse
	if err := json.Unmarshal(bytes, &result); err != nil {
		log.Println("error when trying to unmarshal successful response: %s", err.Error())
		return nil, &domain.ErrorResponse{
			StatusCode: 500,
			Message:    "error unmarshalling successful response",
		}
	}

	return &result, nil
}

func makePostCall(url string, body interface{}, headers http.Header) (*http.Response, error) {
	jsonBytes, err := json.Marshal(body)
	if err != nil {
		return nil, err
	}

	request, err := http.NewRequest(http.MethodPost, url, bytes.NewReader(jsonBytes))
	request.Header = headers

	client := http.Client{}
	return client.Do(request)
}
