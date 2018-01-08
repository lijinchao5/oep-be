package com.xuanli.oepcms.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.xuanli.oepcms.BaseTest;
import com.xuanli.oepcms.entity.User;
import com.xuanli.oepcms.util.PasswordUtil;

public class UserServiceTest extends BaseTest {
    @Autowired
    private UserService userService;

    @Test
    public void shouldAddUser() {
        User user = new User();
        user.setUsername("test" + UUID.randomUUID().toString());
        user.setDesc("test desc");
        user.setPassword("password");
        userService.add(user);

        User addedUser = userService.findByName(user.getUsername());
        assertThat(addedUser, notNullValue());
        assertThat(addedUser.getId(), notNullValue());
//        assertThat(addedUser.getId().longValue(), greaterThan(0L));
        assertThat(addedUser.getUsername(), is(user.getUsername()));
        assertThat(addedUser.getDesc(), is(user.getDesc()));
        assertThat(addedUser.getPassword(), is(not(user.getPassword())));
        assertThat(PasswordUtil.verify(user.getPassword(), addedUser.getPassword()), is(true));
    }
    
    @Test
    public void save() {
 
    	User user = new User();
    	user.setId(2);
    	user.setUsername("lisi");
    	user.setSchool_id("1111");
    	user.setClas_id("1.1");
    	user.setMobile("18611111111");
    	user.setCaptcha("6666");
    	user.setPassword("123");
    	user.setCreate_id("1");
    	user.setUpdate_id("1");
    	System.out.println(user);
        
        assertThat(user.getId(), notNullValue());
        assertThat(user.getUsername(), notNullValue());
    }
    
//    @Test
//    public void findUser() {
//    	User user = new User();
//    	List<User> list = userService.findUser(user.setUsername("zhangsan"), user.setPassword("123456"));
//    	System.out.println(list);
//    	
//        assertThat(user.getId(), notNullValue());
//        assertThat(user.getUsername(), notNullValue());
//    }
    
}
