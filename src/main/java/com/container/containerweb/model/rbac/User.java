package com.container.containerweb.model.rbac;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "sys_user")
public class User {

    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    @Column(length = 1)
    private String gender;

    private String phone;

    private String password;

    private int status;

    @ManyToMany
    private List<Role> roles;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public User(String name, String phone, String password) {
        this.name = name;
        this.phone = phone;
        this.password = password;
    }

    public User() {
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public User simple() {
        this.setRoles(null);
        this.setPassword(null);
        this.setPhone(null);
        this.setGender(null);
        return this;
    }
}
