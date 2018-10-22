package com.container.containerweb.service;

import com.container.containerweb.constants.GoodsStatus;
import com.container.containerweb.constants.MachineStatus;
import com.container.containerweb.dao.*;
import com.container.containerweb.dto.GoodsIdxCode;
import com.container.containerweb.dto.MachineGoodsBinding;
import com.container.containerweb.model.biz.Goods;
import com.container.containerweb.model.biz.GoodsOrder;
import com.container.containerweb.model.biz.Merchant;
import com.container.containerweb.model.biz.VendingMachine;
import com.container.containerweb.model.rbac.User;
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
    private MerchantDao merchantDao;

    @Resource
    private GoodsDao goodsDao;

    @Resource
    private UserDao userDao;

    @Resource
    private GoodsOrderDao goodsOrderDao;

    public List<VendingMachine> getMachineList() {
        return machineDao.findAll();
    }

    public void storeGoods(MachineGoodsBinding binding) {
        List<Goods> goods = goodsDao.findByBarcodeIn(binding.getCodes());
        VendingMachine machine = machineDao.findBySerial(binding.getSerial());
        goods.forEach(e -> {
            e.setVendingMachine(machine);
            e.setStatus(GoodsStatus.STORED.getCode());
            binding.getIdxCode().forEach(r -> {
                if (Objects.equals(r.getCode(), e.getBarcode())) {
                    e.setIdx(r.getIndex());
                }
            });
            goodsDao.save(e);
        });
    }

    public void removeGoods(MachineGoodsBinding binding) {
        GoodsIdxCode idxCode = binding.getIdxCode().get(0);
        Goods goods = goodsDao.findByBarcodeAndIdx(idxCode.getCode(), idxCode.getIndex());
        if (goods != null) {
            goods.setVendingMachine(null);
            goods.setStatus(GoodsStatus.SOLD.getCode());
            goodsDao.save(goods);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void addOrder(GoodsOrder order) {
        Goods goods = goodsDao.findByBarcodeAndIdx(order.getGoods().getBarcode(), order.getGoods().getIdx());
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

    public void save(VendingMachine machine) {
        if (machine.getId() == null)
            machine.setCreateTime(System.currentTimeMillis());
        Merchant merchant = merchantDao.findOne(machine.getMerchant().getId());
        User master = userDao.findOne(machine.getMaster().getId());
        if (merchant == null || master == null) {
            throw new IllegalArgumentException();
        }
        machine.setMaster(master);
        machine.setMerchant(merchant);
        machine.setStatus(MachineStatus.OFFLINE.getCode());
        machineDao.save(machine);
    }
}
