package com.x14n.system.mapper;

import java.util.List;
import com.x14n.system.domain.SysConfig;
import org.apache.ibatis.annotations.Mapper;

/**
 * 参数配置 数据层
 * 
 * @author x14n
 */


public interface SysConfigMapper
{
    /**
     * 查询参数配置信息
     * 
     * @param config 参数配置信息
     * @return 参数配置信息
     */
    public SysConfig selectConfig(SysConfig config);


    List<SysConfig> selectConfigList(SysConfig config);
}
