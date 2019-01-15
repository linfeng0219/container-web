package com.container.containerweb.dto;

import com.container.containerweb.model.biz.Goods;

import java.util.List;
import java.util.stream.Collectors;

public class MachineGoodsBinding {
    private String serial;

    private List<Goods> goodsList;

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public List<Goods> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<Goods> goodsList) {
        this.goodsList = goodsList;
    }
}
