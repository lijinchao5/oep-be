package com.xuanli.oepcms.service;

import java.util.List;

import com.xuanli.oepcms.entity.User;

public class S {
	// public void add(User user) {
	// // 需要对密码加密，但不想修改传递过来的user参数，所以拷贝一份
	// User copyedUser = new User(user);
	// copyedUser.setPassword(PasswordUtil.generate(copyedUser.getPassword()));
	//
	// userMapper.add(copyedUser);
	// }

	// public User findById(Integer id) {
	// return userMapper.findById(id);
	// }
	//
	// public User findByName(String name) {
	// return userMapper.findByName(name);
	// }
	//
//	public List<User> find() {
//		return userMapper.find();
//	}

	/// ** 保存用户信息，先保存用户，再保存用户角色关系*/
	// @Transactional
	// public int saveUser(User user) {
	// if(user==null){
	// throw new ServiceException("保存用户信息，用户对象不能为空！");
	// }
	// // 需要对密码加密，但不想修改传递过来的user参数，所以拷贝一份
	// User copyedUser = new User(user);
	// copyedUser.setPassword(PasswordUtil.generate(copyedUser.getPassword()));
	// System.out.println(copyedUser);
	// //保存用户信息
	// int count = userMapper.insert(copyedUser);
	// System.out.println(count);
	// if(count==-1)
	// throw new ServiceException("保存用户信息失败！");
	//// //保存用户角色信息
	//// String[] roleIdArray=roleIds.split(",");
	//// int counts = userRoleMapper.insertUser(user.getId(),roleIdArray);
	//// if(counts!=roleIdArray.length)
	//// throw new ServiceException("保存用户角色失败！");
	// return count;
	// }
	//
	// public static void main(String[] args) {
	// UserService u = new UserService();
	// User user = new User();
	// user.setId(9);
	// user.setUsername("lisi");
	// user.setSchoolId("1111");
	// user.setClasId("11");
	// user.setMobile("18611111111");
	// user.setCaptcha("6666");
	// user.setPassword("123");
	// user.setCreateId("1");
	// user.setUpdateId("1");
	// System.out.println(user);
	// int i =u.saveUser(user);
	// System.out.println(i);
	// }
}
