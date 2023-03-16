package com.x14n.web.controller.system;


import com.x14n.common.constant.Constants;
import com.x14n.common.core.domain.AjaxResult;
import com.x14n.common.core.domain.entity.SysMenu;
import com.x14n.common.core.domain.entity.SysUser;
import com.x14n.common.core.domain.model.LoginBody;
import com.x14n.common.utils.SecurityUtils;
import com.x14n.framework.web.service.SysLoginService;
import com.x14n.framework.web.service.SysPermissionService;
import com.x14n.system.service.ISysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.aspectj.weaver.loadtime.Aj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
@Api(tags = "登录模块")
@RestController
public class SysLoginController {

    @Autowired
    private SysLoginService loginService;

    @Autowired
    private SysPermissionService permissionService;

    @Autowired
    private ISysMenuService sysMenuService;

    @ApiOperation(value = "登录")
    @ApiImplicitParam(name = "loginBody",value = "登录示例", required = true)
    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginBody loginBody) {
        String username = loginBody.getUsername();
        AjaxResult ajax = AjaxResult.success();
        String token = loginService.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode(), loginBody.getUuid());
        ajax.put(Constants.TOKEN,token);
        return ajax;
    }


    @GetMapping("getInfo")
    public AjaxResult getInfo()
    {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        //角色集合
        Set<String> roles = permissionService.getRolePermission(user);
        Set<String> menuPermission = permissionService.getMenuPermission(user);
        AjaxResult ajax = AjaxResult.success();
        ajax.put("user",user);
        ajax.put("roles",roles);
        ajax.put("permissions",menuPermission);
        return ajax;
    }

    @GetMapping("getRouters")
    public AjaxResult getRouters(){
        Long userId = SecurityUtils.getUserId();
        List<SysMenu> menus = sysMenuService.selectMenuTreeByUserId(userId);
        return AjaxResult.success(sysMenuService.buildMenus(menus));
    }

}
