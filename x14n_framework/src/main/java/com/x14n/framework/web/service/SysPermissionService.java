package com.x14n.framework.web.service;

import com.x14n.common.core.domain.entity.SysUser;
import com.x14n.system.service.ISysMenuService;
import com.x14n.system.service.ISysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;


@Component
public class SysPermissionService {

    @Autowired
    private ISysRoleService roleService;

    @Autowired
    private ISysMenuService menuService;


    public Set<String> getRolePermission(SysUser user) {
        Set<String> roles = new HashSet<>();
        if (user.isAdmin()) {
            roles.add("admin");
        } else {
            roles.addAll(roleService.selectRolePermissionByUserId(user.getUserId()));
        }
        return roles;
    }

    public Set<String> getMenuPermission(SysUser user){
        Set<String> perms = new HashSet<>();
        if (user.isAdmin()){
            perms.add("*:*:*");
        }
        else {
            perms.addAll(menuService.selectMenuPermsByUserId(user.getUserId()));
        }

        return perms;
    }

}
