package com.xuanli.oepcms.service;

import java.util.List;
import java.util.Map;

import com.xuanli.oepcms.entity.User;

public interface UserService {
	/**分页查询*/
	Map<String, Object> findByPage(String username, 
			Integer currentPage);
	
	/**保存数据*/
	void saveUser(User user,String roleIds);
	
	/**根据id查询用户*/
	Map<String,Object> findUserById(Integer userId);
	
	/**修改用户*/
	void updateUser(User user,String roleIds);
	
	/**用户状态*/
	void validById(Integer userId, Integer valid);
	
	/**查询用户权限*/
	List<String> findUserPermission(Integer userId);
	
	/**查询用户菜单*/
	List<Map<String,Object>> findUserMenu(Integer userId);
	
	/**查询角色*/
	List<Map<String, Object>> findRole();
	
}
