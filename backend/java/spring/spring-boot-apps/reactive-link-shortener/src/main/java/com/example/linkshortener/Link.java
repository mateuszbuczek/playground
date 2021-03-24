package com.example.linkshortener;

import lombok.Value;

@Value
public class Link {
    String originalLink;
    String key;
}
