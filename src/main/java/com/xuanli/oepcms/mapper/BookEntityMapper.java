package com.xuanli.oepcms.mapper;

import java.util.List;

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
	/**
	 * @Description:  TODO 获取书籍信息
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年1月18日 上午9:36:17
	 */
	List<BookEntity> getBookEntity(BookEntity bookEntity);


}