package com.example.demo.model.enums;

import java.util.Locale;

public enum UserRole {
    ROLE_ADMINISTRATOR("ROLE_ADMINISTRATOR"),
    ROLE_SELLER("ROLE_SELLER"),
    ROLE_CLIENT("ROLE_CLIENT");

    UserRole(String role) {
    }

    public static UserRole fromValue(String value) {
        switch (value.toUpperCase(Locale.ROOT)) {
            case "ROLE_ADMINISTRATOR": return ROLE_ADMINISTRATOR;
            case "ROLE_SELLER": return ROLE_SELLER;
            case "ROLE_CLIENT": return ROLE_CLIENT;
        }
        return null;
    }
}
