package com.x14n.system.service;

import com.x14n.common.core.domain.SysDictData;

import java.util.List;

public interface ISysDictDataService {
    List<SysDictData> selectDictDataList(SysDictData dictData);

    SysDictData selectDictDataById(Long dictCode);
}
