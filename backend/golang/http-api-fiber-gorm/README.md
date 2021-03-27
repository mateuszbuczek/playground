#### API using gorm & fiber http library

- curl -X GET localhost:8080/api/books
- curl -X GET localhost:8080/api/books/1
- curl -X POST -H "Content-Type: application/json" --data "{\"title\": \"Angels and demons\"}" localhost:8080/api/books
- curl -X DELETE localhost:8080/api/books/4
