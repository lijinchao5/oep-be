package com.xuanli.oepcms.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.xuanli.oepcms.entity.BookEntity;
@Mapper
public interface BookEntityMapper {
	/**删除方法，根据id删除*/
    int deleteBookEntity(Long id);
    /**增加方法*/
    int insertBookEntity(BookEntity record);
    /**查询方法,根据id查询*/
    BookEntity selectById(Long id);
    /**更新方法*/
    int updateByBookEntity(BookEntity record);


}