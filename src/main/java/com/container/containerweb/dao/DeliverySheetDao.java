package com.container.containerweb.dao;

import com.container.containerweb.model.biz.DeliverySheet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeliverySheetDao extends JpaRepository<DeliverySheet, Long> {
    Page<DeliverySheet> findByMerchantId(Integer merchantId, PageRequest pageRequest);
    List<DeliverySheet> findByMerchantIdAndStatus(Integer merchantId,Integer status);
    DeliverySheet findFirstByBatchNo(String batchNo);
}
