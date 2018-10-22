package com.container.containerweb.controller;

import com.container.containerweb.base.BaseResponse;
import com.container.containerweb.constants.ErrorCodes;
import com.container.containerweb.model.biz.GoodsDescription;
import com.container.containerweb.service.GoodsDescService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

@RequestMapping("/goods-description")
@RestController
public class GoodsDescController {

    @Resource
    private GoodsDescService goodsDescService;

    @RequestMapping("/list")
    public Object list() {
        try {
            List<GoodsDescription> descriptions = goodsDescService.getList();
            return BaseResponse.success(descriptions);
        } catch (Exception e) {
            return BaseResponse.error(ErrorCodes.queryGoodsDescError, e.getMessage());
        }
    }

    @RequestMapping("/add")
    public Object add(MultipartFile file, String description) {
        try {
            goodsDescService.addGoodsDesc(file, description);
            return BaseResponse.success();
        } catch (Exception e) {
            return BaseResponse.error(ErrorCodes.addGoodsDescError, e.getMessage());
        }
    }
}
