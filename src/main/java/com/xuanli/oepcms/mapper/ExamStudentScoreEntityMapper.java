package com.xuanli.oepcms.mapper;

import com.xuanli.oepcms.entity.ExamStudentScoreEntity;

public interface ExamStudentScoreEntityMapper {
	/**
	 * Title: deleteExamStudentScoreEntity 
	 * Description:   删除方法
	 * @date 2018年2月6日 下午2:02:38
	 * @param id
	 * @return
	 */
    int deleteExamStudentScoreEntity(Long id);
    /**
     * Title: insertExamStudentScoreEntity 
     * Description:   添加方法
     * @date 2018年2月6日 下午2:02:53
     * @param record
     * @return
     */
    int insertExamStudentScoreEntity(ExamStudentScoreEntity record);
    /**
     * Title: selectById 
     * Description:   查询方法
     * @date 2018年2月6日 下午2:03:03
     * @param id
     * @return
     */
    ExamStudentScoreEntity selectById(Long id);
    /**
     * Title: updateExamStudentScoreEntity 
     * Description:   更新方法
     * @date 2018年2月6日 下午2:03:10
     * @param record
     * @return
     */
    int updateExamStudentScoreEntity(ExamStudentScoreEntity record);
}