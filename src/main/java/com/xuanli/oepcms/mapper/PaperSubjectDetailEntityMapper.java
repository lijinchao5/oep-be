package com.xuanli.oepcms.mapper;

import com.xuanli.oepcms.entity.PaperSubjectDetailEntity;

public interface PaperSubjectDetailEntityMapper {
	/**
	 * Title: deletePaperSubjectDetailEntity 
	 * Description:   删除方法
	 * @date 2018年2月6日 下午2:11:58
	 * @param id
	 * @return
	 */
    int deletePaperSubjectDetailEntity(Long id);
    /**
     * Title: insertPaperSubjectDetailEntity 
     * Description:   添加方法
     * @date 2018年2月6日 下午2:12:06
     * @param record
     * @return
     */
    int insertPaperSubjectDetailEntity(PaperSubjectDetailEntity record);
    /**
     * Title: selectById 
     * Description:   查询方法
     * @date 2018年2月6日 下午2:12:13
     * @param id
     * @return
     */
    PaperSubjectDetailEntity selectById(Long id);
    /**
     * Title: updatePaperSubjectDetailEntity 
     * Description:   更新方法
     * @date 2018年2月6日 下午2:12:19
     * @param record
     * @return
     */
    int updatePaperSubjectDetailEntity(PaperSubjectDetailEntity record);

}