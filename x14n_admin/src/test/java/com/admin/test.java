package com.admin;

import com.x14n.X14nApplication;
import com.x14n.common.core.domain.entity.SysUser;
import com.x14n.system.domain.SysConfig;
import com.x14n.system.mapper.SysUserMapper;
import com.x14n.system.service.ISysConfigService;
import com.x14n.system.service.ISysUserService;
import com.x14n.system.service.impl.SysUserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = X14nApplication.class)
public class test {

    @Autowired
    ISysConfigService sysConfigService;

    @Autowired
    SysUserServiceImpl userService;

    @Test
    public void ISysConfigServiceTest(){
        boolean b = sysConfigService.selectCaptchaOnOff();
        System.out.println(b);

    }

    @Test
    public void selectUserByName(){

        String username = "admin";
        SysUser user = userService.selectUserByUserName(username);
        System.out.println(user);
    }
}
