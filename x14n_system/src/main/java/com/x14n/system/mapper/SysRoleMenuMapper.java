package com.x14n.system.mapper;

import com.x14n.system.domain.SysRoleMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;



public interface SysRoleMenuMapper {

    int batchRoleMenu(List<SysRoleMenu> list);

    int deleteRoleMenu(Long[] ids);
}
