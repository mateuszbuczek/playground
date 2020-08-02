package main

import "fmt"

type Book struct {
	ID            int
	Title         string
	Author        string
	YearPublished int
}

func (b Book) String() string {
	return fmt.Sprintf(
		"ID:\t\t\t%v\n "+
			"Title:\t\t\tq\n "+
			"Author:\t\t%q\n "+
			"Published:\t\t%v\n ", b.ID, b.Title, b.Author, b.YearPublished)
}

var books = []Book{
	{
		ID:            1,
		Title:         "a",
		Author:        "b",
		YearPublished: 1990,
	},
	{
		ID:            2,
		Title:         "b",
		Author:        "c",
		YearPublished: 1991,
	},
	{
		ID:            3,
		Title:         "c",
		Author:        "d",
		YearPublished: 1992,
	},
	{
		ID:            4,
		Title:         "d",
		Author:        "E",
		YearPublished: 1993,
	},
	{
		ID:            5,
		Title:         "d",
		Author:        "E",
		YearPublished: 1993,
	},
	{
		ID:            6,
		Title:         "d",
		Author:        "E",
		YearPublished: 1993,
	},
	{
		ID:            7,
		Title:         "d",
		Author:        "E",
		YearPublished: 1993,
	},
	{
		ID:            8,
		Title:         "d",
		Author:        "E",
		YearPublished: 1993,
	},
	{
		ID:            9,
		Title:         "d",
		Author:        "E",
		YearPublished: 1993,
	},
	{
		ID:            10,
		Title:         "d",
		Author:        "E",
		YearPublished: 1993,
	},
}
