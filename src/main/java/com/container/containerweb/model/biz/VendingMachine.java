package com.container.containerweb.model.biz;

import com.container.containerweb.model.rbac.User;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class VendingMachine {

    @Id
    @GeneratedValue
    private int id;

    private String serial;//序列号

    private String location;

    private int status;

    private Long updateTime;

    private Long createTime;

    @OneToMany
    private List<Goods> goods;

    @OneToOne
    private User master;//负责人

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public User getMaster() {
        return master;
    }

    public void setMaster(User master) {
        this.master = master;
    }

    public List<Goods> getGoods() {
        return goods;
    }

    public void setGoods(List<Goods> goods) {
        this.goods = goods;
    }
}
