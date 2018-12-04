package com.container.containerweb.controller;

import com.alipay.api.internal.util.AlipaySignature;
import com.container.containerweb.base.BaseResponse;
import com.container.containerweb.constants.ErrorCodes;
import com.container.containerweb.dto.QueryOrderDto;
import com.container.containerweb.dto.WxCallbackXmlDto;
import com.container.containerweb.dto.WxReturnXmlDto;
import com.container.containerweb.model.biz.GoodsOrder;
import com.container.containerweb.model.biz.Merchant;
import com.container.containerweb.model.biz.VendingMachine;
import com.container.containerweb.service.GoodsOrderService;
import com.container.containerweb.service.MachineService;
import com.container.containerweb.service.PaymentService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/order")
public class GoodsOrderController {

    @Resource
    private GoodsOrderService goodsOrderService;

    @Resource
    private PaymentService paymentService;

    @Resource
    private MachineService machineService;

    @GetMapping("/page")
    public Object orderPage(QueryOrderDto dto) {
        try {
            Page<GoodsOrder> orders = goodsOrderService.getPage(dto);
            return BaseResponse.success(orders);
        } catch (Exception e) {
            return BaseResponse.error(ErrorCodes.queryOrderError, e.getMessage());
        }
    }

    @PostMapping("/create-order")
    public Object createOrder(@RequestBody GoodsOrder goodsOrder, HttpServletRequest request) {
        try {
            goodsOrderService.addOrder(goodsOrder);
            String codeUrl;
            //wx
            if (goodsOrder.getPaymentMode() == 0) {
                codeUrl = paymentService.wxPay(goodsOrder, request.getRemoteHost());
            } else {
                codeUrl = paymentService.aliPay(goodsOrder);
            }
            return BaseResponse.success(codeUrl);
        } catch (Exception e) {
            return BaseResponse.error(ErrorCodes.createOrderError, e.getMessage());
        }
    }

    @GetMapping("/get-order-status")
    public Object getOrder(String barcode) {
        try {
            GoodsOrder order = goodsOrderService.getOrderByGoodsBarcode(barcode);
            return BaseResponse.success(order);
        } catch (Exception e) {
            return BaseResponse.error(ErrorCodes.queryOrderError, e.getMessage());
        }
    }


    //https://api.xx.com/receive_notify.htm?
    // gmt_payment=2015-06-11 22:33:59
    // &notify_id=42af7baacd1d3746cf7b56752b91edcj34
    // &seller_email=testyufabu07@alipay.com
    // &notify_type=trade_status_sync
    // &sign=kPbQIjX+xQc8F0/A6/AocELIjhhZnGbcBN6G4MM/HmfWL4ZiHM6fWl5NQhzXJusaklZ1LFuMo+lHQUELAYeugH8LYFvxnNajOvZhuxNFbN2LhF0l/KL8ANtj8oyPM4NN7Qft2kWJTDJUpQOzCzNnV9hDxh5AaT9FPqRS6ZKxnzM=&trade_no=2015061121001004400068549373
    // &out_trade_no=21repl2ac2eOutTradeNo322
    // &gmt_create=2015-06-11 22:33:46
    // &seller_id=2088211521646673
    // &notify_time=2015-06-11 22:34:03
    // &subject=FACE_TO_FACE_PAYMENT_PRECREATE中文
    // &trade_status=TRADE_SUCCESS
    // &sign_type=RSA2
    @RequestMapping(value = "/alipay-paid-callback")
    public Object alipayCallback(@RequestParam Map<String, String> paramsMap) {
        try {
            String code = paramsMap.get("code");
            if ("9000".equals(code)) {
                String sign = paramsMap.get("sign");
                String tradeNo = paramsMap.get("trade_no");
                GoodsOrder order = goodsOrderService.getOrderByOrderNo(tradeNo);
                VendingMachine machine = machineService.queryBySerial(order.getMachineSerial());
                Merchant merchant = machine.getMerchant();
                paramsMap.remove("sign");
                paramsMap.remove("sign_type");
                boolean checkRes = AlipaySignature.rsa256CheckContent(AlipaySignature.getSignContent(paramsMap), sign,
                        merchant.getAlipayPublicKey(), "UTF-8");
                if (checkRes) {
                    String totalAmount = paramsMap.get("total_amount");
                    if (!String.format("%.2f", order.getPayment() * 0.01).equals(totalAmount)) {
                        return "success";
                    }
                    if (!merchant.getAlipayAppId().equals(paramsMap.get("app_id"))){
                        return "success";
                    }
                    goodsOrderService.finishOrder(order);
                }
            }

        } catch (Exception e) {
        }
        return null;
    }

    @RequestMapping(value = "/wx-paid-callback", consumes = {"application/xml"})
    public Object callback(@RequestBody WxCallbackXmlDto xmlDto) {
        try {
            if (xmlDto.tradeSuccess()) {
                goodsOrderService.completeOrder(xmlDto.getTradeNo());
                return WxReturnXmlDto.success();
            }
        } catch (Exception e) {
        }
        return null;
    }
}
