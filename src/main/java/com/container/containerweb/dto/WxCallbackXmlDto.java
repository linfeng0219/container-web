package com.container.containerweb.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@XmlRootElement(name = "xml")
public class WxCallbackXmlDto {
    /**
     * <xml>
     * <appid><![CDATA[wx2421b1c4370ec43b]]></appid>
     * <attach><![CDATA[支付测试]]></attach>
     * <bank_type><![CDATA[CFT]]></bank_type>
     * <fee_type><![CDATA[CNY]]></fee_type>
     * <is_subscribe><![CDATA[Y]]></is_subscribe>
     * <mch_id><![CDATA[10000100]]></mch_id>
     * <nonce_str><![CDATA[5d2b6c2a8db53831f7eda20af46e531c]]></nonce_str>
     * <openid><![CDATA[oUpF8uMEb4qRXf22hE3X68TekukE]]></openid>
     * <out_trade_no><![CDATA[1409811653]]></out_trade_no>
     * <result_code><![CDATA[SUCCESS]]></result_code>
     * <return_code><![CDATA[SUCCESS]]></return_code>
     * <sign><![CDATA[B552ED6B279343CB493C5DD0D78AB241]]></sign>
     * <sub_mch_id><![CDATA[10000100]]></sub_mch_id>
     * <time_end><![CDATA[20140903131540]]></time_end>
     * <total_fee>1</total_fee>
     * <coupon_fee_0><![CDATA[10]]></coupon_fee_0>
     * <coupon_count><![CDATA[1]]></coupon_count>
     * <coupon_type><![CDATA[CASH]]></coupon_type>
     * <coupon_id><![CDATA[10000]]></coupon_id>
     * <trade_type><![CDATA[JSAPI]]></trade_type>
     * <transaction_id><![CDATA[1004400740201409030005092168]]></transaction_id>
     * </xml>
     */
    @XmlElement(name = "appid")
    private String appId;

    private String attach;

    @XmlElement(name = "fee_type")
    private String feeType;

    @XmlElement(name = "bank_type")
    private String bankType;

    @XmlElement(name = "is_subscribe")
    private String isSubscribe;

    @XmlElement(name = "mch_id")
    private String merchantId;

    @XmlElement(name = "openid")
    private String openId;

    @XmlElement(name = "out_trade_no")
    private String tradeNo;

    @XmlElement(name = "transaction_id")
    private String transactionId;

    @XmlElement(name = "return_code")
    private String returnCode;

    @XmlElement(name = "result_code")
    private String resultCode;

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public String getBankType() {
        return bankType;
    }

    public void setBankType(String bankType) {
        this.bankType = bankType;
    }

    public String getIsSubscribe() {
        return isSubscribe;
    }

    public void setIsSubscribe(String isSubscribe) {
        this.isSubscribe = isSubscribe;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public boolean tradeSuccess(){
        return Objects.equals(this.resultCode, "SUCCESS") && Objects.equals(this.returnCode, "SUCCESS");
    }
}
