package app

import "net/http"
import "github.com/mateuszbuczek/playground/go/first-app/mvc/interfaces"

func StartApp() {
	http.HandleFunc("/users", interfaces.GetUser)

	if err := http.ListenAndServe(":8080", nil); err != nil {
		panic(err)
	}
}
