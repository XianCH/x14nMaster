package com.x14n.common.utils;

import com.github.pagehelper.PageHelper;
import com.x14n.common.core.page.PageDomain;
import com.x14n.common.core.page.TableSupport;
import com.x14n.common.utils.sql.SqlUtil;

/**
 * 分页工具类
 * 
 * @author x14n
 */
public class PageUtils extends PageHelper
{

    public static void startPage()
    {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        if (StringUtils.isNotNull(pageNum) && StringUtils.isNotNull(pageSize))
        {
            String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
            Boolean reasonable = pageDomain.getReasonable();
            PageHelper.startPage(pageNum, pageSize, orderBy).setReasonable(reasonable);
        }
    }


    public static void clearPage()
    {
        PageHelper.clearPage();
    }
}
