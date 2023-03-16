package com.x14n.system.mapper;

import com.x14n.common.core.domain.entity.SysMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;


public interface SysMenuMapper {
    public List<String> selectMenuPermsByUserId(Long userId);

    public List<SysMenu> selectMenuTreeAll();

    public List<SysMenu> selectMenuTreeByUserId(Long userId);

    List<SysMenu> selectMenuList(SysMenu menu);

    List<SysMenu> selectMenuListByUserId(SysMenu menu);

    SysMenu selectMenuById(Long menuId);

    List<Long> selectMenuListByRoleId(@Param("roleId") Long roleId,@Param("menuCheckStrictly") boolean menuCheckStrictly);

    SysMenu checkMenuNameUnique(@Param("menuName") String menuName,@Param(("parentId")) Long parentId);

    int insertMenu(SysMenu menu);
}
