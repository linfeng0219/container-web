package com.container.containerweb.constants;

public enum GoodsStatus {
    //已生产
    PRODUCED(3000),
    //已入库
    STORED(3001),
    //售出
    SOLD(3002),
    //出库
    OUT(3003);

    private int code;


    GoodsStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
