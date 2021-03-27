package main

import (
	"github.com/gofiber/fiber"
	"http-api-fiber-gorm/book"
	"http-api-fiber-gorm/database"
)

func main() {
	database.DBConn.AutoMigrate(&book.Book{})
	defer database.DBConn.Close()

	app := fiber.New()
	setupRoutes(app)

	app.Listen(8080)
}

func setupRoutes(app *fiber.App) {
	app.Get("/api/books", book.GetBooks)
	app.Get("/api/books/:id", book.GetBook)
	app.Post("/api/books", book.NewBook)
	app.Delete("/api/books/:id", book.DeleteBook)
}
