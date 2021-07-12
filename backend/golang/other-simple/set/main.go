package main

import (
	"errors"
	"fmt"
)

type Set struct {
	Elements map[string]struct{}
}

func NewSet() *Set {
	var set Set
	set.Elements = make(map[string]struct{})
	return &set
}

func (s *Set) Add(element string) {
	s.Elements[element] = struct{}{}
}

func (s *Set) Delete(element string) error {
	if _, exists := s.Elements[element]; !exists {
		return errors.New("element not present in set")
	}
	delete(s.Elements, element)
	return nil
}

func (s *Set) contains(element string) bool {
	_, exists := s.Elements[element]
	return exists
}

func (s *Set) List() {
	for k, _ := range s.Elements {
		fmt.Println(k)
	}
}

func main() {

}
