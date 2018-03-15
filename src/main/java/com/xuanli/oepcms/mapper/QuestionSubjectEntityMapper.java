package com.xuanli.oepcms.mapper;

import com.xuanli.oepcms.entity.QuestionSubjectEntity;

public interface QuestionSubjectEntityMapper {
	/**
	 * Title: deleteQuestionSubjectEntity 
	 * Description:  删除试题库大题
	 * @date 2018年3月15日 上午10:16:31
	 * @param id
	 * @return
	 */
    int deleteQuestionSubjectEntity(Long id);
    /**
     * Title: insertQuestionSubjectEntity 
     * Description:  存入试题库大题
     * @date 2018年3月15日 上午10:16:33
     * @param record
     * @return
     */
    int insertQuestionSubjectEntity(QuestionSubjectEntity record);
    /**
     * Title: selectById 
     * Description:  查询试题库大题
     * @date 2018年3月15日 上午10:16:35
     * @param id
     * @return
     */
    QuestionSubjectEntity selectById(Long id);
    /**
     * Title: updateQuestionSubjectEntity 
     * Description:  更新试题库大题
     * @date 2018年3月15日 上午10:16:37
     * @param record
     * @return
     */
    int updateQuestionSubjectEntity(QuestionSubjectEntity record);

}