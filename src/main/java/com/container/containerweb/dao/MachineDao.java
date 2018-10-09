package com.container.containerweb.dao;

import com.container.containerweb.model.biz.VendingMachine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MachineDao extends JpaRepository<VendingMachine, Integer> {
    VendingMachine findBySerial(String serial);
}
