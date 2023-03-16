package com.x14n.web.controller.system;

import com.x14n.common.config.X14nConfig;
import com.x14n.common.constant.UserConstants;
import com.x14n.common.core.controller.BaseController;
import com.x14n.common.core.domain.AjaxResult;
import com.x14n.common.core.domain.entity.SysUser;
import com.x14n.common.core.domain.model.LoginUser;
import com.x14n.common.utils.SecurityUtils;
import com.x14n.common.utils.StringUtils;
import com.x14n.common.utils.file.FileUploadUtils;
import com.x14n.framework.web.service.TokenService;
import com.x14n.system.service.ISysUserService;
import org.aspectj.weaver.loadtime.Aj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.security.util.Password;

import java.io.IOException;

@RestController
@RequestMapping("/system/user/profile")
public class SysProfileController extends BaseController {

    @Autowired
    private ISysUserService userService;

    @Autowired
    private TokenService tokenService;

    @GetMapping
    public AjaxResult profile() {
        LoginUser loginUser = getLoginUser();
        SysUser user = loginUser.getUser();
        AjaxResult ajax = AjaxResult.success(user);
        ajax.put("roleGroup", userService.selectUserRoleGroup(loginUser.getUsername()));
        ajax.put("postGroup", userService.selectUserPostGroup(loginUser.getUsername()));
        return ajax;
    }

    @PutMapping()
    public AjaxResult updateProfile(@RequestBody SysUser user) {
        LoginUser loginUser = getLoginUser();
        SysUser sysUser = loginUser.getUser();
        user.setUserName(sysUser.getUserName());
        if (StringUtils.isNotEmpty(user.getPhonenumber())
                && UserConstants.NOT_UNIQUE.equals(userService.checkUserPhoneNumber(user))) {
            return AjaxResult.error("修改用户'" + user.getUserName() + "'失败，手机号码已存在");
        }
        if (StringUtils.isNotEmpty(user.getEmail())
                && UserConstants.NOT_UNIQUE.equals(userService.checkUserEmailUnique(user))) {
            return AjaxResult.error("修改用户'" + user.getUserName() + "'失败，邮箱账号已存在");
        }
        user.setUserId(sysUser.getUserId());
        user.setPassword(null);
        if (userService.updateUserProfile(user) > 0) {
            // 更新缓存用户信息
            sysUser.setNickName(user.getNickName());
            sysUser.setPhonenumber(user.getPhonenumber());
            sysUser.setEmail(user.getEmail());
            sysUser.setSex(user.getSex());
            tokenService.setLoginUser(loginUser);
            return AjaxResult.success();
        }
        return AjaxResult.error("修改个人信息异常，请联系管理员");
    }


    @PostMapping("/avatar")
    public AjaxResult avatar(@RequestParam("avatarfile") MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            LoginUser loginUser = getLoginUser();
            String avatar = FileUploadUtils.upload(X14nConfig.getAvatarPath(), file);
            if (userService.updateUserAvatar(loginUser.getUsername(), avatar)) {
                AjaxResult ajax = AjaxResult.success();
                ajax.put("imgUrl", avatar);
                loginUser.getUser().setAvatar(avatar);
                tokenService.setLoginUser(loginUser);
                return ajax;
            }
        }
        return AjaxResult.error("上传图片失败");
    }


    @PutMapping("/updatePwd")
    public AjaxResult updatePwd(String oldPassword, String newPassword) {
        LoginUser loginUser = getLoginUser();
        String username = loginUser.getUsername();
        String password = loginUser.getPassword();
        if (!SecurityUtils.matchesPassword(oldPassword, password)) {
            return AjaxResult.error("修改密码失败，旧密码错误");
        }
        if (SecurityUtils.matchesPassword(newPassword, password)) {
            return AjaxResult.error("新密码不能与旧密码吧相同");
        }
        if (userService.resetUserPwd(username, SecurityUtils.encryptPassword(newPassword)) > 0) {
            loginUser.getUser().setPassword(SecurityUtils.encryptPassword(newPassword));
            tokenService.setLoginUser(loginUser);
            return AjaxResult.success();
        }
        return AjaxResult.error("修改密码异常");
    }
}
