package com.container.containerweb.controller;

import com.container.containerweb.base.BaseResponse;
import com.container.containerweb.constants.ErrorCodes;
import com.container.containerweb.dto.GoodsAmountDto;
import com.container.containerweb.dto.QueryGoodsDto;
import com.container.containerweb.model.biz.Goods;
import com.container.containerweb.service.GoodsService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private ObjectMapper mapper;

    @PostMapping("/add")
    public Object addGoods(@RequestBody Map<Integer, Integer> map) {
        try {
            List<GoodsAmountDto> list = new ArrayList<>(map.size());
            for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                list.add(new GoodsAmountDto(entry.getKey(), entry.getValue()));
            }
            goodsService.addGoods(list);
            return BaseResponse.success();
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
}

