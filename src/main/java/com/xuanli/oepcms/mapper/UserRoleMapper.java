package com.xuanli.oepcms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserRoleMapper {
	
	int isUsedByUser(@Param("roleId")Integer roleId);
	
	int insertRole(@Param("userId")Integer userId,
			@Param("roleIds")String[] roleIds);
	
	int deleteUserRoles(Integer userId);
	
	List<Integer> findRoleIdsByUserId(Integer userId);
}
