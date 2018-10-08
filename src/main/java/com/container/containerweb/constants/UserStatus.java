package com.container.containerweb.constants;

public enum UserStatus {
    ENABLED(0),
    DISABLED(1);

    private int code;

    UserStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
