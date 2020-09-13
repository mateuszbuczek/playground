package utils

import (
	"github.com/golang/protobuf/ptypes"
	"github.com/google/uuid"
	"github.com/mateuszbuczek/playground/grpc/w-go/pb"
)

func NewCPU() *pb.CPU {
	return &pb.CPU{
		Brand:         randomStringFromSet("a", "b"),
		Name:          randomStringFromSet("c", "d"),
		NumberCores:   uint32(randomInt(1, 5)),
		NumberThreads: uint32(randomInt(5, 10)),
		MinGhz:        randomFloat(1, 3),
		MaxGhz:        randomFloat(3, 5),
	}
}

func NewGPU() *pb.GPU {
	return &pb.GPU{
		Brand:  randomStringFromSet("a", "b"),
		Name:   randomStringFromSet("c", "d"),
		MinGhz: randomFloat(1, 3),
		MaxGhz: randomFloat(3, 5),
		Memory: &pb.Memory{
			Value: uint64(randomInt(12, 24)),
			Unit:  pb.Memory_GIGABYTE,
		},
	}
}

func NewRAM() *pb.Memory {
	return &pb.Memory{
		Value: uint64(randomInt(12, 14)),
		Unit:  pb.Memory_GIGABYTE,
	}
}

func NewStorage() *pb.Storage {
	return &pb.Storage{
		Driver: pb.Storage_SSD,
		Memory: &pb.Memory{
			Value: uint64(randomInt(8, 10)),
			Unit:  pb.Memory_MEGABYTE,
		},
	}
}

func NewScreen() *pb.Screen {
	return &pb.Screen{
		SizeInch: float32(randomInt(100, 200)),
		Resolution: &pb.Screen_Resolution{
			Width:  uint32(randomInt(1000, 2000)),
			Height: uint32(randomInt(1000, 4000)),
		},
		Panel:      pb.Screen_LED,
		Multitouch: randomBool(),
	}
}

func NewLaptop() *pb.Laptop {
	newUUID, _ := uuid.NewUUID()

	return &pb.Laptop{
		Id:          newUUID.String(),
		Brand:       randomStringFromSet("a", "b"),
		Name:        randomStringFromSet("c", "d"),
		Cpu:         NewCPU(),
		Memory:      NewRAM(),
		Gpus:        []*pb.GPU{NewGPU()},
		Storages:    []*pb.Storage{NewStorage(), NewStorage()},
		Screen:      NewScreen(),
		Weight:      &pb.Laptop_WeightKg{WeightKg: 1.2},
		PriceUsd:    randomFloat(100, 500),
		ReleaseYear: uint32(randomInt(2010, 2020)),
		UpdatedAt:   ptypes.TimestampNow(),
	}
}
