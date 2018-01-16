package com.xuanli.oepcms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.xuanli.oepcms.entity.ClasEntity;
@Mapper
public interface ClasEntityMapper {
    int deleteById(Long id);

    int insertClasEntity(ClasEntity record);

    ClasEntity selectById(Long id);

    int updateById(ClasEntity record);

	/**
	 * @Description:  TODO
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年1月16日 上午9:37:23
	 */
	List<ClasEntity> selectClasEntity(ClasEntity clasEntity);
    
}