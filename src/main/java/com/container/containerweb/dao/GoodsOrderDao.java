package com.container.containerweb.dao;

import com.container.containerweb.model.biz.GoodsOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodsOrderDao extends JpaRepository<GoodsOrder, Long> {

    GoodsOrder findGoodsOrderByGoodsBarcode(String barcode);
}
