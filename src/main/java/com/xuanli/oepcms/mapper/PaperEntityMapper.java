package com.xuanli.oepcms.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.xuanli.oepcms.entity.PaperEntity;
@Mapper
public interface PaperEntityMapper {
	/**
	 * Title: deletePaperEntity 
	 * Description:   删除试卷方法
	 * @date 2018年2月6日 下午2:09:41
	 * @param id
	 * @return
	 */
    int deletePaperEntity(Long id);
    /**
     * Title: insertPaperEntity 
     * Description:   添加试卷方法
     * @date 2018年2月6日 下午2:09:55
     * @param record
     * @return
     */
    int insertPaperEntity(PaperEntity record);
    /**
     * Title: selectById 
     * Description:   查询方法
     * @date 2018年2月6日 下午2:10:04
     * @param id
     * @return
     */
    PaperEntity selectById(Long id);
    /**
     * Title: updatePaperEntity 
     * Description:   更新方法
     * @date 2018年2月6日 下午2:10:12
     * @param record
     * @return
     */
    int updatePaperEntity(PaperEntity record);

}