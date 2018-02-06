package com.xuanli.oepcms.mapper;

import com.xuanli.oepcms.entity.ExamStudentEntity;

public interface ExamStudentEntityMapper {
	/**
	 * Title: deleteExamStudentEntity 
	 * Description:   删除方法
	 * @date 2018年2月5日 上午10:47:21
	 * @param id
	 * @return
	 */
    int deleteExamStudentEntity(Long id);
    /**
     * Title: insertExamStudentEntity 
     * Description:   添加方法
     * @date 2018年2月5日 上午10:47:32
     * @param record
     * @return
     */
    int insertExamStudentEntity(ExamStudentEntity record);
    /**
     * Title: selectById 
     * Description:   添加方法
     * @date 2018年2月5日 上午10:47:40
     * @param id
     * @return
     */
    ExamStudentEntity selectById(Long id);
    /**
     * Title: updateExamStudentEntity 
     * Description:   更新方法
     * @date 2018年2月5日 上午10:47:49
     * @param record
     * @return
     */
    int updateExamStudentEntity(ExamStudentEntity record);

}