package com.xuanli.oepcms.mapper;

import com.xuanli.oepcms.entity.PaperSubjectEntity;

public interface PaperSubjectEntityMapper {
	/**
	 * Title: deletePaperSubjectEntity 
	 * Description:   删除试卷题方法
	 * @date 2018年2月5日 上午10:34:01
	 * @param id
	 * @return
	 */
    int deletePaperSubjectEntity(Long id);
    /**
     * Title: insertPaperSubjectEntity 
     * Description:   插入方法
     * @date 2018年2月5日 上午10:34:20
     * @param record
     * @return
     */
    int insertPaperSubjectEntity(PaperSubjectEntity record);
    /**
     * Title: selectById 
     * Description:   查询方法
     * @date 2018年2月5日 上午10:34:30
     * @param id
     * @return
     */
    PaperSubjectEntity selectById(Long id);
    /**
     * 更新方法
     * Title: updatePaperSubjectEntity 
     * Description:   
     * @date 2018年2月5日 上午10:34:45
     * @param record
     * @return
     */
    int updatePaperSubjectEntity(PaperSubjectEntity record);

}