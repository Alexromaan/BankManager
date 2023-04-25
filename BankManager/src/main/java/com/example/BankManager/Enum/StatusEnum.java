package com.example.BankManager.Enum;

public enum StatusEnum {
    INVALID("INVALID"),
    PENDING("PENDING"),
    SETTLED("SETTLED"),
    FUTURE("FUTURE");

    private final String statusCode;

    private StatusEnum(String statusCode) {
        this.statusCode = statusCode;
    }

    public String get() {
        return statusCode;
    }
}
