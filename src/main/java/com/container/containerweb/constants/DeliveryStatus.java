package com.container.containerweb.constants;

public enum DeliveryStatus {
    //未完成
    UNCOMPLETE(1000),
    //已完成
    COMPLETE(2000);

    private int code;

    DeliveryStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
