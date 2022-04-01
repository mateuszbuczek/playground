package com.example.emailsvc;

import com.example.emailsvc.model.customer.Customer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.TopologyDescription;
import org.apache.kafka.streams.kstream.GlobalKTable;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@Slf4j
@Component
@EnableScheduling
@RequiredArgsConstructor
public class ScheduledLogger {

    private final StreamsBuilderFactoryBean streamsBuilderFactoryBean;

    @Scheduled(cron = "*/10 * * * * *")
    void logCustomerTable() {
        TopologyDescription describe = streamsBuilderFactoryBean.getTopology().describe();
//        log.info("CURRENT TOPOLOGY: {}", describe);

        ReadOnlyKeyValueStore<String, Customer> store = streamsBuilderFactoryBean.getKafkaStreams().store(StoreQueryParameters.fromNameAndType(
                "customers-STATE-STORE-0000000005",
                QueryableStoreTypes.keyValueStore()));
        Map<String, Customer> customerMap = new HashMap<>();
        store.all().forEachRemaining((entry) -> customerMap.put(entry.key, entry.value));
        log.info("CUSTOMERS: {}", customerMap);
    }
}
