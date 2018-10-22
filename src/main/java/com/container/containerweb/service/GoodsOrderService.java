package com.container.containerweb.service;

import com.container.containerweb.dao.GoodsOrderDao;
import com.container.containerweb.model.biz.GoodsOrder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class GoodsOrderService {

    @Resource
    private GoodsOrderDao goodsOrderDao;

    public List<GoodsOrder> getList() {
        return goodsOrderDao.findAll();
    }

    public GoodsOrder getOrderByGoodsBarCode(String barcode) {
        GoodsOrder order = goodsOrderDao.findGoodsOrderByGoodsBarcode(barcode);
        if (order == null){
            throw new NullPointerException();
        }
        return order;
    }
}
