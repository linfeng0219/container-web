package com.container.containerweb.dao;

import com.container.containerweb.model.biz.Goods;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GoodsDao extends JpaRepository<Goods, Long> {
    List<Goods> findByBarCodeIn(List<String> codes);

    Goods findByBarCodeAndIdx(String code, String idx);
}
