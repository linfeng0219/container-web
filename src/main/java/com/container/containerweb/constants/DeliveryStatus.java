package com.container.containerweb.constants;

public enum DeliveryStatus {
    //待上架
    FORSTORE(1000),
    //在售
    FORSALR(5000),
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
