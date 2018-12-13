package com.container.containerweb.constants;

public enum GoodsStatus {
    //已生产
    PRODUCED(3000),
    //配送中
    DELIVERING(4000),
    //已入库
    STORED(5000),
    //售出
    SOLD(6000),
    //出库
    OUT(7000);

    private int code;


    GoodsStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
