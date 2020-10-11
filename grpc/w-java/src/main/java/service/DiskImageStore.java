package service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class DiskImageStore implements ImageStore{
    private final String imageFolder;
    private final ConcurrentMap<String, ImageMetadata> data;

    public DiskImageStore(String imageFolder) {
        this.imageFolder = imageFolder;
        this.data = new ConcurrentHashMap<>();
    }

    @Override
    public String save(String laptopId, String imageType, ByteArrayOutputStream imageData) throws IOException {
        String imageId = UUID.randomUUID().toString();
        String imagePath = String.format("%s/%s%s", imageFolder, imageId, imageType);

        FileOutputStream fileOutputStream = new FileOutputStream(imagePath);
        imageData.writeTo(fileOutputStream);
        fileOutputStream.close();

        ImageMetadata imageMetadata = new ImageMetadata(laptopId, imageType, imagePath);
        data.put(imageId, imageMetadata);

        return imageId;
    }

    public static class ImageMetadata {
        private String laptopId;
        private String type;
        private String path;

        public ImageMetadata(String laptopId, String type, String path) {
            this.laptopId = laptopId;
            this.type = type;
            this.path = path;
        }

        public String getLaptopId() {
            return laptopId;
        }

        public void setLaptopId(String laptopId) {
            this.laptopId = laptopId;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }
}
