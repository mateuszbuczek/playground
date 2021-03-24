package com.example.demo.creational.prototype;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class PrototypeCapableDocument implements Cloneable {

    private String vendorName;
    private String content;

    public abstract PrototypeCapableDocument cloneDocument() throws CloneNotSupportedException;
}
