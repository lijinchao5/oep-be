package com.xuanli.oepcms.mapper;

import com.xuanli.oepcms.entity.PaperSubjectDetailEntity;

public interface PaperSubjectDetailEntityMapper {
	/**
	 * Title: deletePaperSubjectDetailEntity 
	 * Description:   删除题目详情方法
	 * @date 2018年2月5日 上午10:32:25
	 * @param id
	 * @return
	 */
    int deletePaperSubjectDetailEntity(Long id);
    /**
     * Title: insertPaperSubjectDetailEntity 
     * Description:  插入试卷详情方法
     * @date 2018年2月5日 上午10:32:46
     * @param record
     * @return
     */
    int insertPaperSubjectDetailEntity(PaperSubjectDetailEntity record);
    /**
     * Title: selectById 
     * Description:   查询方法
     * @date 2018年2月5日 上午10:33:13
     * @param id
     * @return
     */
    PaperSubjectDetailEntity selectById(Long id);
    /**
     * Title: updatePaperSubjectDetailEntity 
     * Description:   更新方法
     * @date 2018年2月5日 上午10:33:21
     * @param record
     * @return
     */
    int updatePaperSubjectDetailEntity(PaperSubjectDetailEntity record);

}