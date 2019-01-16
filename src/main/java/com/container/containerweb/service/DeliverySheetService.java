package com.container.containerweb.service;

import com.container.containerweb.constants.DeliveryStatus;
import com.container.containerweb.constants.GoodsStatus;
import com.container.containerweb.dao.*;
import com.container.containerweb.dto.GoodsAmountDto;
import com.container.containerweb.dto.QuerySheetDto;
import com.container.containerweb.model.biz.*;
import com.container.containerweb.model.rbac.User;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class DeliverySheetService {

    @Resource
    private DeliverySheetDao sheetDao;

    @Resource
    private UserDao userDao;

    @Resource
    private MachineDao machineDao;

    @Resource
    private GoodsDescDao goodsDescDao;

    @Resource
    private GoodsDao goodsDao;

    @Resource
    private DeliveryInfoDescriptionDao deliveryInfoDescriptionDao;

    public DeliverySheet addSheet(List<GoodsAmountDto> dtos, String deliverymanId, String machineId) {
        //参数校验
        User deliveryman = userDao.findById(Integer.valueOf(deliverymanId));
        if (deliveryman == null) {
            throw new NullPointerException("送货员不存在");
        }
        VendingMachine machine = machineDao.findOne(Integer.valueOf(machineId));
        if (machine == null) {
            throw new NullPointerException("机柜不存在");
        }
        List<DeliverySheet> deliverySheetList = sheetDao.findByMachineSerialAndStatus(machine.getSerial(),DeliveryStatus.FORSTORE.getCode());
        if(!deliverySheetList.isEmpty()){
            throw new NullPointerException("当前有未完成配货单，请完成后继续");
        }
        String batchNo = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        //生成配货单
        DeliverySheet sheet = new DeliverySheet();
        sheet.setBatchNo(batchNo);
        sheet.setDeliveryman(deliveryman.getName());
        sheet.setMachineCapacity(machine.getCapacity());
        sheet.setMachineSerial(machine.getSerial());
        sheet.setMachineLocation(machine.getLocation());
        sheet.setMerchantId(machine.getMerchant().getId());
        sheet.setStatus(DeliveryStatus.FORSTORE.getCode());
        int total = 0;
        for (GoodsAmountDto dto : dtos) {
            total += dto.getAmount();
        }
        sheet.setTotal(total);
        DeliverySheet saved = sheetDao.save(sheet);
        //添加商品
        List<DeliveryInfoDescription> deliveryInfoDescriptions = new ArrayList<>();
        for (GoodsAmountDto dto : dtos) {
            GoodsDescription description = goodsDescDao.findOne(dto.getId());
            for (int i = 0; i < dto.getAmount(); i++) {
                Goods goods = new Goods();
                goods.setGoodsDescription(description);
                goods.setStatus(GoodsStatus.PRODUCED.getCode());
                goods.setBarcode(Long.toString(System.nanoTime()));
                goods.setBatchNo(batchNo);
                goods.setCreateTime(System.currentTimeMillis());
                goods.setDeliveryman(deliveryman);
                goods.setVendingMachine(machine);
                goodsDao.save(goods);
            }
            //添加配货单关联信息
            DeliveryInfoDescription deliveryInfoDescription = new DeliveryInfoDescription();
            deliveryInfoDescription.setAmount(dto.getAmount());
            deliveryInfoDescription.setGoodsDesc(description.getDescription());
            deliveryInfoDescription.setSheetId(saved.getId());
            DeliveryInfoDescription savedDescription = deliveryInfoDescriptionDao.save(deliveryInfoDescription);
            deliveryInfoDescriptions.add(savedDescription);
        }
        saved.setDescriptions(deliveryInfoDescriptions);
        return saved;
    }

    public Page<DeliverySheet> getSheetPageOfMerchantId(QuerySheetDto dto, Integer merchantId) {
        Example<DeliverySheet> example = Example.of(new DeliverySheet(merchantId), ExampleMatcher.matching().withIgnoreNullValues());
        Page<DeliverySheet> page = sheetDao.findAll(example, new PageRequest(dto.getPage() - 1, dto.getSize()));

        for (DeliverySheet sheet : page.getContent()) {
            List<DeliveryInfoDescription> descriptions = deliveryInfoDescriptionDao.findBySheetId(sheet.getId());
            sheet.setDescriptions(descriptions);
        }

        return page;
    }

    public void save(DeliverySheet sheet) {
        if (sheet.getId() != null){
            sheetDao.save(sheet);
        }
    }
}
