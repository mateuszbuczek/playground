package storage

import "database/sql"
import _ "github.com/go-sql-driver/mysql"

type Storage struct {
	db *sql.DB
}

func SetupStorage() (*Storage, error) {
	db, err := sql.Open("mysql", "root:pass@tcp(127.0.0.1:3306)/test_db")

	if err != nil {
		return nil, sql.ErrConnDone
	}
	return &Storage{db: db}, nil
}
