package com.container.containerweb.dao;

import com.container.containerweb.model.biz.GoodsOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface GoodsOrderDao extends JpaRepository<GoodsOrder, Long>, JpaSpecificationExecutor<GoodsOrder> {

    GoodsOrder findGoodsOrderByGoodsBarcode(String barcode);

    GoodsOrder findByOrderNo(String orderNo);
}
