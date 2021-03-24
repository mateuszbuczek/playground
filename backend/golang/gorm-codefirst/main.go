package main

import (
	"fmt"
	"github.com/jinzhu/gorm"
	_ "github.com/jinzhu/gorm/dialects/postgres"
	"log"
)

const (
	host     = "localhost"
	port     = 5432
	user     = "user"
	password = "password"
	dbname   = "postgres"
)

type Channel struct {
	gorm.Model
	Name        string
	Description string
}

type User struct {
	gorm.Model
	Email    string
	Username string
}

type Message struct {
	gorm.Model
	Content   string
	UserID    uint
	ChannelID uint
	User      User
	Channel   Channel
}

func main() {
	psqlInfo := fmt.Sprintf("host=%s port=%d user=%s "+
		"password=%s dbname=%s sslmode=disable",
		host, port, user, password, dbname)
	db, _ := gorm.Open("postgres", psqlInfo)
	defer db.Close()

	// turn on verbose logging
	db.LogMode(true)
	setup(db)

	var users []User
	db.Find(&users)
	for _, u := range users {
		fmt.Printf("%+v\n", u)
	}

	var messages []Message
	db.Model(users[0]).Related(&messages)
	for _, m := range messages {
		fmt.Printf("%+v\n", m)
	}

	doError(db)
}

func setup(db *gorm.DB) {
	db.AutoMigrate(&Channel{}, &User{}, &Message{})
	seed(db)
}

func seed(db *gorm.DB) {
	channels := []Channel{
		{Name: "General", Description: "General discussions"},
		{Name: "Off-topic", Description: "weird stuff"},
	}
	for _, c := range channels {
		db.Create(&c)
	}
	users := []User{
		{
			Email:    "tom@asd.com",
			Username: "tom",
		},
		{
			Email:    "sus@asd.com",
			Username: "sus",
		},
	}
	for _, u := range users {
		db.Create(&u)
	}

	var generalChat, suggestionsChat Channel
	db.First(&generalChat, "Name=?", "General")
	db.First(&suggestionsChat, "Name=?", "Suggestions")
	var tom, sus User
	db.First(&tom, "Username=?", "tom")
	db.First(&sus, "Username=?", "sus")

	messages := []Message{
		{
			Content: "Hello",
			User:    tom,
			Channel: generalChat,
		},
		{
			Content: "Hi",
			User:    sus,
			Channel: generalChat,
		},
		{
			Content: "more videos",
			User:    tom,
			Channel: suggestionsChat,
		},
	}
	for _, m := range messages {
		db.Create(&m)
	}
}

func doError(db *gorm.DB) {
	var fred User
	if err := db.Where("username =?", "Fred").First(&fred).Error; err != nil {
		log.Fatalf("Error when loading user: %s", err)
	}
}
