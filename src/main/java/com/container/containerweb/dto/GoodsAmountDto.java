package com.container.containerweb.dto;

public class GoodsAmountDto {
    private Integer id;

    private Integer amount;

    private Integer machineId;

    public GoodsAmountDto(Integer id, Integer amount) {
        this.id = id;
        this.amount = amount;
    }
    public static GoodsAmountDto newGoodsAmountDto(String id, String amount) {
        int _id = Integer.valueOf(id);
        int _amount = Integer.valueOf(amount);
        return new GoodsAmountDto(_id, _amount);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
