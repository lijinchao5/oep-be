package com.xuanli.oepcms.mapper;

import com.xuanli.oepcms.entity.PaperOptionEntity;

public interface PaperOptionEntityMapper {
	/**
	 * Title: deletePaperOptionEntity 
	 * Description:   删除方法
	 * @date 2018年2月6日 下午2:10:38
	 * @param id
	 * @return
	 */
    int deletePaperOptionEntity(Long id);
    /**
     * Title: insertPaperOptionEntity 
     * Description:   添加方法
     * @date 2018年2月6日 下午2:11:11
     * @param record
     * @return
     */
    int insertPaperOptionEntity(PaperOptionEntity record);
    /**
     * Title: selectById 
     * Description:   查询方法
     * @date 2018年2月6日 下午2:11:24
     * @param id
     * @return
     */
    PaperOptionEntity selectById(Long id);
    /**
     * Title: updatePaperOptionEntity 
     * Description:   更新方法
     * @date 2018年2月6日 下午2:11:30
     * @param record
     * @return
     */
    int updatePaperOptionEntity(PaperOptionEntity record);

}