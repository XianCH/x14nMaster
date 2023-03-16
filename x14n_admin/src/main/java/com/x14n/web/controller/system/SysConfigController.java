package com.x14n.web.controller.system;

import com.x14n.common.core.controller.BaseController;
import com.x14n.common.core.page.TableDataInfo;
import com.x14n.system.domain.SysConfig;
import com.x14n.system.service.ISysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/system/config")
public class SysConfigController extends BaseController {

    @Autowired
    ISysConfigService configService;

    @GetMapping("/list")
    public TableDataInfo list(SysConfig config){
        startPage();
        List<SysConfig> list = configService.selectConfigList(config);
        return getDataTable(list);
    }
}
