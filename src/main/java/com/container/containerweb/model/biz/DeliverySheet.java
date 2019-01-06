package com.container.containerweb.model.biz;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class DeliverySheet {
    @Id
    @GeneratedValue
    private Integer id;

    private String batchNo;//批次号

    private String deliveryman;//配货员

    private Integer machineCapacity;//机柜容量

    private Integer total;//配送数量

    private String machineLocation;//机柜地址

    private Integer merchantId;//运营商

    private Integer status;//状态

    @Transient
    private List<DeliveryInfoDescription> descriptions;

    public DeliverySheet() {
    }

    public DeliverySheet(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getDeliveryman() {
        return deliveryman;
    }

    public void setDeliveryman(String deliveryman) {
        this.deliveryman = deliveryman;
    }

    public Integer getMachineCapacity() {
        return machineCapacity;
    }

    public void setMachineCapacity(Integer machineCapacity) {
        this.machineCapacity = machineCapacity;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getMachineLocation() {
        return machineLocation;
    }

    public void setMachineLocation(String machineLocation) {
        this.machineLocation = machineLocation;
    }

    public List<DeliveryInfoDescription> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(List<DeliveryInfoDescription> descriptions) {
        this.descriptions = descriptions;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
