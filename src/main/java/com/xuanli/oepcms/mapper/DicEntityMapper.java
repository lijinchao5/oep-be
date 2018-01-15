package com.xuanli.oepcms.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.xuanli.oepcms.entity.DicEntity;
@Mapper
public interface DicEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DicEntity record);

    int insertSelective(DicEntity record);

    DicEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DicEntity record);

    int updateByPrimaryKey(DicEntity record);
}