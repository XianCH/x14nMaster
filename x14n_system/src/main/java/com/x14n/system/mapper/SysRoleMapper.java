package com.x14n.system.mapper;

import com.x14n.common.core.domain.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;



public interface SysRoleMapper {

    List<SysRole> selectRolePermissionByUserId(Long userId);

    public List<SysRole> selectRoleList(SysRole role);

    SysRole checkRoleNameUnique(String roleName);

    SysRole checkRoleKeyUnique(String roleKey);

    int insertRole(SysRole role);

    SysRole selectRoleById(Long roleId);

    int deleteRoleByIds(Long[] roleIds);

    List<SysRole> selectRolesByUserName(String userName);
}
