package com.example.demo.typecr;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CronTabStatus {
    private String labelSelector;
    private int replicas;
}
