package com.xuanli.oepcms.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.xuanli.oepcms.entity.ExamStudentScoreEntity;
@Mapper
public interface ExamStudentScoreEntityMapper {
	/**
	 * Title: deleteExamStudentScoreEntity 
	 * Description:   删除方法
	 * @date 2018年2月6日 下午4:03:42
	 * @param id
	 * @return
	 */
    int deleteExamStudentScoreEntity(Long id);
    /**
     * Title: insertExamStudentScoreEntity 
     * Description:   添加方法
     * @date 2018年2月6日 下午4:03:51
     * @param record
     * @return
     */
    int insertExamStudentScoreEntity(ExamStudentScoreEntity record);
    /**
     * Title: selectById 
     * Description:   查询方法
     * @date 2018年2月6日 下午4:04:00
     * @param id
     * @return
     */
    ExamStudentScoreEntity selectById(Long id);
    /**
     * Title: updateExamStudentScoreEntity 
     * Description:   更新方法
     * @date 2018年2月6日 下午4:04:08
     * @param record
     * @return
     */
    int updateExamStudentScoreEntity(ExamStudentScoreEntity record);

}