package com.xuanli.oepcms.mapper;

import com.xuanli.oepcms.entity.PaperEntity;

public interface PaperEntityMapper {
	/**
	 * 
	 * Title: deletePaperEntity 
	 * Description:   删除试卷方法
	 * @date 2018年2月2日 上午11:46:59
	 * @param id
	 * @return
	 */
    int deletePaperEntity(Long id);
    /**
     * 
     * Title: insertPaperEntity 
     * Description:   增加试卷方法
     * @date 2018年2月2日 上午11:47:28
     * @param record
     * @return
     */
    int insertPaperEntity(PaperEntity record);
    /**
     * 
     * Title: selectByID 
     * Description:   查询试卷方法
     * @date 2018年2月2日 上午11:47:39
     * @param id
     * @return
     */
    PaperEntity selectByID(Long id);
    /**
     * 
     * Title: updatePaperEntity 
     * Description:   更新试卷方法
     * @date 2018年2月2日 上午11:47:54
     * @param record
     * @return
     */
    int updatePaperEntity(PaperEntity record);

}