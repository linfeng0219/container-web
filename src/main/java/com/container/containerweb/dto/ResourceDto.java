package com.container.containerweb.dto;

import java.util.List;

public class ResourceDto {
    private int id;

    private String name;

    private List<ResourceDto> children;

    private String url;

    private String icon;

    public ResourceDto(int id, String name, String url, String icon) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.icon = icon;
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

    public List<ResourceDto> getChildren() {
        return children;
    }

    public void setChildren(List<ResourceDto> children) {
        this.children = children;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
