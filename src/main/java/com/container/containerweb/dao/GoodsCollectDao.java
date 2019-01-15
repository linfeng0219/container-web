package com.container.containerweb.dao;

import com.container.containerweb.model.biz.GoodsCollect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GoodsCollectDao extends JpaRepository<GoodsCollect, Integer>, JpaSpecificationExecutor<GoodsCollect> {

    //已售数量更新
    @Modifying
    @Query("update GoodsCollect set soldAmount = soldAmount + 1, singleTotalAmount = price*(soldAmount+1) where deliverBatchNo = ?1 and goodsDesc = ?2")
    void updateSoldGoodsAmount(String batchNo, String description);

    //总量更新
    @Modifying
    @Query("update GoodsCollect set allTotalAmount = ?1 where deliverBatchNo = ?2")
    void updateTotalAmount(Integer sum, String batchNo);

    //上架更新
    @Modifying
    @Query("update GoodsCollect set actualDeliverAmount = actualDeliverAmount + 1 where deliverBatchNo = ?1 and goodsDesc = ?2")
    void updateActualDeliverAmount(String batchNo, String description);

    List<GoodsCollect> findByDeliverBatchNo(String batchNo);
}
