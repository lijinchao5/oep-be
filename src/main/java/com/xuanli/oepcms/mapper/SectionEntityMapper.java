package com.xuanli.oepcms.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.xuanli.oepcms.entity.SectionEntity;
@Mapper
public interface SectionEntityMapper {
	/**删除方法，根据id删除*/
    int deleteSectionEntity(Long id);

    /**增加方法*/
    int insertSectionEntity(SectionEntity record);
    
    /**查询方法,根据id查询*/
    SectionEntity selectById(Long id);

    /**更新方法*/
    int updateSectionEntity(SectionEntity record);
}