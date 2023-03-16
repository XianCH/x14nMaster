package com.x14n.common.core.controller;


import com.github.pagehelper.PageInfo;
import com.x14n.common.constant.HttpStatus;
import com.x14n.common.core.domain.AjaxResult;
import com.x14n.common.core.domain.model.LoginUser;
import com.x14n.common.core.page.TableDataInfo;
import com.x14n.common.utils.DateUtils;
import com.x14n.common.utils.PageUtils;
import com.x14n.common.utils.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditorSupport;
import java.util.Date;
import java.util.List;

/**
 * web层通用数据处理
 */
public class BaseController {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());


    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // Date 类型转换
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(DateUtils.parseDate(text));
            }
        });
    }

    protected void startPage() {
        PageUtils.startPage();
    }

    /**
     * 响应请求分页数据
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    protected TableDataInfo getDataTable(List<?> list) {
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(HttpStatus.SUCCESS);
        rspData.setMsg("查询成功");
        rspData.setRows(list);
        rspData.setTotal(new PageInfo(list).getTotal());
        return rspData;
    }

    protected AjaxResult toAjax(int rows) {
        return rows > 0 ? AjaxResult.success() : AjaxResult.error();
    }

    public LoginUser getLoginUser() {
        return SecurityUtils.getLoginUser();
    }

    public String getUsername() {
        return getLoginUser().getUsername();
    }

    public Long getUserId() {
        return getLoginUser().getUserId();
    }

    public AjaxResult error(String message)
    {
        return AjaxResult.error(message);
    }
}
