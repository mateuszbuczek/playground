package com.example.demo;

import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class SamplePodService {

    @Value("classpath:sample_pod.yaml")
    Resource resourceFile;

    /**
     * 1) load pod definition from file
     * 2) create it
     * 3) delete it
     */
    @SneakyThrows
    @EventListener(ApplicationStartedEvent.class)
    public void handle() {
        DefaultKubernetesClient client = new DefaultKubernetesClient();

        printPodsInDefaultNamespace(client);

        Pod podToBeCreated = loadPodDefinition(client);
        createPod(client, podToBeCreated);

        printPodsInDefaultNamespace(client);

        deletePod(client, podToBeCreated.getMetadata().getName());

        printPodsInDefaultNamespace(client);
    }

    private Boolean deletePod(DefaultKubernetesClient client, String podName) {
        return client.pods().inNamespace("default").withName(podName).delete();
    }

    private Pod createPod(DefaultKubernetesClient client, Pod podToBeCreated) {
        return client.pods()
                .inNamespace("default")
                .create(podToBeCreated);
    }

    private Pod loadPodDefinition(DefaultKubernetesClient client) throws IOException {
        return client.pods().load(resourceFile.getFile()).get();
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
