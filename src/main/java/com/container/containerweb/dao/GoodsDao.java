package com.container.containerweb.dao;

import com.container.containerweb.model.biz.Goods;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GoodsDao extends JpaRepository<Goods, Long> {
    List<Goods> findByBarcodeIn(List<String> codes);

    Goods findByBarcodeAndIdx(String code, String idx);

    List<Goods> findByBatchNo(String batchNo);

    List<Goods> findByVendingMachineId(int machineId);
}

