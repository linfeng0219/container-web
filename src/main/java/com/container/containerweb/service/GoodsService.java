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
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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

    public void addGoods(List<GoodsAmountDto> dtos) {
        for (GoodsAmountDto dto : dtos) {
            List<Goods> goodsList = new ArrayList<>();
            GoodsDescription description = goodsDescDao.findOne(dto.getId());
            for (int i = 0; i < dto.getAmount(); i++) {
                Goods goods = new Goods();
                goods.setGoodsDescription(description);
                goods.setStatus(GoodsStatus.PRODUCED.getCode());
                goodsList.add(goods);
                goodsDao.save(goodsList);
            }
        }
    }

    public void saveGoods(Goods goods) {

    }

    public Page<Goods> getPage(QueryGoodsDto dto) {
        return goodsDao.findAll(new PageRequest(dto.getPage() - 1, dto.getSize()));
    }
}
