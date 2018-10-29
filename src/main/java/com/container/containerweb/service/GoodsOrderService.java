package com.container.containerweb.service;

import com.container.containerweb.constants.OrderStatus;
import com.container.containerweb.dao.GoodsDao;
import com.container.containerweb.dao.GoodsOrderDao;
import com.container.containerweb.model.biz.Goods;
import com.container.containerweb.model.biz.GoodsOrder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@Service
public class GoodsOrderService {

    @Resource
    private GoodsOrderDao goodsOrderDao;

    @Resource
    private GoodsDao goodsDao;

    public List<GoodsOrder> getList() {
        return goodsOrderDao.findAll();
    }

    public GoodsOrder getOrderByGoodsBarcode(String barcode) {
        GoodsOrder order = goodsOrderDao.findGoodsOrderByGoodsBarcode(barcode);
        if (order == null) {
            throw new NullPointerException();
        }
        return order;
    }

    public void completeOrder(String tradeNo) {
        GoodsOrder order = goodsOrderDao.findByOrderNo(tradeNo);
        if (order != null) {
            order.setPaymentTime(System.currentTimeMillis());
            order.setStatus(OrderStatus.PAID.getCode());
            goodsOrderDao.save(order);
        } else {
            throw new NullPointerException("未找到:" + tradeNo);
        }
    }

    public void addOrder(GoodsOrder order) {
        Goods goods = goodsDao.findByBarcodeAndIdx(order.getGoods().getBarcode(), order.getGoods().getIdx());
        if (goods != null) {
            order.setId(null);
            order.setCreateTime(System.currentTimeMillis());
            order.setGoods(goods);
            order.setPayment(goods.getGoodsDescription().getPrice());
            order.setOrderNo(UUID.randomUUID().toString());
            order.setStatus(OrderStatus.UNPAID.getCode());
            goodsOrderDao.save(order);
        } else {
            throw new IllegalArgumentException();
        }
    }
}
