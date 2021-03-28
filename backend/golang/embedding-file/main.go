package main

import (
	"embed"
	"net/http"
)

//go:embed test.txt
var s string

//go:embed assets/*
var assets embed.FS

func main() {
	print(s)

	fs := http.FileServer(http.FS(assets))
	http.ListenAndServe(":8080", fs)
}
