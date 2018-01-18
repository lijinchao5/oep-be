package com.xuanli.oepcms.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.xuanli.oepcms.entity.HomeworkDetailEntity;
@Mapper
public interface HomeworkDetailEntityMapper {
    int deleteByPrimaryKey(Long id);

    int insert(HomeworkDetailEntity record);

    int insertSelective(HomeworkDetailEntity record);

    HomeworkDetailEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(HomeworkDetailEntity record);

    int updateByPrimaryKey(HomeworkDetailEntity record);
}