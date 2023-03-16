package com.x14n.web.controller.system;


import com.x14n.common.constant.UserConstants;
import com.x14n.common.core.controller.BaseController;
import com.x14n.common.core.domain.AjaxResult;
import com.x14n.common.core.domain.entity.SysRole;
import com.x14n.common.core.domain.entity.SysUser;
import com.x14n.common.core.page.TableDataInfo;
import com.x14n.common.utils.SecurityUtils;
import com.x14n.common.utils.StringUtils;
import com.x14n.system.service.ISysPostService;
import com.x14n.system.service.ISysRoleService;
import com.x14n.system.service.ISysUserService;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/system/user")
public class SysUserController extends BaseController {

    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysRoleService roleService;

    @Autowired
    private ISysPostService postService;



    @GetMapping("/list")
    public TableDataInfo list(SysUser user) {
        startPage();
        List<SysUser> list = userService.selectUserList(user);
        return getDataTable(list);
    }

    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysUser user) {
        if (UserConstants.NOT_UNIQUE.equals(userService.checkUserNameUnique(user.getUserName()))) {
            return AjaxResult.error("新增用户" + user.getUserName() + "'失败，登录账号已存在");
        } else if (StringUtils.isNotEmpty(user.getPhonenumber())
                && UserConstants.NOT_UNIQUE.equals(userService.checkUserPhoneNumber(user))) {
            return AjaxResult.error("新增用户" + user.getUserName() + "'失败，电话号码已存在");
        } else if (StringUtils.isNotEmpty(user.getEmail())
                && UserConstants.NOT_UNIQUE.equals(userService.checkUserEmailUnique(user))) {
            return AjaxResult.error("新增用户" + user.getUserName() + "'失败，邮箱已存在");
        }

        user.setCreateBy(getUsername());
        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        return toAjax(userService.insertUser(user));
    }


    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysUser user) {
        userService.checkUserAllowed(user);
        userService.checkUserDataScope(user.getUserId());
        if (StringUtils.isNotEmpty(user.getPhonenumber())
                && UserConstants.NOT_UNIQUE.equals(userService.checkUserPhoneNumber(user))) {
            return AjaxResult.error("新增用户" + user.getUserName() + "'失败，电话号码已存在");
        } else if (StringUtils.isNotEmpty(user.getEmail())
                && UserConstants.NOT_UNIQUE.equals(userService.checkUserEmailUnique(user))) {
            return AjaxResult.error("新增用户" + user.getUserName() + "'失败，邮箱已存在");
        }
        user.setUpdateBy(getUsername());
        return toAjax(userService.updateUser(user));
    }

    @GetMapping(value = { "/", "/{userId}" })
    public AjaxResult getInfo(@PathVariable(value = "userId", required = false) Long userId)
    {
        userService.checkUserDataScope(userId);
        AjaxResult ajax = AjaxResult.success();
        List<SysRole> roles = roleService.selectRoleAll();
        ajax.put("roles", SysUser.isAdmin(userId) ? roles : roles.stream().filter(r -> !r.isAdmin()).collect(Collectors.toList()));
        ajax.put("posts", postService.selectPostAll());
        if (StringUtils.isNotNull(userId))
        {
            SysUser sysUser = userService.selectUserById(userId);
            ajax.put(AjaxResult.DATA_TAG, sysUser);
            ajax.put("postIds", postService.selectPostListByUserId(userId));
            ajax.put("roleIds", sysUser.getRoles().stream().map(SysRole::getRoleId).collect(Collectors.toList()));
        }
        return ajax;
    }

    @DeleteMapping("/{userIds}")
    public AjaxResult remove(@PathVariable Long[] userIds) {
        if (ArrayUtils.contains(userIds,getUserId())){
            return error("不能删除自己");
        }
        return toAjax(userService.deleteUserByIds(userIds));
    }

    @PutMapping("/resetPwd")
    public AjaxResult resetPwd(@RequestBody SysUser user){
        userService.checkUserAllowed(user);
        userService.checkUserDataScope(user.getUserId());
        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        user.setUpdateBy(getUsername());
        return toAjax(userService.resetPwd(user));
    }
}
