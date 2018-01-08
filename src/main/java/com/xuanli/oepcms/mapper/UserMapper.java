package com.xuanli.oepcms.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
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
    
    
    
    @Select("SELECT * FROM xl_user")
    List<User> findUser(String username,String password);
    
    @Insert("INSERT INTO xl_user (id,`username`,`school_id`,`clas_id`,`mobile`,`captcha`,`password`,`create_date`," + 
    		"			`update_date`,`create_id`,`update_id`)" + 
    		"		VALUES(" + 
    		"		#{id},#{username},#{school_id},#{clas_id},#{mobile},#{captcha},#{password},#{create_date}," + 
    		"			#{update_date},#{create_id},#{update_id})")
    int insert(User user);
    
}