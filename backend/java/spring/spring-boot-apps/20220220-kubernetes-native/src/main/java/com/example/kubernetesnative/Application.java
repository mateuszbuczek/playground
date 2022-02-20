package com.example.kubernetesnative;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.availability.AvailabilityChangeEvent;
import org.springframework.boot.availability.LivenessState;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.annotation.Id;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    HealthIndicator healthIndicator() {
        return () -> Health.status("test-health-indicator").build();
    }

    @Bean
    ApplicationRunner runner(CustomerRepository repository) {
        return args -> {
            var names = Flux.just("first", "second", "third")
                    .map(name -> new Customer(null, name))
                    .flatMap(repository::save);

            names
                    .thenMany(repository.findAll())
                    .subscribe(System.out::println);
        };
    }
}

@Controller
@ResponseBody
@RequiredArgsConstructor
class CustomerController {

    private final CustomerRepository repository;

    @GetMapping("/customers")
    Flux<Customer> get() {
        return repository.findAll();
    }
}

interface CustomerRepository extends ReactiveCrudRepository<Customer, Integer> {}

record Customer (@Id Integer id, String name){}

@Controller
@ResponseBody
@RequiredArgsConstructor
class KubernetesProbeRestController {
    private final ApplicationContext context;

    @PostMapping("/down")
    void down() {
        AvailabilityChangeEvent.publish(context, LivenessState.BROKEN);
    }
}
