package com.example.vavrweb.user.registration.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String username;

    private String password;

    private String email;
}

