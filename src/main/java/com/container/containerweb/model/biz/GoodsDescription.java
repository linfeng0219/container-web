package com.container.containerweb.model.biz;

import javax.persistence.*;

@Table
@Entity
public class GoodsDescription {

    @Id
    @GeneratedValue
    private Integer id;

    private String description;

    private String imageHash;

    private Integer price;

    @ManyToOne
    private Merchant merchant;

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
}
