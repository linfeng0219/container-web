package com.container.containerweb.controller;

import com.container.containerweb.base.BaseResponse;
import com.container.containerweb.constants.ErrorCodes;
import com.container.containerweb.dto.DeliverymanDto;
import com.container.containerweb.dto.GoodsAmountDto;
import com.container.containerweb.dto.QueryGoodsDto;
import com.container.containerweb.model.biz.Goods;
import com.container.containerweb.model.biz.VendingMachine;
import com.container.containerweb.model.rbac.User;
import com.container.containerweb.service.GoodsService;
import com.container.containerweb.service.MachineService;
import com.container.containerweb.service.RbacService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequestMapping("/goods")
@RestController
public class GoodsController {

    @Resource
    private GoodsService goodsService;

    @Resource
    private RbacService rbacService;

    @Resource
    private MachineService machineService;

    @PostMapping("/add")
    public Object addGoods(@RequestBody Map<Integer, Integer> map) {
        try {
            List<GoodsAmountDto> list = new ArrayList<>(map.size());
            for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                list.add(new GoodsAmountDto(entry.getKey(), entry.getValue()));
            }
            List<Goods> res = goodsService.addGoods(list);
            return BaseResponse.success(res);
        } catch (Exception e) {
            return BaseResponse.error(ErrorCodes.addGoodsError, e.getMessage());
        }
    }

    @GetMapping("/page")
    public Object page(QueryGoodsDto dto) {
        try {
            Page<Goods> data = goodsService.getPage(dto);
            return BaseResponse.success(data);
        } catch (Exception e) {
            return BaseResponse.error(ErrorCodes.queryGoodsError, e.getMessage());
        }
    }

    @GetMapping("/batchNo")
    public Object getGoodsByBatchNo(String batchNo) {
        try {
            List<Goods> goodsList = goodsService.getGoodsByBatchNo(batchNo);
            return BaseResponse.success(goodsList);
        } catch (Exception e) {
            return BaseResponse.error(ErrorCodes.queryOrderError, e.getMessage());
        }
    }

    @PostMapping("/set-deliveryman")
    public Object setDelivering(@RequestBody DeliverymanDto dto) {
        try {
            User user = rbacService.queryUserById(dto.getUserId());
            if (user == null) {
                throw new IllegalArgumentException("用户不存在！");
            }
            VendingMachine machine = machineService.queryBySerial(dto.getSerial());
            if (machine == null){
                throw new IllegalArgumentException("机柜不存在！");
            }

            int cnt = goodsService.setGoodsDeliveryman(user, machine,dto.getGoodsIds());
            return BaseResponse.success(cnt);
        } catch (Exception e) {
            return BaseResponse.error(ErrorCodes.saveDeliverymanError, "设置送货员失败。");
        }
    }
}

