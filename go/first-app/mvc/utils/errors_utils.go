package utils

type ApplicationError struct {
	Message string `json:"message"`
	Code    string `json:"code"`
}
