package com.example.demo.translator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class BlogText {

    private String text;

    public void read() {
        System.out.println(this.text);
    }

    public BlogText create(String text) {
        return new BlogText(text);
    }
}
