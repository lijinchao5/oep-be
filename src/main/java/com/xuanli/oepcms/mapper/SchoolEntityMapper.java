package com.xuanli.oepcms.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.xuanli.oepcms.entity.SchoolEntity;
@Mapper
public interface SchoolEntityMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SchoolEntity record);

    int insertSelective(SchoolEntity record);

    SchoolEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SchoolEntity record);

    int updateByPrimaryKey(SchoolEntity record);
}