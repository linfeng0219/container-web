package com.container.containerweb.service;

import com.container.containerweb.dao.GoodsCollectDao;
import com.container.containerweb.dto.QueryGoodsCollectDto;
import com.container.containerweb.model.biz.GoodsCollect;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
public class GoodsCollectService {

    @Resource
    private GoodsCollectDao collectDao;

    public Page<GoodsCollect> getPage(QueryGoodsCollectDto dto, Integer merchantId) {
        Specification<GoodsCollect> specification = specificationOfQueryOrderDto(dto);
        return collectDao.findAll(specification, new PageRequest(dto.getPage() - 1, dto.getSize()));
    }

    private Specification<GoodsCollect> specificationOfQueryOrderDto(QueryGoodsCollectDto dto) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (dto.getMachineSerial() != null) {
                predicates.add(cb.equal(root.get("machineSerial").as(String.class), dto.getMachineSerial()));
            }
            if (dto.getDeliverBatchNo() != null) {
                predicates.add(cb.equal(root.get("deliverBatchNo").as(String.class), dto.getDeliverBatchNo()));
            }
            if (dto.getGoodsDesc() != null) {
                predicates.add(cb.equal(root.get("goodsDesc").as(String.class), dto.getGoodsDesc()));
            }
            if (dto.getFrom() != null && dto.getTo() != null) {
                predicates.add(cb.between(root.get("createTime"), dto.getFrom(), dto.getTo()));
            }
            return query.where(predicates.toArray(new Predicate[0])).getRestriction();
        };
    }
}