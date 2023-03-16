package com.x14n.system.service.impl;

import com.x14n.common.constant.Constants;
import com.x14n.common.core.redis.RedisCache;
import com.x14n.common.core.text.Convert;
import com.x14n.common.utils.StringUtils;
import com.x14n.system.domain.SysConfig;
import com.x14n.system.mapper.SysConfigMapper;
import com.x14n.system.service.ISysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;


@Service
public class SysConfigServiceImpl implements ISysConfigService {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private SysConfigMapper configMapper;


    @PostConstruct
    public void init(){
        loadingConfigCache();
    }

    @Override
    public SysConfig selectConfigById(Long configId) {
        return null;
    }

    @Override
    public String selectConfigByKey(String configKey) {
        String configValue = Convert.toStr(redisCache.getCacheObject(getCacheKey(configKey)));
        if (StringUtils.isNotEmpty(configValue)){
            return configValue;
        }
        SysConfig sysConfig = new SysConfig();
        sysConfig.setConfigKey(configKey);
        SysConfig retConfig = configMapper.selectConfig(sysConfig);
        if (StringUtils.isNotNull(retConfig)){
            redisCache.setCacheObject(getCacheKey(configKey),retConfig.getConfigValue());
            return retConfig.getConfigValue();
        }
        return StringUtils.EMPTY;
    }

    @Override
    public boolean selectCaptchaOnOff() {
        String captchaOnOff = selectConfigByKey("sys.account.captchaOnOff");
        if (StringUtils.isEmpty(captchaOnOff)){
            return true;
        }
        return Convert.toBool(captchaOnOff);
    }

    @Override
    public List<SysConfig> selectConfigList(SysConfig config) {
        return null;
    }



    @Override
    public void loadingConfigCache() {
        List<SysConfig> configList = configMapper.selectConfigList(new SysConfig());
        for (SysConfig config : configList) {
            redisCache.setCacheObject(getCacheKey(config.getConfigKey()),config.getConfigValue());
        }
    }



    @Override
    public String checkConfigKeyUnique(SysConfig config) {
        return null;
    }

    private String getCacheKey(String configKey) {
        return Constants.SYS_CONFIG_KEY + configKey;
    }
}
