package com.x14n.system.mapper;

import com.x14n.system.domain.SysUserPost;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;



public interface SysUserPostMapper {

    int batchUserPost(List<SysUserPost> list);

    void deleteUserPostByUserId(Long userId);

    void deleteUserPost(Long[] userIds);
}
