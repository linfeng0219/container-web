package com.container.containerweb.controller;

import com.container.containerweb.base.BaseResponse;
import com.container.containerweb.constants.ErrorCodes;
import com.container.containerweb.dto.MachineGoodsBinding;
import com.container.containerweb.model.biz.Goods;
import com.container.containerweb.model.biz.GoodsDescription;
import com.container.containerweb.model.biz.Merchant;
import com.container.containerweb.model.biz.VendingMachine;
import com.container.containerweb.model.rbac.User;
import com.container.containerweb.service.GoodsService;
import com.container.containerweb.service.MachineService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
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
    public Object list(HttpSession session) {
        try {
            Integer merchantId = (Integer) session.getAttribute("merchantId");
            List<VendingMachine> list = machineService.getMachineListOfMerchant(merchantId);
            list = list.stream().peek(e -> {
                Merchant _m = new Merchant();
                _m.setName(e.getMerchant().getName());
                e.setMerchant(_m);

                User _master = e.getMaster();
                if (_master != null) {
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

    //上架
    @PostMapping("/store-goods")
    public Object storeGoods(@RequestBody MachineGoodsBinding binding) {
        try {
            machineService.storeGoods(binding);
            return BaseResponse.success();
        } catch (Exception e) {
            return BaseResponse.error(ErrorCodes.storeGoodsError, e.getMessage());
        }
    }

    //下架
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
            List<Goods> goodsList =m.getGoods();
            for (Goods goods:goodsList){
                GoodsDescription goodsDescription = goods.getGoodsDescription();
                goodsDescription.setMerchant(null);
                goods.setVendingMachine(null);
                goods.setDeliveryman(null);
                goods.setGoodsDescription(goodsDescription);
            }
            m.setGoods(goodsList);
            m.setMerchant(null);
            m.setMaster(null);
            return BaseResponse.success(m);
        } catch (Exception e) {
            return BaseResponse.error(ErrorCodes.uploadStatusError, e.getMessage());
        }
    }

    @GetMapping("/get-current-batch-no")
    public Object getCurrentBatchNo(String serial) {
        try {
            List<Goods> goodsList = goodsService.findCurrentBatchNoBySerial(serial);
            for (Goods goods:goodsList){
                GoodsDescription goodsDescription = goods.getGoodsDescription();
                goodsDescription.setMerchant(null);
                goods.setVendingMachine(null);
                goods.setDeliveryman(null);
                goods.setGoodsDescription(goodsDescription);
            }
            return BaseResponse.success(goodsList);
        } catch (Exception e) {
            return BaseResponse.error(ErrorCodes.queryMachineError, e.getMessage());
        }
    }

    @GetMapping("/delete")
    public Object deleteById(Integer id){
        try {
            this.machineService.deleteById(id);
            return BaseResponse.success();
        } catch (Exception e) {
            return BaseResponse.error(ErrorCodes.deleteMachineError, e.getMessage());
        }
    }
}
