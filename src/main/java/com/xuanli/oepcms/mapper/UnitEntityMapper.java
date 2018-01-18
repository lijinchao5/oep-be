package com.xuanli.oepcms.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.xuanli.oepcms.entity.UnitEntity;
@Mapper
public interface UnitEntityMapper {
	/**删除方法，根据id删除*/
    int deleteUnitEntity(Long id);
    
    /**增加方法*/
    int insertUnitEntity(UnitEntity record);
    
    /**查询方法,根据id查询*/
    UnitEntity selectById(Long id);
    
    /**更新方法*/
    int updateUnitEntity(UnitEntity record);
}