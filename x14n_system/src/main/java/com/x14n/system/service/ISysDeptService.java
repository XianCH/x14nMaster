package com.x14n.system.service;

import com.x14n.common.core.domain.TreeSelect;
import com.x14n.common.core.domain.entity.SysDept;

import java.util.List;

public interface ISysDeptService {

    List<SysDept> selectDeptList(SysDept dept);

    List<TreeSelect> buildDeptTreeSelect(List<SysDept> depts);

    public List<SysDept> buildDeptTree(List<SysDept> depts);

    List<Long> selectDeptListByRoleId(Long roleId);

    SysDept selectDeptById(Long deptId);

    String checkDeptNameUnique(SysDept dept);

    int insertDept(SysDept dept);
}
