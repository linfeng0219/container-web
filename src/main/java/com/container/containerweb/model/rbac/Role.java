package com.container.containerweb.model.rbac;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class Role {

    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    @ManyToMany
    private List<Resource> resources;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }
}
