package com.xuanli.oepcms.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.xuanli.oepcms.entity.ClasEntity;
@Mapper
public interface ClasEntityMapper {
    int deleteById(Long id);

    int insertClasEntity(ClasEntity record);

    ClasEntity selectById(Long id);

    int updateById(ClasEntity record);
    
}