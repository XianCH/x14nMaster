package com.x14n.system.mapper;


import com.x14n.common.core.domain.entity.SysRole;
import com.x14n.system.domain.SysPost;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


public interface SysPostMapper {

    List<SysPost> selectPostAll();

    List<Long> selectPostListByUserId(Long userId);

    List<SysPost> selectPostsByUserName(String userName);


    List<SysPost> selectPostList(SysPost post);

    SysPost selectPostById(Long postId);

    SysPost checkPostNameUnique(String postName);

    SysPost checkPostCodeUnique(String postCode);

    int insertPost(SysPost post);
}
