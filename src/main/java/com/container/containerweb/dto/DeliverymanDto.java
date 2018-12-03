package com.container.containerweb.dto;

import java.util.List;

public class DeliverymanDto {
    private int userId;

    private String serial;

    private List<Long> goodsIds;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<Long> getGoodsIds() {
        return goodsIds;
    }

    public void setGoodsIds(List<Long> goodsIds) {
        this.goodsIds = goodsIds;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }
}
