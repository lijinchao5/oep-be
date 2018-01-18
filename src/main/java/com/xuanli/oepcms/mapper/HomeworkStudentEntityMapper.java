package com.xuanli.oepcms.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.xuanli.oepcms.entity.HomeworkStudentEntity;
@Mapper
public interface HomeworkStudentEntityMapper {
	/**删除方法，根据id删除*/
    int deleteHomeworkStudentEntity(Long id);
    /**增加方法*/
    int insertHomeworkStudentEntity(HomeworkStudentEntity record);
    /**查询方法,根据id查询*/
    HomeworkStudentEntity selectById(Long id);
    /**更新方法*/
    int updateHomeworkStudentEntity(HomeworkStudentEntity record);

}