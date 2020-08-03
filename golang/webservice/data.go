package main

import (
	"database/sql"
	"log"
)

var DbConn *sql.DB

func SetupDatabase() {
	var err error

	DbConn, err := sql.Open("mysql", "root:password123@tcp(127.0.0.1:3306)/dbname")
	if err != nil {
		log.Fatal(err)
	}
}
