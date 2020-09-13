package serializer

import (
	"github.com/golang/protobuf/proto"
	"io/ioutil"
)

func WriteProtobufToBinaryFile(message proto.Message, filename string) error {
	data, err := proto.Marshal(message)
	if err != nil {
		return err
	}

	if ioutil.WriteFile(filename, data, 0644) != nil {
		return err
	}

	return nil
}
