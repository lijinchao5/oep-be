package com.xuanli.oepcms.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.xuanli.oepcms.entity.ExamStudentEntity;
@Mapper
public interface ExamStudentEntityMapper {
	/**
	 * Title: deleteExamStudentEntity 
	 * Description:   删除方法
	 * @date 2018年2月6日 下午2:01:34
	 * @param id
	 * @return
	 */
    int deleteExamStudentEntity(Long id);
    /**
     * Title: insertExamStudentEntity 
     * Description:   添加方法
     * @date 2018年2月6日 下午2:01:44
     * @param record
     * @return
     */
    int insertExamStudentEntity(ExamStudentEntity record);
    /**
     * Title: selectById 
     * Description:   查询方法
     * @date 2018年2月6日 下午2:01:53
     * @param id
     * @return
     */
    ExamStudentEntity selectById(Long id);
    /**
     * Title: updateExamStudentEntity 
     * Description:   更新方法
     * @date 2018年2月6日 下午2:02:00
     * @param record
     * @return
     */
    int updateExamStudentEntity(ExamStudentEntity record);

}