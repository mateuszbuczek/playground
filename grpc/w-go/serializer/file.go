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

func ReadProtobufFromBinary(filename string, message proto.Message) error {
	data, err := ioutil.ReadFile(filename)
	if err != nil {
		return err
	}

	return proto.Unmarshal(data, message)
}
