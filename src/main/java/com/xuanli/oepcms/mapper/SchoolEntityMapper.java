package com.xuanli.oepcms.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.xuanli.oepcms.entity.SchoolEntity;
@Mapper
public interface SchoolEntityMapper {
    /**
	 * @Description:  TODO 删除方法 只能按照id删除
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年1月15日 下午2:08:38
	 */
    int deleteSchoolEntity(Long id);
    /**
     * @Description:  TODO 增加方法
     * @CreateName:  QiaoYu 
     * @CreateDate:  2018年1月15日 下午2:08:48
     */
    int insertSchoolEntity(SchoolEntity record);
    /**
     * @Description:  TODO 按照id查询
     * @CreateName:  QiaoYu 
     * @CreateDate:  2018年1月15日 下午2:09:18
     */
    SchoolEntity selectById(Long id);
    /**
     * @Description:  TODO 按照id 更新
     * @CreateName:  QiaoYu 
     * @CreateDate:  2018年1月15日 下午2:09:28
     */
    int updateSchoolEntity(SchoolEntity record);
}