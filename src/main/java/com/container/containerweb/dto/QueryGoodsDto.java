package com.container.containerweb.dto;

public class QueryGoodsDto extends PageDto {
    private String  goodsDescriptionId;

    private String goodsStatus;

    private String machineSerial;

    private String createTime;

    public String getGoodsDescriptionId() {
        return goodsDescriptionId;
    }

    public void setGoodsDescriptionId(String goodsDescriptionId) {
        this.goodsDescriptionId = goodsDescriptionId;
    }

    public String getGoodsStatus() {
        return goodsStatus;
    }

    public void setGoodsStatus(String goodsStatus) {
        this.goodsStatus = goodsStatus;
    }

    public String getMachineSerial() {
        return machineSerial;
    }

    public void setMachineSerial(String machineSerial) {
        this.machineSerial = machineSerial;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
