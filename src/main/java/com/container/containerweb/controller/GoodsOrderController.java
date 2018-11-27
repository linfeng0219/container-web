package com.container.containerweb.controller;

import com.container.containerweb.base.BaseResponse;
import com.container.containerweb.constants.ErrorCodes;
import com.container.containerweb.dto.QueryOrderDto;
import com.container.containerweb.dto.WxCallbackXmlDto;
import com.container.containerweb.dto.WxReturnXmlDto;
import com.container.containerweb.model.biz.GoodsOrder;
import com.container.containerweb.service.GoodsOrderService;
import com.container.containerweb.service.PaymentService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/order")
public class GoodsOrderController {

    @Resource
    private GoodsOrderService goodsOrderService;

    @Resource
    private PaymentService paymentService;

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

    @RequestMapping(value = "/alipay-paid-callback")
    public Object alipayCallback(HttpServletRequest request) {
        try {

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
