package com.container.containerweb.dto;

public class QueryOrderDto extends PageDto {

    private Long from;

    private Long to;

    private Integer merchantId;

    private String machineSerial;

    private Integer goodsDescId;

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

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public String getMachineSerial() {
        return machineSerial;
    }

    public void setMachineSerial(String machineSerial) {
        this.machineSerial = machineSerial;
    }

    public Integer getGoodsDescId() {
        return goodsDescId;
    }

    public void setGoodsDescId(Integer goodsDescId) {
        this.goodsDescId = goodsDescId;
    }
}
