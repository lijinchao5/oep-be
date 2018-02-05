package com.xuanli.oepcms.mapper;

import com.xuanli.oepcms.entity.PaperOptionEntity;

public interface PaperOptionEntityMapper {
	/**
	 * Title: deletePaperEntity 
	 * Description:   删除试卷对应选择/填空方法
	 * @date 2018年2月5日 上午10:27:21
	 * @param id
	 * @return
	 */
    int deletePaperOptionEntity(Long id);
    /**
     * Title: insertPaperEntity 
     * Description:   生成方法
     * @date 2018年2月5日 上午10:27:48
     * @param record
     * @return
     */
    int insertPaperOptionEntity(PaperOptionEntity record);
    /**
     * Title: selectById 
     * Description:   查询方法
     * @date 2018年2月5日 上午10:28:12
     * @param id
     * @return
     */
    PaperOptionEntity selectById(Long id);
    /**
     * Title: updatePaperEntity 
     * Description:   更新方法
     * @date 2018年2月5日 上午10:28:22
     * @param record
     * @return
     */
    int updatePaperOptionEntity(PaperOptionEntity record);

}