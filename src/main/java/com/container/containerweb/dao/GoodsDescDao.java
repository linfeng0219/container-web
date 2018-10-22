package com.container.containerweb.dao;

import com.container.containerweb.model.biz.GoodsDescription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodsDescDao extends JpaRepository<GoodsDescription, Integer> {
}
