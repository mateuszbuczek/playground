package service;

public interface LaptopStore {

    void Save(com.example.pb.Laptop laptop) throws Exception;
    com.example.pb.Laptop Find(String id);
}
