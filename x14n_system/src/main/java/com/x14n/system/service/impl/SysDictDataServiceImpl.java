package com.x14n.system.service.impl;

import com.x14n.common.core.domain.SysDictData;
import com.x14n.system.mapper.SysDictDataMapper;
import com.x14n.system.service.ISysDictDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysDictDataServiceImpl implements ISysDictDataService {

    @Autowired
    private SysDictDataMapper dictDataMapper;

    @Override
    public List<SysDictData> selectDictDataList(SysDictData dictData) {
        return dictDataMapper.selectDictDataList(dictData);
    }

    @Override
    public SysDictData selectDictDataById(Long dictCode) {
        return dictDataMapper.selectDictDataById(dictCode);
    }
}
