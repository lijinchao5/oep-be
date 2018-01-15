package com.xuanli.oepcms.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.xuanli.oepcms.entity.MenuEntity;
@Mapper
public interface MenuEntityMapper {
    int deleteById(Integer id);

    int insertMenuEntity(MenuEntity record);

    MenuEntity selectById(Integer id);

    int updateById(MenuEntity record);

}