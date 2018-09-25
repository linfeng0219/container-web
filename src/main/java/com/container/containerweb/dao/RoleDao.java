package com.container.containerweb.dao;

import com.container.containerweb.model.rbac.Resource;
import com.container.containerweb.model.rbac.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface RoleDao extends JpaRepository<Role, Integer> {

    List<Role> findByIdIn(Collection<Integer> ids);

    @Query("update Role r set r.name = :#{#role.name} where r.id = :#{#role.id}")
    void updateRole(Role role);

}
