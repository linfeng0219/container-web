package com.container.containerweb.dto;

import java.util.List;

public class RoleDto {

    private int id;

    private String name;

    private List<ResourceDto> resources;

    public RoleDto(int id, String name, List<ResourceDto> resources) {
        this.id = id;
        this.name = name;
        this.resources = resources;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ResourceDto> getResources() {
        return resources;
    }

    public void setResources(List<ResourceDto> resources) {
        this.resources = resources;
    }
}
