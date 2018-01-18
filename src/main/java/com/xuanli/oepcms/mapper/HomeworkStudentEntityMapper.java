package com.xuanli.oepcms.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.xuanli.oepcms.entity.HomeworkStudentEntity;
@Mapper
public interface HomeworkStudentEntityMapper {
    int deleteByPrimaryKey(Long id);

    int insert(HomeworkStudentEntity record);

    int insertSelective(HomeworkStudentEntity record);

    HomeworkStudentEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(HomeworkStudentEntity record);

    int updateByPrimaryKey(HomeworkStudentEntity record);
}