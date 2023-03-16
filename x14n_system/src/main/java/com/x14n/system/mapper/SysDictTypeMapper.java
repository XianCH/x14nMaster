package com.x14n.system.mapper;

import com.x14n.common.core.domain.entity.SysDictType;

import java.util.List;

public interface SysDictTypeMapper {
    List<SysDictType> selectDictTypeList(SysDictType dictType);

    SysDictType selectDictTypeById(Long dictId);

    List<SysDictType> selectDictTypeAll();
}
