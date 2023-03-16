package com.x14n.system.mapper;


import com.x14n.common.core.domain.entity.SysDept;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;


public interface SysDeptMapper {
    List<SysDept> selectDeptList(SysDept dept);

    List<Long> selectDeptListByRoleId(@Param("roleId") Long roleId,@Param("deptCheckStrictly") boolean deptCheckStrictly);

    SysDept selectDeptById(Long deptId);

    SysDept checkDeptNameUnique(@Param("deptName") Long deptId,@Param("parentId") String deptName);

    int insertDept(SysDept dept);
}
