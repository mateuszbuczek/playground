package service;

import io.grpc.Context;

public interface LaptopStore {

    void Save(com.example.pb.Laptop laptop) throws Exception;
    com.example.pb.Laptop Find(String id);
    void Search(Context ctx, com.example.pb.Filter filter, LaptopStream stream) throws InterruptedException;
}
