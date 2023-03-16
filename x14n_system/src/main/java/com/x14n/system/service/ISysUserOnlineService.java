package com.x14n.system.service;

import com.x14n.common.core.domain.model.LoginUser;
import com.x14n.system.domain.SysUserOnline;

public interface ISysUserOnlineService {

    SysUserOnline selectOnlineByInfo(String ipaddr, String userName, LoginUser user);

    SysUserOnline loginUserToUserOnline(LoginUser user);

    SysUserOnline selectOnlineByIpaddr(String ipaddr, LoginUser user);

    SysUserOnline selectOnlineByUserName(String userName, LoginUser user);
}
