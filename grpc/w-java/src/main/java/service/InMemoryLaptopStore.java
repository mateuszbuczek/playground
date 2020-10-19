package service;

import com.example.pb.Filter;
import com.example.pb.Laptop;
import io.grpc.Context;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

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

    @Override
    public void Search(Context ctx, Filter filter, LaptopStream stream) throws InterruptedException {
        for (Map.Entry<String, Laptop> entry: data.entrySet()) {
            if (ctx.isCancelled()) {
                return;
            }
            TimeUnit.SECONDS.sleep(1);
            Laptop value = entry.getValue();
            if (isQualified(filter, value)) {
                stream.Send(value);
            }
        }
    }

    private boolean isQualified(Filter filter, Laptop value) {
        return true;
    }
}
