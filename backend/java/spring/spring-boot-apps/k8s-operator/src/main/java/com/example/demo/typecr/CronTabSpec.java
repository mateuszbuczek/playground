package com.example.demo.typecr;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CronTabSpec {
    private String cronSpec;
    private String image;
    private int replicas;
}
