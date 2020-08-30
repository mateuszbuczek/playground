package main

import "github.com/mateuszbuczek/playground/golang/custom-data-types/organization"

func main() {
	p := organization.CreatePerson("Collin", "Tom", organization.CreateSSN("123-45-6789"))
	p1 := organization.CreatePerson("Collin", "Tom1", organization.CreateEUI("123-45-67892", "Germany"))
	_ = p.SetTwitterHandler("@asd")

	println(p.FullName())
	println(p.TwitterHandler().RedirectUrl())
	println(p.ID())
	println(p1.ID())
}
