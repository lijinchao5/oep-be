package com.xuanli.oepcms.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.xuanli.oepcms.BaseTest;
import com.xuanli.oepcms.entity.User;
import com.xuanli.oepcms.util.PasswordUtil;

public class UserSeTest extends BaseTest {
    @Autowired
    private UserService userService;


//    @Test
//    public void shouldAddUser() {
//        User user = new User();
//        user.setUsername("test" + UUID.randomUUID().toString());
//        user.setDesc("test desc");
//        user.setPassword("password");
//        user.setMobile("18011111111");
//        userService.add(user);
//
//        User addedUser = userService.findByName(user.getUsername());
//        assertThat(addedUser, notNullValue());
//        assertThat(addedUser.getId(), notNullValue());
//        assertThat(addedUser.getId().longValue(), greaterThan(0L));
//        assertThat(addedUser.getUsername(), is(user.getUsername()));
//        assertThat(addedUser.getDesc(), is(user.getDesc()));
//        assertThat(addedUser.getPassword(), is(not(user.getPassword())));
//        assertThat(PasswordUtil.verify(user.getPassword(), addedUser.getPassword()), is(true));
//    }
    
    @Test
    public void save() {
    	
    	User user = new User();
    	user.setUsername("李四" + UUID.randomUUID().toString());
    	user.setSchoolId("1111ab");
    	user.setClasId("1122");
    	user.setMobile("18600000000");
    	user.setCaptcha("6666aa");
    	user.setPassword("123456");
    	user.setCreateId("1");
    	user.setUpdateId("1");
    	userService.saveUser(user, "5");
    	System.out.println(user);
        
        assertThat(user.getId(), notNullValue());
        assertThat(user.getUsername(), notNullValue());
        assertThat(user, notNullValue());
        assertThat(user.getId(), notNullValue());
//        assertThat(user.getId().longValue(), greaterThan(0L));
        assertThat(user.getUsername(), is(user.getUsername()));
        assertThat(user.getMobile(), is(user.getMobile()));
//        assertThat(user.getPassword(), is(not(user.getPassword())));
//        assertThat(PasswordUtil.verify(user.getPassword(), user.getPassword()), is(true));
    }

}
