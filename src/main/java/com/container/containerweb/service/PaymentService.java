package com.container.containerweb.service;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.container.containerweb.base.WxPayConfig;
import com.container.containerweb.dao.MachineDao;
import com.container.containerweb.model.biz.GoodsOrder;
import com.container.containerweb.model.biz.Merchant;
import com.container.containerweb.model.biz.VendingMachine;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConstants;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentService {

    @Resource
    private ObjectMapper objectMapper;

    @Resource
    private MachineDao machineDao;

    public String wxPay(GoodsOrder order, String ip) throws Exception {
        VendingMachine machine = machineDao.findBySerial(order.getMachineSerial());
        WxPayConfig config = machine.getMerchant().getWxPayConfig();
        WXPay wxpay = new WXPay(config);

        Map<String, String> data = new HashMap<>();
        data.put("body", order.getGoods().getGoodsDescription().getDescription());
        data.put("out_trade_no", order.getOrderNo());
        data.put("device_info", ip);
        data.put("fee_type", "CNY");
        data.put("total_fee", order.getPayment().toString());
        data.put("spbill_create_ip", "123.12.12.123");
        data.put("notify_url", "http://www.example" +
                ".com/wxpay/notify");
        data.put("trade_type", "NATIVE");  // 此处指定为扫码支付
        data.put("product_id", order.getGoods().getId().toString());

        Map<String, String> resp = wxpay.unifiedOrder(data);
        if (WXPayConstants.SUCCESS.equals(resp.get("return_code")) && WXPayConstants.SUCCESS.equals(resp.get("result_code"))) {
            return resp.get("code_url");
        } else {
            throw new Exception(String.format("weixin return_msg:%s, err_code_des:%s", resp.get("return_msg"), resp.get("err_code_des")));
        }
    }

    public String aliPay(GoodsOrder order) throws Exception {
        VendingMachine machine = machineDao.findBySerial(order.getMachineSerial());
        Merchant tenant = machine.getMerchant();
        String appId = tenant.getAlipayAppId();

        String appPrivateKey = tenant.getAlipayPrivateKey();

        String appPublicKey = tenant.getAlipayPublicKey();

        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", appId, appPrivateKey, "json", "utf-8", appPublicKey, "RSA2");  //获得初始化的AlipayClient
//创建API对应的request类
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        Map<String, String> bizContent = new HashMap<>();
        bizContent.put("subject", order.getGoods().getGoodsDescription().getDescription());
        bizContent.put("out_trade_no", order.getOrderNo());
        bizContent.put("total_amount", String.format("%.2f", order.getPayment() * 0.01));
        bizContent.put("notify_url","http://218.93.6.98:6189/order/alipay-paid-callback");
        request.setBizContent(objectMapper.writeValueAsString(bizContent));
//通过alipayClient调用API，获得对应的response类
        AlipayTradePrecreateResponse response = alipayClient.execute(request);
        if (response.isSuccess())
            return response.getQrCode();
        else
            throw new Exception(response.getCode() + ":" + response.getMsg());
    }
}
