package com.xuanli.oepcms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.xuanli.oepcms.entity.User;

@Mapper
public interface UserMapper {
    @Select("INSERT INTO user (name, `desc`, `password`) VALUES (#{name}, #{desc}, #{password})")
    void add(User user);

    @Select("SELECT id, name, `desc`, `password` FROM user WHERE id = #{id}")
    User findById(long id);

    @Select("SELECT id, name, `desc`, `password` FROM user WHERE name = #{name}")
    User findByName(String name);

    @Select("SELECT * FROM user")
    List<User> find();
}