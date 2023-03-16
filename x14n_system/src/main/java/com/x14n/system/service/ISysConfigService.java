package com.x14n.system.service;

import com.x14n.system.domain.SysConfig;
import org.springframework.stereotype.Service;

import java.util.List;




public interface
ISysConfigService
{

    public SysConfig selectConfigById(Long configId);


    public String selectConfigByKey(String configKey);


    public boolean selectCaptchaOnOff();


    public List<SysConfig> selectConfigList(SysConfig config);


    public void loadingConfigCache();


    public String checkConfigKeyUnique(SysConfig config);
}
