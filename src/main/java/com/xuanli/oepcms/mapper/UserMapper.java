package com.xuanli.oepcms.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.xuanli.oepcms.common.BaseDao;
import com.xuanli.oepcms.entity.User;

@Mapper
public interface UserMapper extends BaseDao<User>{
//    @Select("INSERT INTO xl_user (username, `desc`, `password`,`mobile`) VALUES (#{username}, #{desc}, #{password}, #{mobile})")
//    void add(User user);
    /**分页查询*/
	List<User> findByPage(
			@Param("username")String username,
			@Param("startIndex")Integer startIndex,
			@Param("pageSize") Integer pageSize);
	
	/**获取分页的行数*/
	int getRowCount(@Param("username")String username);
	
	/**根据id查询用户信息*/
	User findUserById(Integer id);
	
	/**状态*/
	int validById(@Param("id")Integer id,@Param("valid")Integer valid);
	
	/**根据用户名查询用户信息*/
	User findUserByName(String username);
	
	/**
	 * 查询用户得权限
	 * @param userId
	 */
	List<String> findUserPermissions(Integer userId);
	List<Map<String,Object>> findUserMenus(Integer userId);
	
}