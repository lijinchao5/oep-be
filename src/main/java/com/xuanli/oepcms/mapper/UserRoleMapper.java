package com.xuanli.oepcms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserRoleMapper {
	/**判断用户是否占用此角色*/
	@Select(" SELECT COUNT(*) " + 
			" FROM sys_user_roles " + 
			" WHERE role_id = #{roleId} ")
	int isUsedByUser(Integer roleId);
	
	/*保存用户角色关系,存在问题，需要遍历所有roleId */
//	@Insert(" INSERT into xl_user_role(user_id,role_id) " + 
//			" VALUES " + 
//			"(#{userId},#{roleId})")
	@Insert("		insert into " + 
			"			sys_user_roles(user_id,role_id) " + 
			"		values " + 
			"		<foreach collection=\"roleIds\" separator=\",\" item=\"item\"> " + 
			"			(#{userId},#{item}) " + 
			"		</foreach>")
	int insertUser(Integer userId,
			@Param("roleIds")String[] roleIds);
	
//	int deleteUserRoles(Integer userId);
	@Select("SELECT role_id " + 
			"FROM xl_user_role " + 
			"WHERE user_id = #{id}")
	List<Integer> findRoleIdsByUserId(Integer userId);
}
