package com.xuanli.oepcms.service;

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
import com.xuanli.oepcms.util.PasswordUtil;

@Service()
@Transactional
public class UserService {
	
	@Autowired
	private UserMapper userMapper;
//	@Autowired
//	private RoleMapper roleMapper;
	@Autowired
	private UserRoleMapper userRoleMapper;
	
    public void add(User user) {
    	// 需要对密码加密，但不想修改传递过来的user参数，所以拷贝一份
    	User copyedUser = new User(user);
        copyedUser.setPassword(PasswordUtil.generate(copyedUser.getPassword()));

        userMapper.add(copyedUser);
    }

    public User findById(Integer id) {
        return userMapper.findById(id);
    }

    public User findByName(String username) {
        return userMapper.findByName(username);
    }

    public List<User> find() {
        return userMapper.find();
    }
    /**数量查询*/
	public int count(Map<String, Object> map) {
		return userMapper.count(map);
	}
    
	/**保存用户信息*/
	public void saveUser(User user,String roleIds) {
		if(user==null){
			throw new ServiceException("保存用户信息，用户对象不能为空！");
		}
		String saltStr = UUID.randomUUID().toString();
		user.setSalt(saltStr);	
		user.setPassword(PasswordUtil.generate(user.getPassword()));
		
		//保存用户信息
		int i = userMapper.insert(user);
		if(i==-1)
		throw new ServiceException("保存用户信息失败！");
		//保存用户角色信息
		String[] roleIdArray=roleIds.split(",");
		int counts = userRoleMapper.insertRole(user.getId(),roleIdArray);
		if(counts!=roleIdArray.length)
		throw new ServiceException("保存用户角色失败！");
	}
	
	/**修改用户信息*/
	public void updateUser(User user,String roleIds) {
		if(user==null)
		throw new ServiceException("用户对象不能为空！");
//		String saltStr = UUID.randomUUID().toString();
//		user.setSalt(saltStr);	
//		user.setPassword(PasswordUtil.generate(user.getPassword()));
		//更新用户信息
		int i = userMapper.update(user);
		System.out.println("user:"+user);
		if(i!=1)
		throw new ServiceException("修改更新用户信息失败！");
		//更新用户角色信息  - 先删除二者关系，再添加二者关系
		String[] roleArrayIds=roleIds.split(",");
		int counts =userRoleMapper.deleteUserRoles(user.getId());
		if(counts<1)
		throw new RuntimeException("更新用户角色信息失败！");
		int rows = userRoleMapper.insertRole(user.getId(),roleArrayIds);
		if(rows!=roleArrayIds.length)
		throw new ServiceException("更新用户角色失败！");
	}
	
	/**修改密码*/
	public void updatePwd(Integer id) {
		if(id==null) {
			
		}
	}
	
	/** 根据id查询用户信息*/
	public Map<String,Object> findUserById(Integer userId) {
		if(userId==null)
		throw new ServiceException("用户id不能为空！");
		User user = userMapper.findById(userId);
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
	

}
