package com.x14n.system.service;

import com.x14n.common.core.domain.TreeSelect;
import com.x14n.common.core.domain.entity.SysMenu;
import com.x14n.system.domain.vo.RouterVo;

import java.util.List;
import java.util.Set;

public interface ISysMenuService {

    public Set<String> selectMenuPermsByUserId(Long userId);

    public List<SysMenu> selectMenuTreeByUserId(Long userId);

    public List<RouterVo> buildMenus(List<SysMenu> menus);


    List<SysMenu> selectMenuList(Long userId);

    List<SysMenu> selectMenuList(SysMenu menu,Long userId);

    SysMenu selectMenuById(Long menuId);

    List<TreeSelect> buildMenuTreeSelect(List<SysMenu> menus);

    List<SysMenu> buildMenuTree(List<SysMenu> menus);

    List<Long> selectMenuListByRoleId(Long roleId);

    String checkMenuNameUnique(SysMenu menu);

    int insertMenu(SysMenu menu);
}
