package dev.hateml.accountrabbitmqproducer.interfaces.error;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorResponse {

    NOT_FOUND("Not found"),
    BAD_REQUEST("Bad request");

    private final String message;

    ErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
