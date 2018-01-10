package com.xuanli.oepcms.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xuanli.oepcms.common.Page;
import com.xuanli.oepcms.entity.User;
import com.xuanli.oepcms.exception.ServiceException;
import com.xuanli.oepcms.mapper.UserMapper;
import com.xuanli.oepcms.mapper.UserRoleMapper;
import com.xuanli.oepcms.service.UserService;

@Service()
@Transactional
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserMapper userMapper;
//	@Autowired
//	private RoleMapper roleMapper;
	@Autowired
	private UserRoleMapper userRoleMapper;
	
	@Override
	public Map<String, Object> findByPage(String username, 
			Integer pageCurrent) {
		Page page=new Page();
		page.setRowCount(userMapper.getRowCount(username));	
		page.setPageCurrent(pageCurrent);
	    int pageSize=2;
	    int startIndex=(pageCurrent-1)*pageSize;
	    page.setPageSize(2);
	    page.setStartIndex(startIndex);
	    
		List<User> list = 
		userMapper.findByPage(username,startIndex,pageSize);
		
		Map<String,Object> map=new HashMap<>();
		map.put("list", list);
		map.put("page",page);
		return map;
	}

	@Override
	public void saveUser(User user, String roleIds) {
		if(user==null){
			throw new ServiceException("保存用户信息，用户对象不能为空！");
		}
		String saltStr = UUID.randomUUID().toString();
		ByteSource salt = ByteSource.Util.bytes(saltStr);
		String pwd = new SimpleHash("MD5",user.getPassword(),salt).toString();
		user.setPassword(pwd);
		user.setSalt(saltStr);
		//保存用户信息
		int i = userMapper.insert(user);
		if(i==-1)
		throw new ServiceException("保存用户信息失败！");
		//保存用户角色信息
		String[] roleIdArray=roleIds.split(",");
		int counts = userRoleMapper.insert(user.getId(),roleIdArray);
		if(counts!=roleIdArray.length)
		throw new ServiceException("保存用户角色失败！");
	}

	@Override
	public void updateUser(User user, String roleIds) {
		// TODO Auto-generated method stub
		
	}

	/** 根据id查询用户信息*/
	@Override
	public Map<String,Object> findUserById(Integer userId) {
		if(userId==null)
		throw new ServiceException("用户id不能为空！");
		User user = userMapper.findUserById(userId);
		if(user==null)
		throw new ServiceException("查询用户信息失败！");
		List<Integer> roleIds = 
		userRoleMapper.findRoleIdsByUserId(userId);
		if(roleIds==null||roleIds.size()==0)
		throw new ServiceException("查询用户角色信息失败！");
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("roleIds",roleIds);
		map.put("user", user);
		return map;
	}
	

	@Override
	public List<String> findUserPermission(Integer userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> findUserMenu(Integer userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> findRole() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void validById(Integer userId, Integer valid) {
		// TODO Auto-generated method stub
		
	}

}
