package com.container.containerweb.service;

import com.container.containerweb.constants.GoodsStatus;
import com.container.containerweb.constants.MachineStatus;
import com.container.containerweb.dao.GoodsDao;
import com.container.containerweb.dao.MachineDao;
import com.container.containerweb.dao.MerchantDao;
import com.container.containerweb.dao.UserDao;
import com.container.containerweb.dto.GoodsIdxCode;
import com.container.containerweb.dto.MachineGoodsBinding;
import com.container.containerweb.dto.UserDto;
import com.container.containerweb.model.biz.Goods;
import com.container.containerweb.model.biz.Merchant;
import com.container.containerweb.model.biz.VendingMachine;
import com.container.containerweb.model.rbac.User;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;

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

    public List<VendingMachine> getMachineListOfMerchant(Integer merchantId) {
        return machineDao.findByMerchantId(merchantId);
    }

    public void storeGoods(MachineGoodsBinding binding) {
        List<Goods> goods = goodsDao.findByBarcodeIn(binding.getCodes());
        VendingMachine machine = machineDao.findBySerial(binding.getSerial());
        goods.forEach(e -> {
            //e.setVendingMachine(machine);
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
            //goods.setVendingMachine(null);
            goods.setStatus(GoodsStatus.SOLD.getCode());
            goodsDao.save(goods);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public VendingMachine save(VendingMachine machine) {
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
        return machineDao.save(machine);
    }

    public VendingMachine updateStatus(VendingMachine machine) {
        VendingMachine one = machineDao.findBySerial(machine.getSerial());
        one.setStatus(machine.getStatus());
        one.setUpdateTime(System.currentTimeMillis());
        one.setTemperature(machine.getTemperature());
        return machineDao.save(one);
    }

    public VendingMachine queryBySerial(String serial) {
        return machineDao.findBySerial(serial);
    }
}
