package com.container.containerweb.model.biz;

import javax.persistence.*;

@Table
@Entity
public class GoodsDescription {

    @Id
    @GeneratedValue
    private Integer id;

    private String description;//类型名字

    private String imageHash;//图片地址

    private Integer price;//价格

    private String barcode;//二维码

    @ManyToOne
    private Merchant merchant;//商户

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageHash() {
        return imageHash;
    }

    public void setImageHash(String imageHash) {
        this.imageHash = imageHash;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
}
