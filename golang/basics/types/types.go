package main

import "fmt"

func mains() {
	var f float32 = 3.14
	firstName := "Arthur"
	fmt.Println("Hello from a module", f, firstName)

	/** pointers */
	var poi *string = new(string)

	/** de referencing pointer*/
	*poi = "ASD"
	fmt.Println(*poi)

	/** pointer pointing to var*/
	ppv := "BC"

	/** variable address */
	ptr := &ppv
	fmt.Println(ptr, *ptr)

	/** constant*/
	const pi = 3.1415

	/** arrs*/
	var arr [3]int
	arr[0] = 1

	arr2 := [3]int{1, 2, 3}
	fmt.Println(arr2)

	arr3 := []int{1, 2, 3}
	arr3 = append(arr3, 2, 4, 5)
	fmt.Println(arr3)

	// including 1, excluding 5
	s1 := arr3[1:5]
	fmt.Println(s1)

	/** maps */
	m := map[string]int{"foo": 42}
	fmt.Println(m, m["foo"])

	delete(m, "foo")

	/** structs */
	type user struct {
		ID        int
		FirstName string
		LastName  string
	}

	var u user
	u.ID = 1
	u.FirstName = "tom"

	u2 := user{ID: 1, FirstName: "t", LastName: "a"}
	fmt.Println(u, u2)
}
