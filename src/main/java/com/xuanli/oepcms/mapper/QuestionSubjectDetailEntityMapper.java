package com.xuanli.oepcms.mapper;

import com.xuanli.oepcms.entity.QuestionSubjectDetailEntity;

public interface QuestionSubjectDetailEntityMapper {
	/**
	 * Title: deleteQuestionSubjectDetailEntity 
	 * Description:  删除试题库小题
	 * @date 2018年3月15日 上午10:14:48
	 * @param id
	 * @return
	 */
    int deleteQuestionSubjectDetailEntity(Long id);
    /**
     * Title: insertQuestionSubjectDetailEntity 
     * Description:  存入试题库小题
     * @date 2018年3月15日 上午10:14:50
     * @param record
     * @return
     */
    int insertQuestionSubjectDetailEntity(QuestionSubjectDetailEntity record);
    /**
     * Title: selectById 
     * Description:  查询试题库小题
     * @date 2018年3月15日 上午10:14:52
     * @param id
     * @return
     */
    QuestionSubjectDetailEntity selectById(Long id);
    /**
     * Title: updateByPrimaryKey 
     * Description:  更新试题库小题
     * @date 2018年3月15日 上午10:14:54
     * @param record
     * @return
     */
    int updateQuestionSubjectDetailEntity(QuestionSubjectDetailEntity record);
}