package com.xuanli.oepcms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.xuanli.oepcms.entity.ExamStudentScoreWordEntity;
@Mapper
public interface ExamStudentScoreWordEntityMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ExamStudentScoreWordEntity record);

    int insertSelective(ExamStudentScoreWordEntity record);

    ExamStudentScoreWordEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ExamStudentScoreWordEntity record);

    int updateByPrimaryKey(ExamStudentScoreWordEntity record);

	/**
	 * @Description:  TODO
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年2月24日 上午11:49:27
	 */
	List<ExamStudentScoreWordEntity> getExamStudentWords(ExamStudentScoreWordEntity examStudentScoreWordEntity);
}