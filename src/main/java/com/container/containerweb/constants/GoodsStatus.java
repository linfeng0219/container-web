package com.container.containerweb.constants;

public enum GoodsStatus {
    //待上架(配送单生成后就为该状态)
    PRODUCED(3000),
    //不上架（由机柜设定，一个批次没有上架的视为不上架）
    GIVEUP(4000),
    //待售（由机柜设定，一个批次中匹配到二维码的为待售）
    FORSALE(5000),
    //已售
    SOLD(6000),
    //销毁（由机柜设定，条件为配送人到机柜手动设定）
    OUT(7000);

    private int code;


    GoodsStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
