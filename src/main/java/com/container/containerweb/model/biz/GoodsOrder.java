package com.container.containerweb.model.biz;

import javax.persistence.*;

@Table
@Entity
public class GoodsOrder {

    @Id
    @GeneratedValue
    private Long id;

    private String orderNo;

    private String machineSerial;

    @OneToOne
    private Goods goods;

    private Long createTime;

    private Long paymentTime;//付款时间

    private Integer paymentMode;//付款方式

    private Integer payment;//单位‘分’

    private Integer status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(Long paymentTime) {
        this.paymentTime = paymentTime;
    }

    public Integer getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(Integer paymentMode) {
        this.paymentMode = paymentMode;
    }

    public Integer getPayment() {
        return payment;
    }

    public void setPayment(Integer payment) {
        this.payment = payment;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public String getMachineSerial() {
        return machineSerial;
    }

    public void setMachineSerial(String machineSerial) {
        this.machineSerial = machineSerial;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
