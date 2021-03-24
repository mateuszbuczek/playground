package service

import (
	"bytes"
	"fmt"
	"github.com/google/uuid"
	"os"
	"sync"
)

type ImageStore interface {
	Save(laptopId string, imageType string, imageData bytes.Buffer) (string, error)
}

type DiskImageStore struct {
	mutex       sync.RWMutex
	imageFolder string
	images      map[string]*ImageInfo
}

type ImageInfo struct {
	LaptopId string
	Type     string
	Path     string
}

func NewDiskImageStore(imageFolder string) *DiskImageStore {
	return &DiskImageStore{
		imageFolder: imageFolder,
		images:      make(map[string]*ImageInfo),
	}
}

func (store DiskImageStore) Save(laptopId string, imageType string, imageData bytes.Buffer) (string, error) {
	imageId, _ := uuid.NewRandom()

	imagePath := fmt.Sprintf("%s/%s%s", store.imageFolder, imageId, imageType)

	file, _ := os.Create(imagePath)

	imageData.WriteTo(file)

	store.mutex.Lock()
	defer store.mutex.Unlock()

	store.images[imageId.String()] = &ImageInfo{
		LaptopId: laptopId,
		Type:     imageType,
		Path:     imagePath,
	}

	return imageId.String(), nil
}
