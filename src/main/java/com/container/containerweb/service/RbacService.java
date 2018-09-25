package com.container.containerweb.service;

import com.container.containerweb.dao.ResourceDao;
import com.container.containerweb.dao.RoleDao;
import com.container.containerweb.dao.UserDao;
import com.container.containerweb.dto.QueryUserDto;
import com.container.containerweb.model.rbac.Role;
import com.container.containerweb.model.rbac.User;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RbacService {

    @Resource
    private UserDao userDao;

    @Resource
    private RoleDao roleDao;

    @Resource
    private ResourceDao resourceDao;

    public void saveUser(User user) {
        if (user.getId() != null) {
            userDao.updateUser(user);
        } else {
            userDao.save(user);
        }
    }

    public void authorizeUser(Integer userId, List<Integer> roleIds) {
        User user = userDao.findOne(userId);
        List<Role> roles = roleDao.findByIdIn(roleIds);
        user.setRoles(roles);

        userDao.save(user);
    }

    public void disableUser(Integer userId) {
        User user = userDao.findOne(userId);
        user.setEnabled(false);
        userDao.save(user);
    }

    public void saveRole(Role role) {
        if (role.getId() != null) {
            roleDao.updateRole(role);
        } else {
            roleDao.save(role);
        }
    }

    public void authorizeRole(Integer subject, List<Integer> authorityIds) {
        Role role = roleDao.findOne(subject);

        List<com.container.containerweb.model.rbac.Resource> resources = resourceDao.findByIdIn(authorityIds);

        role.setResources(resources);

        roleDao.save(role);
    }

    public void saveResource(com.container.containerweb.model.rbac.Resource resource) {
        if (resource.getId() != null) {
            resourceDao.updateResource(resource);
        } else {
            resourceDao.save(resource);
        }
    }

    public Page<User> queryUser(QueryUserDto dto) {
        PageRequest request = new PageRequest(dto.getPage(), dto.getSize());
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("phone", ExampleMatcher.GenericPropertyMatchers.contains());

        Example<User> example = Example.of(new User(), exampleMatcher);
        return userDao.findAll(example, request);
    }
}
