package com.container.containerweb.service;

import com.container.containerweb.dao.GoodsCollectDao;
import com.container.containerweb.dto.QueryGoodsCollectDto;
import com.container.containerweb.model.biz.GoodsCollect;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class GoodsCollectService {

    @Resource
    private GoodsCollectDao collectDao;

    public Page<GoodsCollect> getPage(QueryGoodsCollectDto dto, Integer merchantId) {
        return collectDao.findAll(new PageRequest(dto.getPage() - 1, dto.getSize()));
    }
}
