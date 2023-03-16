package com.x14n.web.controller.system;

import com.x14n.common.constant.UserConstants;
import com.x14n.common.core.controller.BaseController;
import com.x14n.common.core.domain.AjaxResult;
import com.x14n.common.core.domain.entity.SysDept;
import com.x14n.system.service.ISysDeptService;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/system/dept")
public class SysDeptController extends BaseController {

    @Autowired
    ISysDeptService deptService;


    @GetMapping("/list")
    public AjaxResult list(SysDept dept) {
        List<SysDept> depts = deptService.selectDeptList(dept);
        return AjaxResult.success(depts);
    }

    @GetMapping("/{deptId}")
    public AjaxResult getInfo(@PathVariable Long deptId) {
        return AjaxResult.success(deptService.selectDeptById(deptId));
    }


    @GetMapping("/treeselect")
    public AjaxResult treeSelect(SysDept dept) {
        List<SysDept> depts = deptService.selectDeptList(dept);
        return AjaxResult.success(deptService.buildDeptTreeSelect(depts));
    }

    @GetMapping(value = "/roleDeptTreeselect/{roleId}")
    public AjaxResult roleDeptTreeselect(@PathVariable("roleId") Long roleId) {
        List<SysDept> depts = deptService.selectDeptList(new SysDept());
        AjaxResult ajax = AjaxResult.success();
        ajax.put("checkedKeys", deptService.selectDeptListByRoleId(roleId));
        ajax.put("depts", deptService.buildDeptTreeSelect(depts));
        return ajax;
    }

    @PutMapping
    public AjaxResult add(SysDept dept) {
        if (UserConstants.NOT_UNIQUE.equals(deptService.checkDeptNameUnique(dept))) {
            return AjaxResult.error("新增部门'" + dept.getDeptName() + "'失败，部门名称已存在");
        }
        dept.setCreateBy(getUsername());
        return toAjax(deptService.insertDept(dept));
    }

}
