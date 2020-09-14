package serializer_test

import (
	"github.com/golang/protobuf/proto"
	"github.com/mateuszbuczek/playground/grpc/w-go/pb"
	"github.com/mateuszbuczek/playground/grpc/w-go/serializer"
	"github.com/mateuszbuczek/playground/grpc/w-go/utils"
	"github.com/stretchr/testify/require"
	"testing"
)

func TestWriteProtobufToBinaryFile(t *testing.T) {
	t.Parallel()

	binaryFileName := "./laptop.bin"
	laptop1 := utils.NewLaptop()
	err := serializer.WriteProtobufToBinaryFile(laptop1, binaryFileName)
	require.NoError(t, err)

	laptop2 := &pb.Laptop{}
	err = serializer.ReadProtobufFromBinary(binaryFileName, laptop2)
	require.NoError(t, err)
	require.True(t, proto.Equal(laptop1, laptop2))
}
