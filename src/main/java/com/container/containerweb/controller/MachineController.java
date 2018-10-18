package com.container.containerweb.controller;

import com.container.containerweb.base.BaseResponse;
import com.container.containerweb.constants.ErrorCodes;
import com.container.containerweb.dto.MachineGoodsBinding;
import com.container.containerweb.model.biz.GoodsOrder;
import com.container.containerweb.model.biz.VendingMachine;
import com.container.containerweb.service.GoodsOrderService;
import com.container.containerweb.service.MachineService;
import com.container.containerweb.service.PaymentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/machine")
public class MachineController {

    @Resource
    private MachineService machineService;

    @Resource
    private PaymentService paymentService;

    @Resource
    private GoodsOrderService orderService;

    @GetMapping("/list")
    public Object list() {
        try {
            List<VendingMachine> list = machineService.getMachineList();
            return BaseResponse.success(list);
        } catch (Exception e) {
            return BaseResponse.error(ErrorCodes.queryMachineError, e.getMessage());
        }
    }

    @PostMapping("/save")
    public Object saveMch(@RequestBody VendingMachine machine) {
        try {
            machineService.save(machine);
            return BaseResponse.success();
        } catch (Exception e) {
            return BaseResponse.error(ErrorCodes.addMerchantError, e.getMessage());
        }
    }

    @PostMapping("/store-goods")
    public Object storeGoods(@RequestBody MachineGoodsBinding binding) {
        try {
            machineService.storeGoods(binding);
            return BaseResponse.success();
        } catch (Exception e) {
            return BaseResponse.error(ErrorCodes.storeGoodsError, e.getMessage());
        }
    }

    @PostMapping("/removeGoods")
    public Object removeGoods(@RequestBody MachineGoodsBinding binding) {
        try {
            machineService.removeGoods(binding);
            return BaseResponse.success();
        } catch (Exception e) {
            return BaseResponse.error(ErrorCodes.removeGoodsError, e.getMessage());
        }
    }

    @PostMapping("/upload-order")
    public Object createOrder(@RequestBody GoodsOrder goodsOrder, HttpServletRequest request) {
        try {
            machineService.addOrder(goodsOrder);
            String codeUrl;
            //wx
            if (goodsOrder.getPayment() == 0) {
                codeUrl = paymentService.wxPay(goodsOrder, request.getRemoteHost());
            } else {
                codeUrl = paymentService.aliPay(goodsOrder);
            }
            return BaseResponse.success(codeUrl);
        } catch (Exception e) {
            return BaseResponse.error(ErrorCodes.createOrderError, e.getMessage());
        }
    }

    @GetMapping("/get-order")
    public Object updateStatus(String barCode) {
        try {
            GoodsOrder order = orderService.getOrderByGoodsBarCode(barCode);
            return BaseResponse.success(order);
        } catch (Exception e) {
            return BaseResponse.error(ErrorCodes.queryOrderError, e.getMessage());
        }
    }
}
