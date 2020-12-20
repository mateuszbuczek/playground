package com.example.demo;

import io.fabric8.kubernetes.api.model.apiextensions.v1beta1.CustomResourceDefinition;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientException;
import io.fabric8.kubernetes.client.Watcher;
import io.fabric8.kubernetes.client.dsl.base.CustomResourceDefinitionContext;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Service
public class TypelessCrService {

    @Value("classpath:animal/animal-crd.yaml")
    Resource crdFile;

    @Value("classpath:animal/animal-cr.yaml")
    Resource crFile;

    private static final CountDownLatch closeLatch = new CountDownLatch(1);

    /**
     * declare crd
     * create cr
     * read cr
     * */
    @SneakyThrows
    @EventListener(ApplicationStartedEvent.class)
    public void handle() {
        try (final KubernetesClient client = new DefaultKubernetesClient()) {
            createCrd(client);

            CustomResourceDefinitionContext animalCrdContext = new CustomResourceDefinitionContext.Builder()
                    .withName("animals.jungle.example.com")
                    .withGroup("jungle.example.com")
                    .withScope("Namespaced")
                    .withVersion("v1")
                    .withPlural("animals")
                    .build();

            Map<String, Object> cr = client
                    .customResource(animalCrdContext)
                    .load(crFile.getInputStream());

            client.customResource(animalCrdContext).create("default", cr);

            client.customResource(animalCrdContext).watch("default", new Watcher<>() {
                @Override
                public void eventReceived(Action action, String resource) {
                    try {
                        System.out.println(action + " : " + resource);
                        //json can be deserialized here
                    } catch (Exception ignored) {
                    }
                }

                @Override
                public void onClose(KubernetesClientException cause) {
                    closeLatch.countDown();
                    if (cause != null) {
                        System.out.println(cause.getMessage());
                    }
                }
            });

            closeLatch.await(10, TimeUnit.MINUTES);
        }
    }

    private void createCrd(KubernetesClient client) throws IOException {
        CustomResourceDefinition crd = client.customResourceDefinitions()
                .load(crdFile.getFile())
                .get();

        client.customResourceDefinitions().createOrReplace(crd);
    }
}
