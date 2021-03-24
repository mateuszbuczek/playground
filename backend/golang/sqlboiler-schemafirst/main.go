package main

import (
	"context"
	"database/sql"
	"fmt"
	_ "github.com/lib/pq"
	"github.com/mateuszbuczek/playground/golang/sqlboiler-schemafirst/models"
	"github.com/volatiletech/sqlboiler/v4/boil"
	"github.com/volatiletech/sqlboiler/v4/queries/qm"
)

const (
	host     = "localhost"
	port     = 5432
	user     = "user"
	password = "password"
	dbname   = "postgres"
)

//go:generate sqlboiler psql

func main() {
	psqlInfo := fmt.Sprintf("host=%s port=%d user=%s "+
		"password=%s dbname=%s sslmode=disable",
		host, port, user, password, dbname)

	db, _ := sql.Open("postgres", psqlInfo)
	ctx := context.Background()

	pilots, _ := models.Pilots().All(ctx, db)

	for _, p := range pilots {
		fmt.Printf("p: %+v\n", p)
	}

	jets, _ := models.Jets().All(ctx, db)
	for _, j := range jets {
		fmt.Printf("j1: %+v\n", j)
	}

	jets, _ = models.Jets(qm.Where("color=?", "red")).All(ctx, db)
	for _, j := range jets {
		fmt.Printf("j2: %+v\n", j)
	}

	pilots, _ = models.Pilots(qm.Limit(1), qm.Offset(0)).All(ctx, db)
	for _, p := range pilots {
		fmt.Printf("p2: %+v\n", p)
	}

	p := models.Pilot{Name: "TOMEK"}
	_ = p.Insert(ctx, db, boil.Infer())
}
