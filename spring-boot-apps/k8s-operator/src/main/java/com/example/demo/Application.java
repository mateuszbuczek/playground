package com.example.demo;

import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.Resource;

import java.util.List;

@SpringBootApplication
public class Application {

    @Value("classpath:sample_pod.yaml")
    Resource resourceFile;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @SneakyThrows
    @EventListener(ApplicationStartedEvent.class)
    public void handle() {
        DefaultKubernetesClient client = new DefaultKubernetesClient();

        printPodsInDefaultNamespace(client);

        Pod podToBeCreated = client.pods().load(resourceFile.getFile()).get();

        Pod pod = client.pods()
                .inNamespace("default")
                .create(podToBeCreated);

        printPodsInDefaultNamespace(client);

        client.pods().inNamespace("default").withName("sample-pod").delete();

        printPodsInDefaultNamespace(client);
    }

    private void printPodsInDefaultNamespace(DefaultKubernetesClient client) {
        List<Pod> list = client.pods().inNamespace("default").list().getItems();
        if (list.size() > 0) {
            list.forEach(System.out::println);
        } else {
            System.out.println("no pods found");
        }
    }
}
