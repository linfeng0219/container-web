package com.container.containerweb.service;

import com.container.containerweb.constants.GoodsStatus;
import com.container.containerweb.dao.GoodsDao;
import com.container.containerweb.dao.GoodsOrderDao;
import com.container.containerweb.dao.MachineDao;
import com.container.containerweb.dto.GoodsIdxCode;
import com.container.containerweb.dto.MachineGoodsBinding;
import com.container.containerweb.model.biz.Goods;
import com.container.containerweb.model.biz.GoodsOrder;
import com.container.containerweb.model.biz.VendingMachine;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class MachineService {

    @Resource
    private MachineDao machineDao;

    @Resource
    private GoodsDao goodsDao;

    @Resource
    private GoodsOrderDao goodsOrderDao;

    public List<VendingMachine> getMachineList() {
        return machineDao.findAll();
    }

    public void storeGoods(MachineGoodsBinding binding) {
        List<Goods> goods = goodsDao.findByBarCodeIn(binding.getCodes());
        VendingMachine machine = machineDao.findBySerial(binding.getSerial());
        goods.forEach(e -> {
            e.setVendingMachine(machine);
            e.setStatus(GoodsStatus.STORED.getCode());
            binding.getIdxCode().forEach(r -> {
                if (Objects.equals(r.getCode(), e.getBarCode())) {
                    e.setIdx(r.getIndex());
                }
            });
            goodsDao.save(e);
        });
    }

    public void removeGoods(MachineGoodsBinding binding) {
        GoodsIdxCode idxCode = binding.getIdxCode().get(0);
        Goods goods = goodsDao.findByBarCodeAndIdx(idxCode.getCode(), idxCode.getIndex());
        if (goods != null) {
            goods.setVendingMachine(null);
            goods.setStatus(GoodsStatus.SOLD.getCode());
            goodsDao.save(goods);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void addOrder(GoodsOrder order) {
        Goods goods = goodsDao.findByBarCodeAndIdx(order.getGoods().getBarCode(), order.getGoods().getIdx());
        if (goods != null) {
            order.setId(null);
            order.setCreateTime(System.currentTimeMillis());
            order.setGoods(goods);
            order.setPayment(goods.getPrice());
            order.setOrderNo(UUID.randomUUID().toString());
            goodsOrderDao.save(order);
        } else {
            throw new IllegalArgumentException();
        }
    }
}
