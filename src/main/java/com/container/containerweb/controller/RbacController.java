package com.container.containerweb.controller;

import com.container.containerweb.base.BaseResponse;
import com.container.containerweb.constants.ErrorCodes;
import com.container.containerweb.dto.AuthorityDto;
import com.container.containerweb.dto.QueryUserDto;
import com.container.containerweb.model.rbac.Role;
import com.container.containerweb.model.rbac.User;
import com.container.containerweb.service.RbacService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class RbacController {

    @Resource
    private RbacService rbacService;

    @PostMapping("/user/save")
    public Object saveUser(@RequestBody User user) {
        try {
            rbacService.saveUser(user);
            return BaseResponse.success();
        } catch (Exception e) {
            return BaseResponse.error(ErrorCodes.saveUserError, e.getMessage());
        }
    }

    @PostMapping("/user/disable")
    public Object disableUser(Integer userId) {
        try {
            rbacService.disableUser(userId);
            return BaseResponse.success();
        } catch (Exception e) {
            return BaseResponse.error(ErrorCodes.disableUserError, e.getMessage());
        }
    }

    @PostMapping("/user/authorize")
    public Object authorizeUser(@RequestBody AuthorityDto authorityDto) {
        try {
            rbacService.authorizeUser(authorityDto.getSubject(), authorityDto.getAuthorityIds());
            return BaseResponse.success();
        } catch (Exception e) {
            return BaseResponse.error(ErrorCodes.authorizeUserError, e.getMessage());
        }
    }

    @PostMapping("/role/save")
    public Object saveRole(@RequestBody Role role) {
        try {
            rbacService.saveRole(role);
            return BaseResponse.success();
        } catch (Exception e) {
            return BaseResponse.error(ErrorCodes.saveRoleError, e.getMessage());
        }
    }

    @PostMapping("/role/authorize")
    public Object authorizeRole(@RequestBody AuthorityDto dto) {
        try {
            rbacService.authorizeRole(dto.getSubject(), dto.getAuthorityIds());
            return BaseResponse.success();
        } catch (Exception e) {
            return BaseResponse.error(ErrorCodes.authorizeRoleError, e.getMessage());
        }
    }

    @PostMapping("/resource/save")
    public Object saveResource(@RequestBody com.container.containerweb.model.rbac.Resource resource) {
        try {
            rbacService.saveResource(resource);
            return BaseResponse.success();
        } catch (Exception e) {
            return BaseResponse.error(ErrorCodes.saveResourceError, e.getMessage());
        }
    }

    @GetMapping("/user/query")
    public Object queryUser(QueryUserDto dto) {
        try {
            Page<User> data = rbacService.queryUser(dto);
            return BaseResponse.success(data);
        } catch (Exception e) {
            return BaseResponse.error(ErrorCodes.queryUserError, e.getMessage());
        }
    }
}
