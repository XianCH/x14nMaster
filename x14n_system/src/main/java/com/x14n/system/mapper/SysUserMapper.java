package com.x14n.system.mapper;


import com.x14n.common.core.domain.entity.SysUser;
import com.x14n.common.core.domain.model.LoginUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface SysUserMapper {

    public SysUser selectUserByUserName(String UserName);

    List<SysUser> selectUserList(SysUser user);

    int checkUserNameUnique(String userName);

    SysUser checkUserPhoneNumberUnique(String phonenumber);

    SysUser checkUserEmailUnique(String email);

    int insertUser(SysUser user);

    int updateUser(SysUser user);

    int deleteUserByIds(Long[] userIds);

    SysUser selectUserById(Long userId);

    int resetUserPwd(@Param("userName") String username, @Param("password") String password);

    int updateUserAvatar(@Param("userName") String username,@Param("avatar") String avatar);
}
