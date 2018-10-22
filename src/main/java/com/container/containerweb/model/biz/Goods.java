package com.container.containerweb.model.biz;

import javax.persistence.*;

@Table
@Entity
public class Goods {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private int price;

    private Long createTime;

    private int status;

    private String barcode;

    private String comment;

    @ManyToOne
    private GoodsDescription goodsDescription;

    private String idx;

    @ManyToOne
    private VendingMachine vendingMachine;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public VendingMachine getVendingMachine() {
        return vendingMachine;
    }

    public void setVendingMachine(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public GoodsDescription getGoodsDescription() {
        return goodsDescription;
    }

    public void setGoodsDescription(GoodsDescription goodsDescription) {
        this.goodsDescription = goodsDescription;
    }
}
