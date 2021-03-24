package com.hateml.users.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull(message = "Email cannot be null")
    @Column(unique = true)
    private String email;

    @NotNull(message = "Password cannot be null")
    @Size(min = 8, message = "Password must have at least 8 characters")
    private String password;

    @NotNull(message = "First name cannot be null")
    @Size(min = 2, message = "First name cannot be less than two characters")
    private String firstName;

    @NotNull(message = "Last name cannot be null")
    @Size(min = 2, message = "Last name cannot be less than two characters")
    private String lastName;
}
