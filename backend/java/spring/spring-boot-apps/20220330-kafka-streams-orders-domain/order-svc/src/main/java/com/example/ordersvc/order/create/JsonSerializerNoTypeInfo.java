package com.example.ordersvc.order.create;

import org.springframework.kafka.support.serializer.JsonSerializer;

public class JsonSerializerNoTypeInfo<T> extends JsonSerializer<T> {

    public JsonSerializerNoTypeInfo() {
        super();
        noTypeInfo();
    }
}
