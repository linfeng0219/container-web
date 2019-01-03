package com.container.containerweb.dao;

import com.container.containerweb.model.biz.DeliveryInfoDescription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeliveryInfoDescriptionDao extends JpaRepository<DeliveryInfoDescription, Integer> {
    List<DeliveryInfoDescription> findBySheetId(Integer id);
}
