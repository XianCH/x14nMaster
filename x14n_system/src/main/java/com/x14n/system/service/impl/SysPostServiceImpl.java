package com.x14n.system.service.impl;

import com.sun.org.apache.regexp.internal.RE;
import com.x14n.common.constant.UserConstants;
import com.x14n.common.utils.StringUtils;
import com.x14n.system.domain.SysPost;
import com.x14n.system.mapper.SysPostMapper;
import com.x14n.system.service.ISysPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysPostServiceImpl implements ISysPostService {

    @Autowired
    private SysPostMapper postMapper;

    @Override
    public List<SysPost> selectPostAll() {
        return postMapper.selectPostAll();
    }

    @Override
    public List<Long> selectPostListByUserId(Long userId) {
        return postMapper.selectPostListByUserId(userId);
    }

    @Override
    public List<SysPost> selectPostList(SysPost post) {
        return postMapper.selectPostList(post);
    }

    @Override
    public SysPost selectPostById(Long postId) {
        return postMapper.selectPostById(postId);
    }

    @Override
    public String checkPostNameUnique(SysPost post) {
        Long postId = StringUtils.isNull(post.getPostId()) ? -1L : post.getPostId();
        SysPost info = postMapper.checkPostNameUnique(post.getPostName());
        if (StringUtils.isNotNull(info) && info.getPostId().longValue() != postId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public String checkPostCodeUnique(SysPost post) {
        Long postId = StringUtils.isNull(post.getPostId()) ? -1L : post.getPostId();
        SysPost info = postMapper.checkPostCodeUnique(post.getPostCode());
        if (StringUtils.isNotNull(info) && info.getPostId().longValue() != postId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public int insertPost(SysPost post) {
        return postMapper.insertPost(post);
    }
}
