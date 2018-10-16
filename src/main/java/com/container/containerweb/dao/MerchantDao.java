package com.container.containerweb.dao;

import com.container.containerweb.model.biz.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MerchantDao extends JpaRepository<Merchant,Integer> {
    List<Merchant> findByNameLike(String name);
}
