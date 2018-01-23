package com.xuanli.oepcms.mapper;

import com.xuanli.oepcms.entity.HomeworkStudentScoreSymbolEntity;

public interface HomeworkStudentScoreSymbolEntityMapper {
	/**学生作业单词跟读删除方法*/
    int deleteHomeworkStudentScoreSymbolEntity(Long id);
    /**保存方法*/
    int insertHomeworkStudentScoreSymbolEntity(HomeworkStudentScoreSymbolEntity record);
    /**查询方法*/
    HomeworkStudentScoreSymbolEntity selectById(Long id);
    /**更新方法*/
    int updateHomeworkStudentScoreSymbolEntity(HomeworkStudentScoreSymbolEntity record);

}