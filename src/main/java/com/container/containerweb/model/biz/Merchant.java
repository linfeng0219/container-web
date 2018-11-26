package com.container.containerweb.model.biz;

import com.container.containerweb.base.WxPayConfig;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Merchant {

    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    private String alipayAppId;

    private String alipayPublicKey;

    private String alipayPrivateKey;

    private String alipayCallbackUrl;

    private String wxAppId;

    private String wxMchId;

    private String wxKey;

    private String wxCertPath;

    private String wxCallbackUrl;

    private Integer wxConnectTimeout;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlipayAppId() {
        return alipayAppId;
    }

    public void setAlipayAppId(String alipayAppId) {
        this.alipayAppId = alipayAppId;
    }

    public String getAlipayPrivateKey() {
        return alipayPrivateKey;
    }

    public void setAlipayPrivateKey(String alipayPrivateKey) {
        this.alipayPrivateKey = alipayPrivateKey;
    }

    public String getAlipayCallbackUrl() {
        return alipayCallbackUrl;
    }

    public void setAlipayCallbackUrl(String alipayCallbackUrl) {
        this.alipayCallbackUrl = alipayCallbackUrl;
    }

    public String getWxAppId() {
        return wxAppId;
    }

    public void setWxAppId(String wxAppId) {
        this.wxAppId = wxAppId;
    }

    public String getWxMchId() {
        return wxMchId;
    }

    public void setWxMchId(String wxMchId) {
        this.wxMchId = wxMchId;
    }

    public String getWxKey() {
        return wxKey;
    }

    public void setWxKey(String wxKey) {
        this.wxKey = wxKey;
    }

    public String getWxCertPath() {
        return wxCertPath;
    }

    public void setWxCertPath(String wxCertPath) {
        this.wxCertPath = wxCertPath;
    }

    public Integer getWxConnectTimeout() {
        return wxConnectTimeout;
    }

    public void setWxConnectTimeout(Integer wxConnectTimeout) {
        this.wxConnectTimeout = wxConnectTimeout;
    }

    @JsonIgnore
    public WxPayConfig getWxPayConfig(){
        return new WxPayConfig(this.wxAppId, this.wxMchId, this.wxKey, this.wxCertPath, this.wxConnectTimeout);
    }

    public String getWxCallbackUrl() {
        return wxCallbackUrl;
    }

    public void setWxCallbackUrl(String wxCallbackUrl) {
        this.wxCallbackUrl = wxCallbackUrl;
    }

    public String getAlipayPublicKey() {
        return alipayPublicKey;
    }

    public void setAlipayPublicKey(String alipayPublicKey) {
        this.alipayPublicKey = alipayPublicKey;
    }
}
