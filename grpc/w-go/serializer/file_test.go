package serializer

import (
	"github.com/mateuszbuczek/playground/grpc/w-go/utils"
	"github.com/stretchr/testify/require"
	"testing"
)

func TestWriteProtobufToBinaryFile(t *testing.T) {
	t.Parallel()

	binaryFileName := ",/tmp/laptop.bin"
	laptop1 := utils.NewLaptop()
	err := WriteProtobufToBinaryFile(laptop1, binaryFileName)
	require.NoError(t, err)
}
