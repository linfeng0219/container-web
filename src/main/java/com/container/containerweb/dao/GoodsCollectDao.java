package com.container.containerweb.dao;

import com.container.containerweb.model.biz.GoodsCollect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface GoodsCollectDao extends JpaRepository<GoodsCollect, Integer>, JpaSpecificationExecutor<GoodsCollect> {
}
