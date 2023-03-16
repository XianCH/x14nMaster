package com.x14n.system.service.impl;

import com.x14n.common.core.domain.SysDictData;
import com.x14n.common.core.domain.entity.SysDictType;
import com.x14n.common.utils.DictUtils;
import com.x14n.common.utils.StringUtils;
import com.x14n.system.mapper.SysDictDataMapper;
import com.x14n.system.mapper.SysDictTypeMapper;
import com.x14n.system.service.ISysDictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class SysDictTypeServiceImpl implements ISysDictTypeService {

    @Autowired
    private SysDictDataMapper dictDataMapper;

    @Autowired
    private SysDictTypeMapper dictTypeMapper;

    @PostConstruct
    public void init(){
        loadingDictCache();
    }

    @Override
    public List<SysDictData> selectDictDataByType(String dictType) {
        {
            List<SysDictData> dictDatas = DictUtils.getDictCache(dictType);
            if (StringUtils.isNotEmpty(dictDatas))
            {
                return dictDatas;
            }
            dictDatas = dictDataMapper.selectDictDataByType(dictType);
            if (StringUtils.isNotEmpty(dictDatas))
            {
                DictUtils.setDictCache(dictType, dictDatas);
                return dictDatas;
            }
            return null;
        }
    }

    @Override
    public List<SysDictType> selectDictTypeList(SysDictType dictType) {
        return dictTypeMapper.selectDictTypeList(dictType);
    }

    @Override
    public SysDictType selectDictTypeById(Long dictId) {
        return dictTypeMapper.selectDictTypeById(dictId);
    }

    @Override
    public List<SysDictType> selectDictTypeAll() {
        return dictTypeMapper.selectDictTypeAll();
    }

    @Override
    public void loadingDictCache() {
        SysDictData dictData = new SysDictData();
    }
}
