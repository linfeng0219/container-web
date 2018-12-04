package com.container.containerweb.service;

import com.container.containerweb.constants.GoodsStatus;
import com.container.containerweb.constants.OrderStatus;
import com.container.containerweb.dao.GoodsDao;
import com.container.containerweb.dao.GoodsOrderDao;
import com.container.containerweb.dao.MachineDao;
import com.container.containerweb.dto.QueryOrderDto;
import com.container.containerweb.model.biz.Goods;
import com.container.containerweb.model.biz.GoodsOrder;
import com.container.containerweb.model.biz.VendingMachine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class GoodsOrderService {

    @Resource
    private GoodsOrderDao goodsOrderDao;

    @Resource
    private GoodsDao goodsDao;

    @Resource
    private MachineDao machineDao;

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
        VendingMachine machine = machineDao.findBySerial(order.getMachineSerial());
        if (machine == null) {
            throw new IllegalArgumentException();
        }
        Goods goods = goodsDao.findByBarcodeAndIdx(order.getGoods().getBarcode(), order.getGoods().getIdx());
        if (goods != null) {
            order.setId(null);
            order.setCreateTime(System.currentTimeMillis());
            order.setGoods(goods);
            order.setPayment(goods.getGoodsDescription().getPrice());
            order.setOrderNo(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + goods.getVendingMachine().getId() + goods.getGoodsDescription().getId());
            order.setStatus(OrderStatus.UNPAID.getCode());
            goodsOrderDao.save(order);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public Page<GoodsOrder> getPage(QueryOrderDto dto) {
        return goodsOrderDao.findAll(new PageRequest(dto.getPage() - 1, dto.getSize()));
    }

    public GoodsOrder getOrderByOrderNo(String tradeNo) {
        return goodsOrderDao.findByOrderNo(tradeNo);
    }

    public void finishOrder(GoodsOrder order) {
        Goods goods = order.getGoods();
        goods.setStatus(GoodsStatus.SOLD.getCode());
        goodsDao.save(goods);
        order.setPaymentTime(System.currentTimeMillis());
        order.setStatus(OrderStatus.PAID.getCode());
        goodsOrderDao.save(order);
    }
}
