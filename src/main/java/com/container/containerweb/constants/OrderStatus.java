package com.container.containerweb.constants;

public enum  OrderStatus {

    //未付款
    UNPAID(9999),

    //已付款
    PAID(1000);

    private int code;

    OrderStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
