package com.x14n.framework.web.service;

import com.x14n.common.core.domain.entity.SysUser;
import com.x14n.common.core.domain.model.LoginUser;
import com.x14n.common.enums.UserStatus;
import com.x14n.common.exception.ServiceException;
import com.x14n.common.utils.StringUtils;
import com.x14n.system.service.ISysConfigService;
import com.x14n.system.service.ISysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    SysPermissionService sysPermissionService;

    @Autowired
    ISysUserService sysUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user = sysUserService.selectUserByUserName(username);

        if (StringUtils.isNull(user)) {
            log.info("login user:{}，do not exit", username);
            throw new ServiceException("longin user:" + username + "do not exit");
        } else if (UserStatus.DELETED.getCode().equals(user.getDelFlag())) {
            log.info("login user:{},has been deleted", username);
            throw new ServiceException("login user:" + username + "has been deleted");
        } else if (UserStatus.DISABLE.getCode().equals(user.getStatus())) {
            log.info("login user:{},hava been disabled", username);
            throw new ServiceException("login user:" + username + "hava been disabled");
        }

        UserDetails loginUser = createLoginUser(user);
        return loginUser;
    }

    public UserDetails createLoginUser(SysUser user){
        return new LoginUser(user.getUserId(),user.getDeptId(),user,sysPermissionService.getRolePermission(user));
    }
}
