package main

import (
	"errors"
	"io"
)

func main() {
	ReadSomething()
}

type BadReader struct {
	err error
}

func ReadSomething() error {
	var r io.Reader = BadReader{err: errors.New("my reader")}

	if _, err := r.Read([]byte("test")); err != nil {
		return err
	}

	return nil
}

func (br BadReader) Read(p []byte) (n int, err error) {
	return -1, br.err
}
