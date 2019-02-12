package com.container.containerweb.controller;

import com.container.containerweb.base.BaseResponse;
import com.container.containerweb.constants.ErrorCodes;
import com.container.containerweb.dto.UserDto;
import com.container.containerweb.model.biz.GoodsDescription;
import com.container.containerweb.service.GoodsDescService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
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
    public Object add(HttpSession session, MultipartFile file, String description, String price, String barcode,String id) {
        try {
            UserDto user = (UserDto) session.getAttribute("user");
            if (user != null) {
                if (!StringUtils.isEmpty(id)) {
                    goodsDescService.updateGoodsDesc(file, description, price, id,barcode);
                } else {
                    goodsDescService.addGoodsDesc(file, description, price, barcode,user.getMerchant());
                }
                return BaseResponse.success();
            } else {
                throw new RuntimeException("用户未登录");
            }
        } catch (Exception e) {
            return BaseResponse.error(ErrorCodes.addGoodsDescError, e.getMessage());
        }
    }

    @GetMapping("/delete")
    public Object delete(Integer id) {
        try {
            goodsDescService.deleteById(id);
            return BaseResponse.success();
        } catch (Exception e) {
            return BaseResponse.error(ErrorCodes.deleteGoodsDesc, e.getMessage());
        }
    }
}
