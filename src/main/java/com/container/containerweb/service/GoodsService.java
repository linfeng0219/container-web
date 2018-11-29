package com.container.containerweb.service;

import com.container.containerweb.constants.GoodsStatus;
import com.container.containerweb.dao.GoodsDao;
import com.container.containerweb.dao.GoodsDescDao;
import com.container.containerweb.dto.GoodsAmountDto;
import com.container.containerweb.dto.QueryGoodsDto;
import com.container.containerweb.model.biz.Goods;
import com.container.containerweb.model.biz.GoodsDescription;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class GoodsService {

    @Resource
    private GoodsDao goodsDao;

    @Resource
    private GoodsDescDao goodsDescDao;

    @Value("${web.upload.img}")
    private String imgPath;

    public List<Goods> addGoods(List<GoodsAmountDto> dtos) {
        List<Goods> all = new ArrayList<>();
        String batchNo = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        for (GoodsAmountDto dto : dtos) {
            List<Goods> goodsList = new ArrayList<>();
            GoodsDescription description = goodsDescDao.findOne(dto.getId());
            for (int i = 0; i < dto.getAmount(); i++) {
                Goods goods = new Goods();
                goods.setGoodsDescription(description);
                goods.setStatus(GoodsStatus.PRODUCED.getCode());
                goods.setBarcode(Long.toString(System.nanoTime()));
                goods.setBatchNo(batchNo);
                goods.setCreateTime(System.currentTimeMillis());
                goodsList.add(goods);
                goodsDao.save(goodsList);
                all.addAll(goodsList);
            }
        }
        return all;
    }

    public void saveGoods(Goods goods) {

    }

    public Page<Goods> getPage(QueryGoodsDto dto) {
        return goodsDao.findAll(new PageRequest(dto.getPage() - 1, dto.getSize()));
    }

    public List<Goods> getGoodsByBatchNo(String batchNo) {
        List<Goods> goodsList = goodsDao.findByBatchNo(batchNo);
        for (Goods goods : goodsList) {
            goods.setGoodsDescription(null);
            //goods.setVendingMachine(null);
        }
        return goodsList;
    }
}
