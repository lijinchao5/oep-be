package com.xuanli.oepcms.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.xuanli.oepcms.entity.HomeworkEntity;
@Mapper
public interface HomeworkEntityMapper {
    int deleteByPrimaryKey(Long id);

    int insert(HomeworkEntity record);

    int insertSelective(HomeworkEntity record);

    HomeworkEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(HomeworkEntity record);

    int updateByPrimaryKey(HomeworkEntity record);
}