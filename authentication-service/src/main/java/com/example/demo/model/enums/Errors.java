package com.example.demo.model.enums;

public enum Errors {
    INCOMPLETE_INFORMATION("INCOMPLETE_INFORMATION"),
    INVALID_INPUT("INVALID_INPUT"),
    EMAIL_ALREADY_REGISTERED("EMAIL_ALREADY_REGISTERED");

    private final String value;

    Errors(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
