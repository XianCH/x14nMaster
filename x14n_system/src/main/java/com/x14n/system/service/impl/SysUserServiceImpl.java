package com.x14n.system.service.impl;

import com.x14n.common.annotation.DataScope;
import com.x14n.common.constant.UserConstants;
import com.x14n.common.core.domain.entity.SysRole;
import com.x14n.common.core.domain.entity.SysUser;
import com.x14n.common.exception.ServiceException;
import com.x14n.common.utils.SecurityUtils;
import com.x14n.common.utils.StringUtils;
import com.x14n.common.utils.spring.SpringUtils;
import com.x14n.system.domain.SysPost;
import com.x14n.system.domain.SysUserPost;
import com.x14n.system.domain.SysUserRole;
import com.x14n.system.mapper.*;
import com.x14n.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class SysUserServiceImpl implements ISysUserService {

    @Autowired
    SysUserMapper userMapper;

    @Autowired
    SysUserRoleMapper userRoleMapper;

    @Autowired
    SysUserPostMapper userPostMapper;

    @Autowired
    SysPostMapper postMapper;

    @Autowired
    SysRoleMapper roleMapper;


    @Override
    public SysUser selectUserByUserName(String userName) {
        return userMapper.selectUserByUserName(userName);
    }

    @Override
    public List<SysUser> selectUserList(SysUser user) {
        return userMapper.selectUserList(user);
    }

    @Override
    public String checkUserNameUnique(String userName) {
        int count = userMapper.checkUserNameUnique(userName);
        if (count > 0) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public String checkUserPhoneNumber(SysUser user) {
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        SysUser info = userMapper.checkUserPhoneNumberUnique(user.getPhonenumber());
        if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public String checkUserEmailUnique(SysUser user) {
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        SysUser info = userMapper.checkUserEmailUnique(user.getEmail());
        if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    @Transactional
    public int insertUser(SysUser user) {
        int rows = userMapper.insertUser(user);
        //新建用户岗位关联
        insertUserPost(user);
        //新建用户和角色管理
        insertUserRole(user);
        return rows;
    }

    @Override
    public void checkUserAllowed(SysUser user) {
        if (StringUtils.isNotNull(user.getUserId()) && user.isAdmin()) {
            throw new ServiceException("不允许操作着急管理员用户");
        }
    }

    @Override
    public void checkUserDataScope(Long userId) {
        if (!SysUser.isAdmin(SecurityUtils.getUserId())) {
            SysUser user = new SysUser();
            user.setUserId(userId);
            List<SysUser> users = SpringUtils.getAopProxy(this).selectUserList(user);
            if (StringUtils.isEmpty(users)) {
                throw new ServiceException("没有权限访问用户数据！");
            }
        }
    }

    @Override
    @Transactional
    public int updateUser(SysUser user) {
        Long userId = user.getUserId();
        userRoleMapper.deleteUserRoleByUserId(userId);
        insertUserRole(user);
        userPostMapper.deleteUserPostByUserId(userId);
        insertUserPost(user);
        return userMapper.updateUser(user);
    }

    @Override
    @Transactional
    public int deleteUserByIds(Long[] userIds) {
        for (Long userId : userIds) {
            checkUserAllowed(new SysUser(userId));
            checkUserDataScope(userId);
        }
        userRoleMapper.deleteUserRole(userIds);
        userPostMapper.deleteUserPost(userIds);
        return userMapper.deleteUserByIds(userIds);
    }

    @Override
    public int resetPwd(SysUser user) {
        return userMapper.updateUser(user);
    }

    @Override
    public SysUser selectUserById(Long userId) {
        return userMapper.selectUserById(userId);
    }

    @Override
    public String selectUserRoleGroup(String userName) {
        List<SysRole> list = roleMapper.selectRolesByUserName(userName);
        if (CollectionUtils.isEmpty(list)) {
            return StringUtils.EMPTY;
        }
        return list.stream().map(SysRole::getRoleName).collect(Collectors.joining(","));
    }

    @Override
    public String selectUserPostGroup(String userName) {
        List<SysPost> list = postMapper.selectPostsByUserName(userName);
        if (CollectionUtils.isEmpty(list)) {
            return StringUtils.EMPTY;
        }
        return list.stream().map(SysPost::getPostName).collect(Collectors.joining(","));
    }

    @Override
    public int resetUserPwd(String username, String password) {
        return userMapper.resetUserPwd(username, password);
    }

    @Override
    public boolean updateUserAvatar(String username, String avatar) {
        return userMapper.updateUserAvatar(username,avatar) > 0;
    }

    @Override
    public int updateUserProfile(SysUser user) {
        return userMapper.updateUser(user);
    }

    public void insertUserPost(SysUser user) {
        Long[] posts = user.getPostIds();
        if (StringUtils.isNotNull(posts)) {
            // 新增用户与岗位管理
            List<SysUserPost> list = new ArrayList<SysUserPost>();
            for (Long postId : posts) {
                SysUserPost up = new SysUserPost();
                up.setUserId(user.getUserId());
                up.setPostId(postId);
                list.add(up);
            }
            if (list.size() > 0) {
                userPostMapper.batchUserPost(list);
            }
        }
    }

    public void insertUserRole(SysUser user) {
        Long[] roles = user.getRoleIds();
        if (StringUtils.isNotNull(roles)) {
            // 新增用户与角色管理
            List<SysUserRole> list = new ArrayList<SysUserRole>();
            for (Long roleId : roles) {
                SysUserRole ur = new SysUserRole();
                ur.setUserId(user.getUserId());
                ur.setRoleId(roleId);
                list.add(ur);
            }
            if (list.size() > 0) {
                userRoleMapper.batchUserRole(list);
            }
        }
    }


}
