package interfaces

import (
	"github.com/gin-gonic/gin"
	"github.com/mateuszbuczek/playground/go/first-app/mvc/domain"
	"github.com/mateuszbuczek/playground/go/first-app/mvc/utils"
	"strconv"
)

func GetUser(c *gin.Context) {
	userId, err := strconv.ParseInt(c.Param("user_id"), 10, 64)
	if err != nil {
		apiErr :=
			&utils.ApplicationError{
				Message: "user id must be a number",
				Code:    "400",
			}
		utils.Respond(c, 400, apiErr)
		return
	}

	user, apiErr := domain.UsersService.GetUser(userId)
	if apiErr != nil {
		utils.Respond(c, 404, apiErr)
		return
	}

	utils.Respond(c, 200, user)
}
