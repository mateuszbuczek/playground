package service;

import com.example.pb.Laptop;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class InMemoryLaptopStore implements LaptopStore {
    private ConcurrentMap<String, com.example.pb.Laptop> data = new ConcurrentHashMap<>();

    @Override
    public void Save(Laptop laptop) throws Exception {
        if (data.containsKey(laptop.getId())) {
            throw new IllegalArgumentException("already exists");
        }

        Laptop other = laptop.toBuilder().build();
        data.put(other.getId(), other);
    }

    @Override
    public Laptop Find(String id) {
        if (!data.containsKey(id)) {
            return null;
        }

        return data.get(id).toBuilder().build();
    }
}
