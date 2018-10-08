package com.container.containerweb.model.biz;

import javax.persistence.*;
import java.util.List;

@Table
@Entity
public class GoodsOrder {

    @Id
    @GeneratedValue
    private long id;

    private String serial;

    @OneToMany
    private List<Goods> goods;

    private Long createTime;

    private Long paymentTime;//付款时间

    private Long paymentMode;//付款方式

    private int payment;//单位‘分’

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
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

    public Long getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(Long paymentMode) {
        this.paymentMode = paymentMode;
    }

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

    public List<Goods> getGoods() {
        return goods;
    }

    public void setGoods(List<Goods> goods) {
        this.goods = goods;
    }
}
