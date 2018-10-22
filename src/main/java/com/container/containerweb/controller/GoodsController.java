package com.container.containerweb.controller;

import com.container.containerweb.base.BaseResponse;
import com.container.containerweb.constants.ErrorCodes;
import com.container.containerweb.model.biz.Goods;
import com.container.containerweb.service.GoodsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;

@RequestMapping("/goods")
@RestController
public class GoodsController {

    @Resource
    private GoodsService goodsService;

    @RequestMapping("/add")
    public Object addGoods(@RequestParam("goodsImage") CommonsMultipartFile file, Goods goods){
        try {
            if (file.isEmpty()) {
                return BaseResponse.error(ErrorCodes.addGoodsError, "文件为空");
            }
            goodsService.addGoods(file, goods);
            return BaseResponse.success();
        } catch (Exception e) {
            return BaseResponse.error(ErrorCodes.addGoodsError, e.getMessage());
        }
    }
}

