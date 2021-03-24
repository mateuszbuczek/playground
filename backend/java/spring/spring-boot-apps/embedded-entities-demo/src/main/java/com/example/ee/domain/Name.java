package com.example.ee.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Name {

    @NotNull
    @Size(max = 40)
    private String firstName;

    @Size(max = 40)
    private String middleName;

    private String lastName;
}
