package com.xuanli.oepcms.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.xuanli.oepcms.common.BaseDao;
import com.xuanli.oepcms.entity.User;

@Mapper
public interface UserMapper extends BaseDao<User>{

	

	/**添加用户*/
	void add(User user);

	/**根据id查询用户*/
	User findById(Integer id);

	/**根据用户名查询用户*/
	User findByName(String username);
	    
	/**查询所有用户列表*/
	List<User> find();
	
	/**状态*/
	int validById(@Param("id")Integer id,@Param("valid")Integer valid);
	
	
	/**
	 * 查询用户得权限
	 * @param userId
	 */
	List<String> findUserPermissions(Integer userId);
	List<Map<String,Object>> findUserMenus(Integer userId);
	
}