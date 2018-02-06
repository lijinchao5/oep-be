package com.xuanli.oepcms.mapper;

import com.xuanli.oepcms.entity.ExamSubjectEntity;

public interface ExamSubjectEntityMapper {
	/**
	 * Title: deleteExamSubjectEntity 
	 * Description:   删除方法
	 * @date 2018年2月5日 上午10:49:49
	 * @param id
	 * @return
	 */
    int deleteExamSubjectEntity(Long id);
    /**
     * Title: insertExamSubjectEntity 
     * Description:   添加方法
     * @date 2018年2月5日 上午10:49:59
     * @param record
     * @return
     */
    int insertExamSubjectEntity(ExamSubjectEntity record);
    /**
     * Title: selectById 
     * Description:   查询方法
     * @date 2018年2月5日 上午10:50:07
     * @param id
     * @return
     */
    ExamSubjectEntity selectById(Long id);
    /**
     * Title: updateExamSubjectEntity 
     * Description:   更新方法
     * @date 2018年2月5日 上午10:50:14
     * @param record
     * @return
     */
    int updateExamSubjectEntity(ExamSubjectEntity record);

}