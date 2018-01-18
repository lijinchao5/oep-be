package com.xuanli.oepcms.mapper;

import com.xuanli.oepcms.entity.SectionDetail;

public interface SectionDetailMapper {
	/**删除方法，根据id删除*/
    int deleteSectionDetail(Long id);

    /**增加方法*/
    int insertSectionDetail(SectionDetail record);

    SectionDetail selectById(Long id);
    /**查询方法,根据id查询*/
    
    /**更新方法*/
    int updateSectionDetail(SectionDetail record);
}