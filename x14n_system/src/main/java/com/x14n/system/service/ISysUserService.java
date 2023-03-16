package com.x14n.system.service;

import com.x14n.common.core.domain.entity.SysUser;
import com.x14n.system.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface ISysUserService {

    public SysUser selectUserByUserName(String userName);

    public List<SysUser> selectUserList(SysUser user);

    public String checkUserNameUnique(String userName);


    String checkUserPhoneNumber(SysUser user);

    String checkUserEmailUnique(SysUser sysUser);

    int insertUser(SysUser user);

    void checkUserAllowed(SysUser user);

    void checkUserDataScope(Long userId);

    int updateUser(SysUser user);

    int deleteUserByIds(Long[] userIds);

    int resetPwd(SysUser user);

    SysUser selectUserById(Long userId);

    String selectUserRoleGroup(String userName);

    String selectUserPostGroup(String userName);

    int resetUserPwd(String username, String password);

    boolean updateUserAvatar(String username, String avatar);

    int updateUserProfile(SysUser user);
}
