package com.container.containerweb.constants;

public enum GoodsStatus {
    //已生产
    PRODUCED(1000),
    //已入库
    STORED(1001),
    //售出
    SOLD(1002);

    private int code;


    GoodsStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
