package com.x14n.web.controller.system;

import com.x14n.common.core.controller.BaseController;
import com.x14n.common.core.domain.AjaxResult;
import com.x14n.common.core.domain.entity.SysDictType;
import com.x14n.common.core.page.TableDataInfo;
import com.x14n.system.service.ISysDictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/system/dict/type")
public class SysDictTypeController extends BaseController {

    @Autowired
    private ISysDictTypeService dictTypeService;

    @GetMapping("/list")
    public TableDataInfo list(SysDictType dictType) {
        startPage();
        List<SysDictType> list = dictTypeService.selectDictTypeList(dictType);
        return getDataTable(list);
    }

    @GetMapping(value = "/{dictId}")
    public AjaxResult getInfo(@PathVariable Long dictId) {
        return AjaxResult.success(dictTypeService.selectDictTypeById(dictId));
    }

    @GetMapping("/optionselect")
    public AjaxResult optionselect() {
        List<SysDictType> dictTypes = dictTypeService.selectDictTypeAll();
        return AjaxResult.success(dictTypes);
    }


}
