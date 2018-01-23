package com.xuanli.oepcms.mapper;

import com.xuanli.oepcms.entity.HomeworkStudentScoreSymbolEntity;

public interface HomeworkStudentScoreSymbolEntityMapper {
    int deleteByPrimaryKey(Long id);

    int insert(HomeworkStudentScoreSymbolEntity record);

    int insertSelective(HomeworkStudentScoreSymbolEntity record);

    HomeworkStudentScoreSymbolEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(HomeworkStudentScoreSymbolEntity record);

    int updateByPrimaryKey(HomeworkStudentScoreSymbolEntity record);
}