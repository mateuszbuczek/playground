package com.example.demo.typecr;

import io.fabric8.kubernetes.api.model.Namespaced;
import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.fabric8.kubernetes.client.CustomResource;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CronTab extends CustomResource implements Namespaced {
    private CronTabSpec spec;
    private CronTabStatus status;

    @Override
    public ObjectMeta getMetadata() {
        return super.getMetadata();
    }
}