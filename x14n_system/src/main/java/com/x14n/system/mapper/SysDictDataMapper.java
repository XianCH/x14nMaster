package com.x14n.system.mapper;

import com.x14n.common.core.domain.SysDictData;

import java.util.List;

public interface SysDictDataMapper {
    List<SysDictData> selectDictDataList(SysDictData dictData);

    SysDictData selectDictDataById(Long dictCode);

    List<SysDictData> selectDictDataByType(String dictType);
}
