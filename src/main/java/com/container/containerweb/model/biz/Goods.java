package com.container.containerweb.model.biz;

import com.container.containerweb.model.rbac.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Objects;

@Table
@Entity
public class Goods {

    @Id
    @GeneratedValue
    private Long id;

    private Long createTime;

    private int status;

    private String barcode;

    private String comment;

    private String batchNo;

    @ManyToOne
    private GoodsDescription goodsDescription;

    private String idx;

    @ManyToOne
    private VendingMachine vendingMachine;

    @ManyToOne
    private User deliveryman;

    public Goods() {
    }

    public Goods(int status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public User getDeliveryman() {
        return deliveryman;
    }

    public void setDeliveryman(User deliveryman) {
        this.deliveryman = deliveryman;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Goods goods = (Goods) o;
        return Objects.equals(id, goods.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
