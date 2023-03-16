package com.x14n.web.controller.system;


import com.x14n.common.constant.UserConstants;
import com.x14n.common.core.controller.BaseController;
import com.x14n.common.core.domain.AjaxResult;
import com.x14n.common.core.domain.entity.SysRole;
import com.x14n.common.core.page.TableDataInfo;
import com.x14n.system.service.ISysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/role")
public class SysRoleController extends BaseController {

    @Autowired
    private ISysRoleService roleService;

    @GetMapping("/list")
    public TableDataInfo list(SysRole role) {
        startPage();
        List<SysRole> sysRoles = roleService.selectRoleList(role);
        return getDataTable(sysRoles);
    }

    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysRole role) {
        if (UserConstants.NOT_UNIQUE.equals(roleService.checkRoleNameUnique(role))) {
            return AjaxResult.error("新增角色'" + role.getRoleName() + "'失败，角色名称已存在");
        } else if (UserConstants.NOT_UNIQUE.equals(roleService.checkRoleKeyUnique(role))) {
            return AjaxResult.error("新增角色'" + role.getRoleName() + "'失败，角色权限已存在");
        }
        role.setCreateBy(getUsername());
        return toAjax(roleService.insertRole(role));
    }

    @DeleteMapping("/{roleIds}")
    public AjaxResult remove(@PathVariable Long[] roleIds) {
        return toAjax(roleService.deleteRoleByIds(roleIds));
    }
}
