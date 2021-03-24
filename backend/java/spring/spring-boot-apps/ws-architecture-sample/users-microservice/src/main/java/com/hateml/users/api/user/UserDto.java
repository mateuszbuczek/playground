package com.hateml.users.api.user;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserDto {

    private Long id;
    private String email;
    private String firstName;
    private String lastName;
}
