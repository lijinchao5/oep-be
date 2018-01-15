package com.xuanli.oepcms.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.xuanli.oepcms.entity.SchoolEntity;
@Mapper
public interface SchoolEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SchoolEntity record);

    int insertSelective(SchoolEntity record);

    SchoolEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SchoolEntity record);

    int updateByPrimaryKey(SchoolEntity record);
}