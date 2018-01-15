package com.xuanli.oepcms.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.xuanli.oepcms.entity.RoleEntity;
@Mapper
public interface RoleEntityMapper {
    int deleteByPrimaryKey(Long roleId);

    int insert(RoleEntity record);

    int insertSelective(RoleEntity record);

    RoleEntity selectByPrimaryKey(Long roleId);

    int updateByPrimaryKeySelective(RoleEntity record);

    int updateByPrimaryKey(RoleEntity record);
}