package com.hateml.users.api.user;

import com.hateml.users.domain.User;
import org.springframework.stereotype.Component;

@Component
public class UserDtoFactory {

    public UserDto create(User user) {

        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }
}
