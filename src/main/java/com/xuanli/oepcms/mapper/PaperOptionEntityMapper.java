package com.xuanli.oepcms.mapper;

import com.xuanli.oepcms.entity.PaperOptionEntity;

public interface PaperOptionEntityMapper {
	/**
	 * 
	 * Title: deletePaperOptionEntity 
	 * Description:   删除方法
	 * @date 2018年2月2日 上午11:48:33
	 * @param id
	 * @return
	 */
    int deletePaperOptionEntity(Long id);
    /**
     * 
     * Title: insertPaperOptionEntity 
     * Description:   增加方法
     * @date 2018年2月2日 上午11:48:44
     * @param record
     * @return
     */
    int insertPaperOptionEntity(PaperOptionEntity record);
    /**
     * 
     * Title: selectByPrimaryKey 
     * Description:   查询方法
     * @date 2018年2月2日 上午11:49:01
     * @param id
     * @return
     */
    PaperOptionEntity selectById(Long id);
    /**
     * 更新方法
     * Title: updatePaperOptionEntity 
     * Description:   
     * @date 2018年2月2日 上午11:49:08
     * @param record
     * @return
     */
    int updatePaperOptionEntity(PaperOptionEntity record);

}