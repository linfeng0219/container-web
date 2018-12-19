package com.container.containerweb.service;

import com.container.containerweb.constants.UserStatus;
import com.container.containerweb.dao.ResourceDao;
import com.container.containerweb.dao.RoleDao;
import com.container.containerweb.dao.UserDao;
import com.container.containerweb.dto.QueryUserDto;
import com.container.containerweb.dto.ResourceDto;
import com.container.containerweb.dto.RoleDto;
import com.container.containerweb.dto.UserDto;
import com.container.containerweb.model.rbac.Resource;
import com.container.containerweb.model.rbac.Role;
import com.container.containerweb.model.rbac.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class RbacService {

    @javax.annotation.Resource
    private UserDao userDao;

    @javax.annotation.Resource
    private RoleDao roleDao;

    @javax.annotation.Resource
    private ResourceDao resourceDao;

    public void saveUser(User user) {
        userDao.save(user);
    }

    @Transactional
    public void updateUser(User user) {
        if (user.getId() != null)
            userDao.updateUser(user.getId(), user.getName(), user.getPhone(), user.getMerchant().getId());
        else
            throw new IllegalArgumentException("user id cannot be null");
    }

    public void authorizeUser(Integer userId, List<Integer> roleIds) {
        User user = userDao.findOne(userId);
        List<Role> roles = roleDao.findByIdIn(roleIds);
        user.setRoles(roles);

        userDao.save(user);
    }

    public void disableUser(Integer userId) {
        User user = userDao.findOne(userId);
        user.setStatus(UserStatus.DISABLED.getCode());
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

    public UserDto queryUser(QueryUserDto dto) {
        User user = null;
        if (dto.getId() != null) {
            user = userDao.findById(dto.getId());
        } else {
            user = userDao.findByNameOrPhone("%" + dto.getName() + "%", dto.getPhone());
        }
        if (user != null) {
            UserDto userDto = new UserDto(user.getId(), user.getName(), user.getPhone());
            userDto.setRoles(this.parseRoleListToRoleDtoList(user.getRoles()));
            return userDto;
        }
        return null;
    }

    public List<UserDto> queryUserByNameLike(String name) {
        List<User> users;
        if (StringUtils.isEmpty(name)) {
            users = userDao.findAll();
        } else {
            users = userDao.findByNameLike("%" + name + "%");
        }
        List<UserDto> userDtos = new ArrayList<>(users.size());
        for (User user : users) {
            UserDto userDto = new UserDto(user.getId(), user.getName(), user.getPhone());
            userDto.setRoles(this.parseRoleListToRoleDtoList(user.getRoles()));
            userDtos.add(userDto);
        }
        return userDtos;
    }

    public UserDto login(String username, String password) throws IllegalAccessException {

        User user = userDao.findByNameAndPassword(username, password);
        if (user == null)
            throw new IllegalAccessException("用户名或密码错误");

        UserDto userDto = new UserDto(user.getId(), user.getName(), user.getMerchant());
        userDto.setRoles(this.parseRoleListToRoleDtoList(user.getRoles()));
        return userDto;
    }

    public Page<RoleDto> rolePage(Pageable pageable) {
        Page<Role> page = roleDao.findAll(pageable);
        List<RoleDto> content = parseRoleListToRoleDtoList(page.getContent());
        return new PageImpl<>(content);
    }

    public List<RoleDto> findAll() {
        List<Role> roleList = roleDao.findAll();
        return parseRoleListToRoleDtoList(roleList);
    }

    private List<RoleDto> parseRoleListToRoleDtoList(List<Role> roleList) {
        List<RoleDto> roleDtoList = new ArrayList<>(roleList.size());

        for (Role role : roleList) {
            List<Resource> resourceList = role.getResources();
            List<ResourceDto> resourceDtoList = parseResourceListToResourceDtoList(resourceList);
            roleDtoList.add(new RoleDto(role.getId(), role.getName(), resourceDtoList));
        }
        return roleDtoList;
    }

    private List<ResourceDto> parseResourceListToResourceDtoList(List<Resource> resourceList) {
        List<ResourceDto> resourceDtoList = new ArrayList<>(resourceList.size());
        for (Resource resource : resourceList) {
            if (resource.getPid() == 0) {
                ResourceDto root = new ResourceDto(resource.getId(), resource.getName(), resource.getUrl(), resource.getIcon());
                List<ResourceDto> children = new ArrayList<>();
                for (Resource child : resourceList) {
                    if (child.getPid().equals(resource.getId())) {
                        children.add(new ResourceDto(child.getId(), child.getName(), child.getUrl(), child.getIcon()));
                    }
                }
                root.setChildren(children);
                resourceDtoList.add(root);
            }
        }
        return resourceDtoList;
    }

    public List<UserDto> getUserList(Pageable pageable) {
        List<User> list = userDao.findAll();
        List<UserDto> content = new ArrayList<>(list.size());
        for (User user : list) {
            UserDto dto = new UserDto(user.getId(), user.getName(), user.getPhone());
            if (user.getMerchant() != null)
                dto.setMerchant(user.getMerchant().simple());
            dto.setRoles(parseRoleListToRoleDtoList(user.getRoles()));
            content.add(dto);
        }
        return content;
    }

    public RoleDto queryRole(Integer id) {
        Role role = roleDao.findOne(id);
        return parseRoleListToRoleDtoList(Collections.singletonList(role)).get(0);
    }

    public List<ResourceDto> getResourceList() {
        List<Resource> resources = resourceDao.findAll();
        return parseResourceListToResourceDtoList(resources);
    }

    public User queryUserById(Integer userId) {
        return userDao.findById(userId);
    }
}
