package com.xuanli.oepcms.mapper;

import com.xuanli.oepcms.entity.PaperEntity;

public interface PaperEntityMapper {
	/**
	 * Title: deletePaperEntity 
	 * Description:   删除试卷方法
	 * @date 2018年2月5日 上午10:27:21
	 * @param id
	 * @return
	 */
    int deletePaperEntity(Long id);
    /**
     * Title: insertPaperEntity 
     * Description:   生成试卷方法
     * @date 2018年2月5日 上午10:27:48
     * @param record
     * @return
     */
    int insertPaperEntity(PaperEntity record);
    /**
     * Title: selectById 
     * Description:   查询方法
     * @date 2018年2月5日 上午10:28:12
     * @param id
     * @return
     */
    PaperEntity selectById(Long id);
    /**
     * Title: updatePaperEntity 
     * Description:   更新方法
     * @date 2018年2月5日 上午10:28:22
     * @param record
     * @return
     */
    int updatePaperEntity(PaperEntity record);

}