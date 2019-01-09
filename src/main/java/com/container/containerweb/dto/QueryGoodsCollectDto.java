package com.container.containerweb.dto;

public class QueryGoodsCollectDto extends PageDto {

    private String deliverBatchNo;

    private String machineSerial;

    private String goodsDesc;

    private Long from;

    private Long to;

    public Long getFrom() {
        return from;
    }

    public void setFrom(Long from) {
        this.from = from;
    }

    public Long getTo() {
        return to;
    }

    public void setTo(Long to) {
        this.to = to;
    }

    public String getDeliverBatchNo() {
        return deliverBatchNo;
    }

    public void setDeliverBatchNo(String deliverBatchNo) {
        this.deliverBatchNo = deliverBatchNo;
    }

    public String getMachineSerial() {
        return machineSerial;
    }

    public void setMachineSerial(String machineSerial) {
        this.machineSerial = machineSerial;
    }

    public String getGoodsDesc() {
        return goodsDesc;
    }

    public void setGoodsDesc(String goodsDesc) {
        this.goodsDesc = goodsDesc;
    }
}
