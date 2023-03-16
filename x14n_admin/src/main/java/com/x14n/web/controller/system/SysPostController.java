package com.x14n.web.controller.system;

import com.x14n.common.constant.UserConstants;
import com.x14n.common.core.controller.BaseController;
import com.x14n.common.core.domain.AjaxResult;
import com.x14n.common.core.page.TableDataInfo;
import com.x14n.system.domain.SysPost;
import com.x14n.system.service.ISysPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/system/post")
public class SysPostController extends BaseController {

    @Autowired
    ISysPostService postService;

    @GetMapping("/list")
    public TableDataInfo list(SysPost post) {
        startPage();
        List<SysPost> list = postService.selectPostList(post);
        return getDataTable(list);
    }

    @GetMapping(value = "/{postId}")
    public AjaxResult getInfo(@PathVariable Long postId) {
        return AjaxResult.success(postService.selectPostById(postId));
    }

    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysPost post) {
        if (UserConstants.NOT_UNIQUE.equals(postService.checkPostNameUnique(post))){
            return AjaxResult.error("新增岗位'" + post.getPostName() + "'失败，岗位已存在");
        }
        else if (UserConstants.NOT_UNIQUE.equals(postService.checkPostCodeUnique(post))){
            return AjaxResult.error("新增岗位'"+post.getPostName()+"'失败，岗位编码已存在");
        }
        post.setCreateBy(getUsername());
        return toAjax(postService.insertPost(post));
    }

}
