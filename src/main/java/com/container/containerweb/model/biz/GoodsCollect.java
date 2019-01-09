package com.container.containerweb.model.biz;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table
@Entity
public class GoodsCollect {
    @Id
    @GeneratedValue
    private Integer id;

    private Long createTime;

    private String goodsDesc;

    @ColumnDefault("0")
    private Integer price;

    @ColumnDefault("0")
    private Integer prepareDeliverAmount;

    @ColumnDefault("0")
    private Integer actualDeliverAmount;

    @ColumnDefault("0")
    private Integer soldAmount;

    @ColumnDefault("0")
    private Integer singleTotalAmount;

    private String machineSerial;

    private String machineLocation;

    @ColumnDefault("0")
    private Integer shouldRecycleAmount;

    @ColumnDefault("0")
    private Integer allTotalAmount;

    private String deliverBatchNo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getGoodsDesc() {
        return goodsDesc;
    }

    public void setGoodsDesc(String goodsDesc) {
        this.goodsDesc = goodsDesc;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getPrepareDeliverAmount() {
        return prepareDeliverAmount;
    }

    public void setPrepareDeliverAmount(Integer prepareDeliverAmount) {
        this.prepareDeliverAmount = prepareDeliverAmount;
    }

    public Integer getActualDeliverAmount() {
        return actualDeliverAmount;
    }

    public void setActualDeliverAmount(Integer actualDeliverAmount) {
        this.actualDeliverAmount = actualDeliverAmount;
    }

    public Integer getSoldAmount() {
        return soldAmount;
    }

    public void setSoldAmount(Integer soldAmount) {
        this.soldAmount = soldAmount;
    }

    public Integer getSingleTotalAmount() {
        return singleTotalAmount;
    }

    public void setSingleTotalAmount(Integer singleTotalAmount) {
        this.singleTotalAmount = singleTotalAmount;
    }

    public String getMachineSerial() {
        return machineSerial;
    }

    public void setMachineSerial(String machineSerial) {
        this.machineSerial = machineSerial;
    }

    public Integer getShouldRecycleAmount() {
        return shouldRecycleAmount;
    }

    public void setShouldRecycleAmount(Integer shouldRecycleAmount) {
        this.shouldRecycleAmount = shouldRecycleAmount;
    }

    public Integer getAllTotalAmount() {
        return allTotalAmount;
    }

    public void setAllTotalAmount(Integer allTotalAmount) {
        this.allTotalAmount = allTotalAmount;
    }

    public String getMachineLocation() {
        return machineLocation;
    }

    public void setMachineLocation(String machineLocation) {
        this.machineLocation = machineLocation;
    }

    public String getDeliverBatchNo() {
        return deliverBatchNo;
    }

    public void setDeliverBatchNo(String deliverBatchNo) {
        this.deliverBatchNo = deliverBatchNo;
    }
}
