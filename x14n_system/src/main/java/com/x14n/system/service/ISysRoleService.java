package com.x14n.system.service;

import com.x14n.common.core.domain.entity.SysRole;

import java.util.List;
import java.util.Set;

public interface ISysRoleService {

    public Set<String> selectRolePermissionByUserId(Long userId);

    List<SysRole> selectRoleList(SysRole role);

    String checkRoleNameUnique(SysRole role);

    String checkRoleKeyUnique(SysRole role);

    int insertRole(SysRole role);

    int deleteRoleByIds(Long[] roleIds);

    public void checkRoleAllowed(SysRole role);

    public void checkRoleDataScope(Long roleId);

    public SysRole selectRoleById(Long roleId);

    public int countUserRoleByRoleId(Long roleId);

    List<SysRole> selectRoleAll();
}
