package com.container.containerweb.dao;

import com.container.containerweb.model.rbac.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ResourceDao extends JpaRepository<Resource, Integer> {

    List<Resource> findByIdIn(Collection<Integer> ids);

    @Query("update Resource r set r.name = :#{#resource.name}, r.url = :#{#resource.url} where r.id = :#{#resource.id}")
    void updateResource(Resource resource);
}
