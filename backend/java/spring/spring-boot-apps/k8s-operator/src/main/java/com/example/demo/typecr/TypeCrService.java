package com.example.demo.typecr;

import io.fabric8.kubernetes.api.model.apiextensions.v1beta1.CustomResourceDefinition;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientException;
import io.fabric8.kubernetes.client.Watcher;
import io.fabric8.kubernetes.client.dsl.MixedOperation;
import io.fabric8.kubernetes.client.dsl.base.CustomResourceDefinitionContext;
import io.fabric8.kubernetes.internal.KubernetesDeserializer;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class TypeCrService {

    @Value("classpath:crontab/crd.yaml")
    Resource crdFile;

    @Value("classpath:crontab/cr.yaml")
    Resource crFile;

    @SneakyThrows
    @EventListener(ApplicationStartedEvent.class)
    public void handle() {
        try (KubernetesClient client = new DefaultKubernetesClient()) {
            CustomResourceDefinition crd = client.customResourceDefinitions()
                    .load(crdFile.getInputStream()).get();

            client.customResourceDefinitions().createOrReplace(crd);

            MixedOperation<CronTab,
                    CronTabList,
                    DoneableCronTab,
                    io.fabric8.kubernetes.client.dsl.Resource<CronTab, DoneableCronTab>> cronClient =
                    client.customResources(crd, CronTab.class, CronTabList.class, DoneableCronTab.class);

            CronTab cronTab = cronClient.load(crFile.getInputStream()).get();
            cronClient.inNamespace("default").create(cronTab);

            KubernetesDeserializer.registerCustomKind("stable.example.com" + "/v1", "CronTab", CronTab.class);
            cronClient.inNamespace("default").watch(new Watcher<CronTab>() {
                @Override
                public void eventReceived(Action action, CronTab cronTab) {
                    System.out.println(action.toString() + " " + cronTab.getMetadata().getName());
                }

                @Override
                public void onClose(KubernetesClientException e) {
                    System.out.println("watch closed...");
                }
            });

            Thread.sleep(600  * 1000);
        }
    }
}
