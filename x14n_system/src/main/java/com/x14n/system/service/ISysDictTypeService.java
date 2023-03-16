package com.x14n.system.service;

import com.x14n.common.core.domain.SysDictData;
import com.x14n.common.core.domain.entity.SysDictType;

import java.util.List;

public interface ISysDictTypeService {
    List<SysDictData> selectDictDataByType(String dictType);

    List<SysDictType> selectDictTypeList(SysDictType dictType);

    SysDictType selectDictTypeById(Long dictId);

    List<SysDictType> selectDictTypeAll();

    void loadingDictCache();

}
