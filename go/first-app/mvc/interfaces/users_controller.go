package interfaces

import (
	"encoding/json"
	"github.com/mateuszbuczek/playground/go/first-app/mvc/domain"
	"github.com/mateuszbuczek/playground/go/first-app/mvc/utils"
	"log"
	"net/http"
	"strconv"
)

func GetUser(resp http.ResponseWriter, req *http.Request) {
	userIdParam := req.URL.Query().Get("user_id")
	log.Printf("about to process user_id %v", userIdParam)

	userId, err := strconv.ParseInt(userIdParam, 10, 64)
	if err != nil {
		apiErr :=
			&utils.ApplicationError{
				Message: "user id must be a number",
				Code:    "400",
			}
		resp.WriteHeader(400)
		json, _ := json.Marshal(apiErr)
		resp.Write(json)
		return
	}

	user, apiErr := domain.UsersService.GetUser(userId)

	if err != nil {
		resp.WriteHeader(404)
		json, _ := json.Marshal(apiErr)
		resp.Write(json)
	}

	jsonValue, _ := json.Marshal(user)
	resp.Write(jsonValue)
}
