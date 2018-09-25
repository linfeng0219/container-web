package com.container.containerweb.dao;

import com.container.containerweb.model.rbac.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {

    @Query("update User u set u.name = :#{#user.name} ,u.phone = :#{#user.phone}, u.gender = :#{#user.gender} where u.id = :#{#user.id}")
    void updateUser(@Param("user") User user);
}
