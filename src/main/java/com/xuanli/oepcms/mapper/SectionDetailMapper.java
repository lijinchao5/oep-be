package com.xuanli.oepcms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.xuanli.oepcms.entity.SectionDetail;
@Mapper
public interface SectionDetailMapper {
	/**删除方法，根据id删除*/
    int deleteSectionDetail(Long id);

    /**增加方法*/
    int insertSectionDetail(SectionDetail record);

    SectionDetail selectById(Long id);
    /**查询方法,根据id查询*/
    
    /**更新方法*/
    int updateSectionDetail(SectionDetail record);

	/**
	 * @Description:  TODO
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年1月18日 上午10:15:32
	 */
	List<SectionDetail> getSectionDetails(SectionDetail sectionDetail);
}