package com.container.containerweb.dto;

import com.container.containerweb.model.biz.Merchant;

import java.util.List;

public class UserDto {
    private Integer id;

    private String name;

    private String phone;

    private String password;

    private Merchant merchant;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    private List<RoleDto> roles;

    public UserDto(Integer id, String name, Merchant merchant) {
        this.id = id;
        this.merchant = merchant;
        this.name = name;
    }

    public UserDto(Integer id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

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

    public List<RoleDto> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleDto> roles) {
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }
}
