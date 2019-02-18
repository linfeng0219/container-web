package com.container.containerweb.dto;

import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleDto roleDto = (RoleDto) o;
        return Objects.equals(name, roleDto.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public RoleDto(String name) {
        this.name = name;
    }
}
