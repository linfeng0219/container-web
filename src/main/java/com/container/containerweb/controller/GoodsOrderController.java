package com.container.containerweb.controller;

import com.container.containerweb.base.BaseResponse;
import com.container.containerweb.constants.ErrorCodes;
import com.container.containerweb.model.biz.GoodsOrder;
import com.container.containerweb.service.GoodsOrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/order")
public class GoodsOrderController {

    @Resource
    private GoodsOrderService goodsOrderService;

    @GetMapping("/list")
    public Object orderList() {
        try {
            List<GoodsOrder> orders = goodsOrderService.getList();
            return BaseResponse.success(orders);
        } catch (Exception e) {
            return BaseResponse.error(ErrorCodes.queryOrderError, e.getMessage());
        }
    }
}
