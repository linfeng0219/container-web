package com.container.containerweb.dao;

import com.container.containerweb.model.rbac.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {

    @Modifying
    @Query("update User u set u.name = ?2 ,u.phone = ?3 , u.merchant.id = ?4 where u.id = ?1")
    void updateUser(int id, String name, String phone, Integer merchantId);

    User findByNameAndPassword(String name, String password);

    User findByNameOrPhone(String name, String phone);

    User findById(Integer id);

    List<User> findByNameLike(String name);
}
