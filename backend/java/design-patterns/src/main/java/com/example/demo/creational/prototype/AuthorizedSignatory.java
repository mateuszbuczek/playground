package com.example.demo.creational.prototype;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorizedSignatory implements Cloneable {

    private String name;
    private String designation;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
