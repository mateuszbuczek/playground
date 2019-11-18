package com.hateml.users.api.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorResponse {

    UNEXPECTED_ERROR("Something went wrong");

    private final String message;
}
