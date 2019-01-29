package com.container.containerweb.service;

import com.container.containerweb.constants.DeliveryStatus;
import com.container.containerweb.constants.GoodsStatus;
import com.container.containerweb.constants.MachineStatus;
import com.container.containerweb.dao.*;
import com.container.containerweb.dto.GoodsIdxCode;
import com.container.containerweb.dto.MachineGoodsBinding;
import com.container.containerweb.model.biz.DeliverySheet;
import com.container.containerweb.model.biz.Goods;
import com.container.containerweb.model.biz.Merchant;
import com.container.containerweb.model.biz.VendingMachine;
import com.container.containerweb.model.rbac.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    @Resource
    private GoodsCollectDao collectDao;

    @Resource
    private DeliverySheetDao deliverySheetDao;

    public List<VendingMachine> getMachineListOfMerchant(Integer merchantId) {
        return machineDao.findByMerchantId(merchantId);
    }

    //上架
    public void storeGoods(MachineGoodsBinding binding) {
        List<Goods> goodsList = binding.getGoodsList();
        for (Goods goods:goodsList){
            Goods e = goodsDao.findOne(goods.getId());
            if (e != null) {
                e.setIdx(goods.getIdx());
                e.setIdy(goods.getIdy());
                e.setStatus(goods.getStatus());
                goodsDao.save(e);
                collectDao.updateActualDeliverAmount(e.getBatchNo(), e.getGoodsDescription().getDescription());
                DeliverySheet ds = deliverySheetDao.findFirstByBatchNo(e.getBatchNo());
                if(ds!=null && (ds.getStatus()!=DeliveryStatus.FORSALR.getCode())){
                    ds.setStatus(DeliveryStatus.FORSALR.getCode());
                    deliverySheetDao.save(ds);
                }
            } else {
                throw new IllegalArgumentException();
            }
        }
    }
    //销毁
    public void removeGoods(MachineGoodsBinding binding) {
        List<Goods> goodsList = binding.getGoodsList();
        for (Goods goods:goodsList){
            Goods e = goodsDao.findOne(goods.getId());
            if (e != null) {
                e.setStatus(GoodsStatus.OUT.getCode());
                goodsDao.save(e);
                DeliverySheet ds = deliverySheetDao.findFirstByBatchNo(e.getBatchNo());
                if(ds!=null){
                    ds.setStatus(DeliveryStatus.COMPLETE.getCode());
                    deliverySheetDao.save(ds);
                }
            } else {
                throw new IllegalArgumentException();
            }
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
        List<Goods> goods = goodsDao.findByVendingMachineSerialAndStatus(machine.getSerial(),GoodsStatus.FORSALE.getCode());
        one.setStatus(machine.getStatus());
        one.setUpdateTime(System.currentTimeMillis());
        one.setTemperature(machine.getTemperature());
        one.setHumidity(machine.getHumidity());
        one.setErrorCode(machine.getErrorCode());
        one.setGoods(goods);
        return machineDao.save(one);
    }

    public VendingMachine queryBySerial(String serial) {
        return machineDao.findBySerial(serial);
    }

    public void deleteById(Integer id) {
        machineDao.delete(id);
    }
}
