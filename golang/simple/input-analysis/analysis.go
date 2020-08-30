package main

import (
	"encoding/json"
	"io/ioutil"
	"log"
	"os"
	"time"
)

type Values struct {
	Name     string  `json:"name"`
	TempMin  float32 `json:"tempMin"`
	TempMax  float32 `json:"tempMax"`
	Interval int     `json:"interval"`
	Values   []Value `json:"values"`
}

type Value struct {
	Message      int     `json:"messageId"`
	Temperature  float32 `json:"temperature"`
	EnqueuedTime string  `json:"enqueuedTime"`
}

type reading struct {
	hour       int
	normal     float32
	outofrange float32
}

func main() {
	file, err := os.Open("data.json")
	if err != nil {
		log.Fatal("File not found")
	}

	defer file.Close()

	byteValue, _ := ioutil.ReadAll(file)

	var v Values
	json.Unmarshal(byteValue, &v)

	tempMap := make(map[int][]float32)

	for _, e := range v.Values {
		t, err := time.Parse("2006-01-02 15:04:05", e.EnqueuedTime)
		if err != nil {
			log.Fatal(err)
		}

		h := t.Hour()
		tempMap[h] = append(tempMap[h], e.Temperature)
	}

	var normal, outofrange float32
	var readings []reading
}
