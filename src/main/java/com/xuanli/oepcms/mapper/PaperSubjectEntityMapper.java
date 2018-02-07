package com.xuanli.oepcms.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.xuanli.oepcms.entity.PaperSubjectEntity;
@Mapper
public interface PaperSubjectEntityMapper {
	/**
	 * Title: deletePaperSubjectEntity 
	 * Description:   删除方法
	 * @date 2018年2月6日 下午2:12:44
	 * @param id
	 * @return
	 */
    int deletePaperSubjectEntity(Long id);
    /**
     * Title: insertPaperSubjectEntity 
     * Description:   添加方法
     * @date 2018年2月6日 下午2:12:54
     * @param record
     * @return
     */
    int insertPaperSubjectEntity(PaperSubjectEntity record);
    /**
     * Title: selectById 
     * Description:   查询方法
     * @date 2018年2月6日 下午2:13:06
     * @param id
     * @return
     */
    PaperSubjectEntity selectById(Long id);
    /**
     * Title: updatePaperSubjectEntity 
     * Description:   更新方法
     * @date 2018年2月6日 下午2:13:13
     * @param record
     * @return
     */
    int updatePaperSubjectEntity(PaperSubjectEntity record);

}