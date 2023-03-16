package com.x14n.system.service;

import com.x14n.system.domain.SysPost;

import java.util.List;

public interface ISysPostService {
    List<SysPost> selectPostAll();

    List<Long> selectPostListByUserId(Long userId);

    List<SysPost> selectPostList(SysPost post);

    SysPost selectPostById(Long postId);

    String checkPostNameUnique(SysPost post);

    String checkPostCodeUnique(SysPost post);

    int insertPost(SysPost post);
}
