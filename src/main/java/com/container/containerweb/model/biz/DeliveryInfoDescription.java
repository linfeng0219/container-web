package com.container.containerweb.model.biz;

import javax.persistence.*;

@Table
@Entity
public class DeliveryInfoDescription {
    
    @Id
    @GeneratedValue
    private Integer id;
    
    private String goodsDesc;
    
    private Integer amount;

    private Integer sheetId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGoodsDesc() {
        return goodsDesc;
    }

    public void setGoodsDesc(String goodsDesc) {
        this.goodsDesc = goodsDesc;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getSheetId() {
        return sheetId;
    }

    public void setSheetId(Integer sheetId) {
        this.sheetId = sheetId;
    }
}
