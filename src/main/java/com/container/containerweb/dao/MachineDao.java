package com.container.containerweb.dao;

import com.container.containerweb.model.biz.Goods;
import com.container.containerweb.model.biz.Merchant;
import com.container.containerweb.model.biz.VendingMachine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MachineDao extends JpaRepository<VendingMachine, Integer> {
    VendingMachine findBySerial(String serial);

    List<VendingMachine> findByMerchantId(Integer merchantId);
}
