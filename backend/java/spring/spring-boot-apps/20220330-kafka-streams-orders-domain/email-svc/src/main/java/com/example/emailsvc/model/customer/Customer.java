package com.example.emailsvc.model.customer;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Customer {

    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String level;

}
