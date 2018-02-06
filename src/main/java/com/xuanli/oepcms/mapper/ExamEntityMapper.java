package com.xuanli.oepcms.mapper;

import com.xuanli.oepcms.entity.ExamEntity;

public interface ExamEntityMapper {
	/**
	 * Title: deleteExamEntity 
	 * Description:   删除考试方法
	 * @date 2018年2月5日 上午10:46:14
	 * @param id
	 * @return
	 */
    int deleteExamEntity(Long id);
    /**
     * Title: insertExamEntity 
     * Description:   添加方法
     * @date 2018年2月5日 上午10:46:29
     * @param record
     * @return
     */
    int insertExamEntity(ExamEntity record);
    /**
     * Title: selectById 
     * Description:   查询方法
     * @date 2018年2月5日 上午10:46:40
     * @param id
     * @return
     */
    ExamEntity selectById(Long id);
    /**
     * Title: updateExamEntity 
     * Description:   更新方法
     * @date 2018年2月5日 上午10:46:47
     * @param record
     * @return
     */
    int updateExamEntity(ExamEntity record);

}