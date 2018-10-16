package com.container.containerweb.controller;

import com.container.containerweb.base.BaseResponse;
import com.container.containerweb.constants.ErrorCodes;
import com.container.containerweb.dto.*;
import com.container.containerweb.model.rbac.Role;
import com.container.containerweb.model.rbac.User;
import com.container.containerweb.service.RbacService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class RbacController {

    @Resource
    private RbacService rbacService;

    @PostMapping("/user/login")
    public Object login(@RequestBody LoginDto dto, HttpSession session) {
        try {
            UserDto user = rbacService.login(dto.getUsername(), dto.getPassword());
            session.setAttribute("user", user);
            return BaseResponse.success(user);
        } catch (Exception e) {
            return BaseResponse.error(ErrorCodes.loginError, e.getMessage());
        }
    }

    @PostMapping("/user/add")
    public Object saveUser(@RequestBody User user) {
        try {
            rbacService.saveUser(user);
            return BaseResponse.success();
        } catch (Exception e) {
            return BaseResponse.error(ErrorCodes.saveUserError, e.getMessage());
        }
    }

    @PostMapping("/user/update")
    public Object updateUser(@RequestBody User user) {
        try {
            rbacService.updateUser(user);
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

    @GetMapping("/user/exact-query")
    public Object queryExactUser(QueryUserDto dto) {
        try {
            UserDto userDto = rbacService.queryUser(dto);
            return BaseResponse.success(userDto);
        } catch (Exception e) {
            return BaseResponse.error(ErrorCodes.queryUserError, e.getMessage());
        }
    }

    @GetMapping("/user/name-like-query")
    public Object queryUserNameLike(QueryUserDto dto) {
        try {
            List<UserDto> userDto = rbacService.queryUserByNameLike(dto.getName());
            return BaseResponse.success(userDto);
        } catch (Exception e) {
            return BaseResponse.error(ErrorCodes.queryUserError, e.getMessage());
        }
    }

    @GetMapping("/user/list")
    public Object userList(@PageableDefault Pageable pageable) {
        try {
            Page<UserDto> users = rbacService.getUserList(pageable);
            return BaseResponse.success(users);
        } catch (Exception e) {
            return BaseResponse.error(ErrorCodes.queryUserError, e.getMessage());
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

    @GetMapping("/role/list")
    public Object roleList() {
        try {
            List<RoleDto> list = rbacService.findAll();
            return BaseResponse.success(list);
        } catch (Exception e) {
            return BaseResponse.error(ErrorCodes.queryRoleError, e.getMessage());
        }
    }

    @GetMapping("/role/query")
    public Object queryRole(Integer id) {
        try {
            return BaseResponse.success(rbacService.queryRole(id));
        } catch (Exception e) {
            return BaseResponse.error(ErrorCodes.queryRoleError, e.getMessage());
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

    @GetMapping("/resource/list")
    public Object resourceList() {
        try {
            List<ResourceDto> list = rbacService.getResourceList();
            return BaseResponse.success(list);
        } catch (Exception e) {
            return BaseResponse.error(ErrorCodes.queryResourceError, e.getMessage());
        }
    }

    @GetMapping("/user/valid-session")
    public Object validSession(HttpSession session) {
        try {
            UserDto user = (UserDto) session.getAttribute("user");
            if (user != null)
                return BaseResponse.success(user);
            else
                throw new IllegalArgumentException("用户未登录");
        } catch (Exception e) {
            return BaseResponse.error(ErrorCodes.unlogonError, e.getMessage());
        }
    }
}
