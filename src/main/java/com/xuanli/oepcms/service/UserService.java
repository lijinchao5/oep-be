package com.xuanli.oepcms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xuanli.oepcms.entity.User;
import com.xuanli.oepcms.exception.ServiceException;
import com.xuanli.oepcms.mapper.UserMapper;
import com.xuanli.oepcms.util.PasswordUtil;


@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void add(User user) {
        // 需要对密码加密，但不想修改传递过来的user参数，所以拷贝一份
        User copyedUser = new User(user);
        copyedUser.setPassword(PasswordUtil.generate(copyedUser.getPassword()));

        userMapper.add(copyedUser);
    }

    public User findById(long id) {
        return userMapper.findById(id);
    }

    public User findByName(String name) {
        return userMapper.findByName(name);
    }

    public List<User> find() {
        return userMapper.find();
    }
    
    
    
    public List<User> findUser(String username,String password){
    	User user = new User();
		//1.验证用户名和密码
		if(!username.equals(user.getUsername())||!password.equals(user.getPassword())){
			throw new ServiceException("用户名或密码不正确");
		}
		//2.查找数据
		List<User> list= userMapper.findUser(username, password);
		return list;
    }
    
    public void save(User user) {
    	
		//验证参数的有效性
		if(user==null){
			throw new ServiceException("保存对象不能为空");
		}
		//执行保存动作(此处可能有运行时异常,之前已做处理,会自动抛出异常)
		int rows = userMapper.insert(user);
		if(rows<1){
			throw new ServiceException("保存失败");
		}

    }
    
}
