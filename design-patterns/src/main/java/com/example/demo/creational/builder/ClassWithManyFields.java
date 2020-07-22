package com.example.demo.creational.builder;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ClassWithManyFields {

    private String f1;
    private String f2;
    private String f3;
    private String f4;
    private String f5;
    private List<String> f6;

    public static Builder builder(String f1, String f2) {
        return new Builder(f1, f2);
    }

    public static class Builder {
        private final String f1;
        private final String f2;
        private String f3;
        private String f4;
        private String f5;
        private List<String> f6;

        public Builder(String f1, String f2) {
            this.f1 = f1;
            this.f2 = f2;
            this.f6 = new ArrayList<>();
        }

        public Builder f3(String f3) {
            this.f3 = f3;
            return this;
        }

        public Builder f4(String f4) {
            this.f4 = f4;
            return this;
        }

        public Builder f5(String f5) {
            this.f5 = f5;
            return this;
        }

        public Builder f6(List<String> f6) {
            this.f6 = f6;
            return this;
        }

        public ClassWithManyFields build() {
            return new ClassWithManyFields(f1, f2, f3, f4, f5, f6);
        }
    }
}
