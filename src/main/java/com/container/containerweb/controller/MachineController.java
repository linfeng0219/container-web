package com.container.containerweb.controller;

import com.container.containerweb.base.BaseResponse;
import com.container.containerweb.constants.ErrorCodes;
import com.container.containerweb.dto.MachineGoodsBinding;
import com.container.containerweb.model.biz.Goods;
import com.container.containerweb.model.biz.Merchant;
import com.container.containerweb.model.biz.VendingMachine;
import com.container.containerweb.model.rbac.User;
import com.container.containerweb.service.GoodsOrderService;
import com.container.containerweb.service.GoodsService;
import com.container.containerweb.service.MachineService;
import com.container.containerweb.service.PaymentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/machine")
public class MachineController {

    @Resource
    private MachineService machineService;

    @Resource
    private GoodsService goodsService;

    @GetMapping("/list")
    public Object list() {
        try {
            List<VendingMachine> list = machineService.getMachineList();
            list = list.stream().peek(e -> {
                Merchant _m = new Merchant();
                _m.setName(e.getMerchant().getName());
                e.setMerchant(_m);

                User _master = e.getMaster();
                if (_master != null){
                    e.setMaster(_master.simple());
                }
            }).collect(Collectors.toList());
            return BaseResponse.success(list);
        } catch (Exception e) {
            return BaseResponse.error(ErrorCodes.queryMachineError, e.getMessage());
        }
    }

    @PostMapping("/save")
    public Object saveMch(@RequestBody VendingMachine machine) {
        try {
            VendingMachine saved = machineService.save(machine);
            return BaseResponse.success(saved);
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

    @PostMapping("/remove-goods")
    public Object removeGoods(@RequestBody MachineGoodsBinding binding) {
        try {
            machineService.removeGoods(binding);
            return BaseResponse.success();
        } catch (Exception e) {
            return BaseResponse.error(ErrorCodes.removeGoodsError, e.getMessage());
        }
    }

    @PostMapping("/upload-status")
    public Object uploadStatus(@RequestBody VendingMachine machine) {
        try {
            VendingMachine m = machineService.updateStatus(machine);
            List<Goods> goodsList = goodsService.getGoodsByMachine(m);
            m.setGoods(goodsList);
            m.setMerchant(null);
            m.setMaster(null);
            return BaseResponse.success(m);
        } catch (Exception e) {
            return BaseResponse.error(ErrorCodes.uploadStatusError, e.getMessage());
        }
    }
}
