package com.x14n.system.service.impl;

import com.x14n.common.annotation.DataScope;
import com.x14n.common.constant.UserConstants;
import com.x14n.common.core.domain.TreeSelect;
import com.x14n.common.core.domain.entity.SysDept;
import com.x14n.common.core.domain.entity.SysRole;
import com.x14n.common.exception.ServiceException;
import com.x14n.common.utils.StringUtils;
import com.x14n.system.mapper.SysDeptMapper;
import com.x14n.system.mapper.SysRoleMapper;
import com.x14n.system.service.ISysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysDeptServiceImpl implements ISysDeptService {

    @Autowired
    SysDeptMapper deptMapper;

    @Autowired
    SysRoleMapper roleMapper;

    @Override
    @DataScope(deptAlias = "d")
    public List<SysDept> selectDeptList(SysDept dept) {
        return deptMapper.selectDeptList(dept);
    }

    @Override
    public List<TreeSelect> buildDeptTreeSelect(List<SysDept> depts) {
        List<SysDept> deptTrees = buildDeptTree(depts);
        return deptTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    @Override
    public List<SysDept> buildDeptTree(List<SysDept> depts) {
        List<SysDept> returnList = new ArrayList<SysDept>();
        List<Long> tempList = new ArrayList<Long>();
        for (SysDept dept : depts) {
            tempList.add(dept.getDeptId());
        }
        for (SysDept dept : depts) {
            if (!tempList.contains(dept.getParentId())) {
                recursionFn(depts, dept);
                returnList.add(dept);
            }
        }
        if (returnList.isEmpty()) {
            returnList = depts;
        }
        return returnList;
    }

    @Override
    public List<Long> selectDeptListByRoleId(Long roleId) {
        SysRole role = roleMapper.selectRoleById(roleId);
        return deptMapper.selectDeptListByRoleId(roleId, role.isDeptCheckStrictly());
    }

    @Override
    public SysDept selectDeptById(Long deptId) {
        return deptMapper.selectDeptById(deptId);
    }

    @Override
    public String checkDeptNameUnique(SysDept dept) {
        Long deptId = StringUtils.isNull(dept.getDeptId()) ? -1L : dept.getDeptId();
        SysDept info = deptMapper.checkDeptNameUnique(dept.getDeptId(), dept.getDeptName());
        if (StringUtils.isNotNull(info) && info.getDeptId().longValue() != deptId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public int insertDept(SysDept dept) {
        SysDept info = deptMapper.selectDeptById(dept.getDeptId());
        if (!UserConstants.DEPT_NORMAL.equals(info.getStatus())){
            throw new ServiceException("部门停用，不允许新增");
        }
        dept.setAncestors(info.getAncestors() + "," + dept.getParentId());
        return deptMapper.insertDept(dept);
    }


    /**
     * 递归列表
     */
    private void recursionFn(List<SysDept> list, SysDept t) {
        List<SysDept> childList = getChildList(list, t);
        t.setChildren(childList);
        for (SysDept tChild : childList) {
            if (hasChild(list, tChild)) {
                recursionFn(list, tChild);
            }
        }
    }

    private List<SysDept> getChildList(List<SysDept> list, SysDept t) {
        List<SysDept> tlist = new ArrayList<SysDept>();
        Iterator<SysDept> it = list.iterator();
        while (it.hasNext()) {
            SysDept n = (SysDept) it.next();
            if (StringUtils.isNotNull(n.getParentId()) && n.getParentId().longValue() == t.getDeptId().longValue()) {
                tlist.add(n);
            }
        }
        return tlist;
    }

    private boolean hasChild(List<SysDept> list, SysDept t) {
        return getChildList(list, t).size() > 0;
    }
}
