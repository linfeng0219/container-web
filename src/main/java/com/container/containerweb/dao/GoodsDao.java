package com.container.containerweb.dao;

import com.container.containerweb.model.biz.Goods;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface GoodsDao extends JpaRepository<Goods, Long>, JpaSpecificationExecutor<Goods> {
    List<Goods> findByBarcodeIn(List<String> codes);

    Goods findByBarcodeAndIdx(String code, String idx);

    List<Goods> findByBatchNo(String batchNo);

    List<Goods> findByVendingMachineId(int machineId);

    Page<Goods> findByVendingMachineMerchantId(Integer merchantId, Pageable pageable);

    Page<Goods> findByVendingMachineMerchantIdAndStatus(Integer merchantId, Integer status, Pageable pageable);

    List<Goods> findByVendingMachineSerialAndStatus(String serial, int status);
}

