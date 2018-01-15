package com.xuanli.oepcms.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.xuanli.oepcms.entity.ClasEntity;
@Mapper
public interface ClasEntityMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ClasEntity record);

    int insertSelective(ClasEntity record);

    ClasEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ClasEntity record);

    int updateByPrimaryKey(ClasEntity record);
}