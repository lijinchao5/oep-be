package com.xuanli.oepcms.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.xuanli.oepcms.entity.User;

@Mapper
public interface UserMapper {
    @Select("INSERT INTO xl_user (username, `desc`, `password`,`mobile`) VALUES (#{username}, #{desc}, #{password}, #{mobile})")
    void add(User user);
    
    /**根据id查询用户信息*/
    @Select("SELECT id, username, `desc`, `password` FROM xl_user WHERE id = #{id}")
    User findById(Integer id);
    
    @Select("SELECT id, `username`, `desc`, `password` FROM xl_user WHERE username = #{username}")
    User findByName(String username);
    
    /**根据手机号查询用户信息*/
    @Select("SELECT id, username, `password`,`mobile` FROM xl_user WHERE mobile = #{mobile}")
    User findByMobile(String mobile);
    
    /**获取用户列表*/
    @Select("SELECT * FROM xl_user")
    List<User> find();
    
    /**根据角色保存用户,角色id暂时先不传入*/
    int saveUser(User user);
    
    /**登陆*/
    @Select("SELECT `mobile`,`password` FROM xl_user WHERE mobile=#{mobile} AND password=#{password}")
    public List<User> findByNameAndPassword(String mobile,String password);
    
    
    /**插入数据*/
    @Select("INSERT INTO xl_user (id,`username`,`school_id`,`clas_id`,`mobile`,`captcha`,`password`,`create_date`, " + 
    		"			`update_date`,`create_id`,`update_id`)" + 
    		"		VALUES(" + 
    		"		#{id},#{username},#{schoolId},#{clasId},#{mobile},#{captcha},#{password},#{createDate}, " + 
    		"			#{updateDate},#{createId},#{updateId})")
    public int insert(User user);
    
    /**查询用户的权限*/
    @Select("		SELECT m.permission " + 
    		"		FROM xl_user_role ur,xl_role_menu rm ,xl_menu m " + 
    		"		WHERE ur.role_id = rm.role_id " + 
    		"		AND rm.menu_id = m.id AND ur.user_id = #{userId} ")
	List<String> findUserPermissions(Integer userId);
    
	List<Map<String,Object>> findUserMenus(Integer userId);
}