package com.example.demo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

@Getter
@ToString
@RequiredArgsConstructor
public class Customer {

  @Id @Setter private Long id;

  private final String firstName;
  private final String lastName;
}
