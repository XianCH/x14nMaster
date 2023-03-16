package com.x14n.system.mapper;

import com.x14n.system.domain.SysUserRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


public interface SysUserRoleMapper {

    int batchUserRole(List<SysUserRole> userRoleList);

    void deleteUserRoleByUserId(Long userId);

    void deleteUserRole(Long[] userIds);

    int countUserRoleByRoleId(Long roleId);
}
