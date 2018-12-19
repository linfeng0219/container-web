package com.container.containerweb.controller;

import com.container.containerweb.base.BaseResponse;
import com.container.containerweb.constants.ErrorCodes;
import com.container.containerweb.model.biz.Merchant;
import com.container.containerweb.service.MerchantService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/merchant")
public class MerchantController {

    @Resource
    private MerchantService merchantService;

    @GetMapping("/list")
    public Object getList() {
        try {
            List<Merchant> list = merchantService.merchantList();
            return BaseResponse.success(list);
        } catch (Exception e) {
            return BaseResponse.error(ErrorCodes.queryMerchantError, e.getMessage());
        }
    }

    @PostMapping("/save")
    public Object saveMch(@RequestBody Merchant merchant) {
        try {
            merchantService.save(merchant);
            return BaseResponse.success();
        } catch (Exception e) {
            return BaseResponse.error(ErrorCodes.addMerchantError, e.getMessage());
        }
    }

    @GetMapping("/name-like-query")
    public Object nameLike(String name){
        try {
            List<Merchant> merchants = merchantService.getByNameLike(name);
            return BaseResponse.success(merchants);
        } catch (Exception e) {
            return BaseResponse.error(ErrorCodes.queryMerchantError, e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public Object delete(Integer id){
        try {
            merchantService.deleteById(id);
            return BaseResponse.success();
        } catch (Exception e) {
            return BaseResponse.error(ErrorCodes.deleteMerchantError, e.getMessage());
        }
    }
}
