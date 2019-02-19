package com.container.containerweb.controller;

import com.container.containerweb.base.BaseResponse;
import com.container.containerweb.constants.ErrorCodes;
import com.container.containerweb.dto.*;
import com.container.containerweb.model.biz.*;
import com.container.containerweb.model.rbac.User;
import com.container.containerweb.service.*;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
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

    @Resource
    private DeliverySheetService deliverySheetService;

    @Resource
    private GoodsCollectService collectService;

    @PostMapping("/add-delivery-sheet")
    public Object addGoods(@RequestBody Map<String, String> map) {
        try {
            List<GoodsAmountDto> list = new ArrayList<>(map.size());
            String deliverymanId = map.get("deliverymanId");
            String machineId = map.get("machineId");
            if (StringUtils.isEmpty(deliverymanId) || StringUtils.isEmpty(machineId)) {
                throw new IllegalArgumentException("缺少送货员或机柜");
            }
            map.remove("deliverymanId");
            map.remove("machineId");
            for (Map.Entry<String, String> entry : map.entrySet()) {
                list.add(GoodsAmountDto.newGoodsAmountDto(entry.getKey(), entry.getValue()));
            }
            DeliverySheet sheet = deliverySheetService.addSheet(list, deliverymanId, machineId);

            goodsService.addGoodsCollectRecord(list, sheet.getBatchNo(), machineId);
            return BaseResponse.success(sheet);
        } catch (Exception e) {
            return BaseResponse.error(ErrorCodes.addGoodsError, e.getMessage());
        }
    }

    @GetMapping("/page")
    public Object page(QueryGoodsDto dto, HttpSession session) {
        try {
            Integer merchantId = (Integer) session.getAttribute("merchantId");
            Page<Goods> data = goodsService.getPageOfMerchantId(dto, merchantId);
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

    @GetMapping("/get-delivery-sheet-page")
    public Object getDeliverySheetPage(QuerySheetDto dto, HttpSession session) {
        try {
            Integer merchantId = (Integer) session.getAttribute("merchantId");
            UserDto userDto = (UserDto) session.getAttribute("user");
            Page<DeliverySheet> page;
            if (userDto.getRoles().contains(new RoleDto("admin"))) {
                page = deliverySheetService.getSheetPage(dto);
            } else {
                page = deliverySheetService.getSheetPageOfMerchantId(dto, merchantId);
            }
            return BaseResponse.success(page);
        } catch (Exception e) {
            return BaseResponse.error(ErrorCodes.querySheetError, e.getMessage());
        }
    }

    @GetMapping("/get-goods-collect-page")
    public Object getGoodsCollectPage(QueryGoodsCollectDto dto, HttpSession session) {
        try {
            Integer merchantId = (Integer) session.getAttribute("merchantId");
            UserDto userDto = (UserDto) session.getAttribute("user");
            Page<GoodsCollect> page;
            if (userDto.getRoles().contains(new RoleDto("admin"))) {
                page = collectService.getPage(dto);
            } else {
                page = collectService.getPage(dto, merchantId);
            }
            return BaseResponse.success(page);
        } catch (Exception e) {
            return BaseResponse.error(ErrorCodes.queryGoodsCollectError, e.getMessage());
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
            if (machine == null) {
                throw new IllegalArgumentException("机柜不存在！");
            }

            int cnt = goodsService.setGoodsDeliveryman(user, machine, dto.getGoodsIds());
            return BaseResponse.success(cnt);
        } catch (Exception e) {
            return BaseResponse.error(ErrorCodes.saveDeliverymanError, "设置送货员失败。");
        }
    }

    @PostMapping("/save-deliver-sheet")
    public Object saveDeliverSheet(@RequestBody DeliverySheet sheet) {
        try {
            deliverySheetService.save(sheet);
            return BaseResponse.success();
        } catch (Exception e) {
            return BaseResponse.error(ErrorCodes.saveDeliverymanError, e.getMessage());
        }
    }

    @PostMapping("/delete-deliver-sheet")
    public Object deleteDeliverSheet(@RequestBody DeliverySheet sheet) {
        try {
            deliverySheetService.delete(sheet);
            return BaseResponse.success();
        } catch (Exception e) {
            return BaseResponse.error(ErrorCodes.saveDeliverymanError, e.getMessage());
        }
    }
}

