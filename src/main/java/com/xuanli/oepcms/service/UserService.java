package com.xuanli.oepcms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xuanli.oepcms.entity.User;
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
}
