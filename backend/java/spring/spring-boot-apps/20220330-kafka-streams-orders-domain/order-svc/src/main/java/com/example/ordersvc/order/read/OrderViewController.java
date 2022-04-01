package com.example.ordersvc.order.read;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderViewController {

    private final StreamsBuilderFactoryBean streamsBuilderFactoryBean;

    @GetMapping
    public Map<String, String> getOrders() {
        ReadOnlyKeyValueStore<String, String> store = getStore();

        Map<String, String> result = new LinkedHashMap<>();
        store.all().forEachRemaining(entry -> result.put(entry.key, entry.value));
        return result;
    }

    @GetMapping("/{id}")
    public Map<String, String> getOrder(@PathVariable String id) {
        ReadOnlyKeyValueStore<String, String> store = getStore();

        Map<String, String> result = new LinkedHashMap<>();
        Optional.ofNullable(store.get(id))
                .ifPresent(entry -> result.put(id, entry));
        return result;
    }

    private ReadOnlyKeyValueStore<String, String> getStore() {
        KafkaStreams kafkaStreams = streamsBuilderFactoryBean.getKafkaStreams();

        return kafkaStreams.store(StoreQueryParameters.fromNameAndType(
                KafkaStreamOrderTableConfig.ORDERS_STORE_NAME,
                QueryableStoreTypes.keyValueStore()));
    }
}
