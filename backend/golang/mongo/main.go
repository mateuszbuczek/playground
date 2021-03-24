package main

import (
	"context"
	"fmt"
	"go.mongodb.org/mongo-driver/bson"
	"go.mongodb.org/mongo-driver/mongo"
	"go.mongodb.org/mongo-driver/mongo/options"
	"log"
)

var (
	mongoURI = "mongodb://localhost:27017"
)

func main() {
	client, err := mongo.NewClient(options.Client().ApplyURI(mongoURI))
	if err != nil {
		log.Fatal(err)
	}
	ctx := context.Background()
	err = client.Connect(ctx)
	if err != nil {
		log.Fatal(err)
	}

	defer client.Disconnect(ctx)
	demoDB := client.Database("admin")
	err = demoDB.CreateCollection(ctx, "cats")
	if err != nil {
		log.Fatal(err)
	}

	catsCollection := demoDB.Collection("cats")
	defer catsCollection.Drop(ctx)

	result, err := catsCollection.InsertOne(ctx, bson.D{
		{Key: "name", Value: "Mocha"},
		{Key: "breed", Value: "Turkish van"},
	})
	if err != nil {
		log.Fatal(err)
	}
	fmt.Println(result)

	manyResult, err := catsCollection.InsertMany(ctx, []interface{}{
		bson.D{
			{Key: "name", Value: "a"},
			{Key: "breed", Value: "b"},
		},
		bson.D{
			{Key: "name", Value: "c"},
			{Key: "breed", Value: "d"},
		},
	})
	if err != nil {
		log.Fatal(err)
	}

	fmt.Println(manyResult)

	cursor, err := catsCollection.Find(ctx, bson.M{})
	if err != nil {
		log.Fatal(err)
	}

	var cats []bson.M
	if err = cursor.All(ctx, &cats); err != nil {
		log.Fatal(err)
	}

	log.Println(cats)
}
