package com.xuanli.oepcms.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.xuanli.oepcms.entity.HomeworkStudentScoreEntity;
@Mapper
public interface HomeworkStudentScoreEntityMapper {
    int deleteHomeworkStudentScoreEntity(Long id);

    int insertHomeworkStudentScoreEntity(HomeworkStudentScoreEntity record);

    HomeworkStudentScoreEntity selectById(Long id);

    int updateHomeworkStudentScoreEntity(HomeworkStudentScoreEntity record);
}