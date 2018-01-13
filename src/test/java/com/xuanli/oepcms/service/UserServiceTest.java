package com.xuanli.oepcms.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.beanutils.converters.CalendarConverter;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.xuanli.oepcms.BaseTest;
import com.xuanli.oepcms.entity.User;
import com.xuanli.oepcms.mapper.UserMapper;
import com.xuanli.oepcms.shiro.SysShiroService;
import com.xuanli.oepcms.util.PasswordUtil;

public class UserServiceTest extends BaseTest {
    @Autowired
    private UserService userService;
	@Autowired
	private SysShiroService loginService;

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
    public void saveUser() {
    	
    	User user = new User();
    	user.setUsername("wangwu");
    	user.setSchoolId("1111ab");
    	user.setClasId("1122");
    	user.setMobile("18600000000");
    	user.setCaptcha("6666aa");
    	user.setPassword("123456");
    	user.setCreateId("1");
    	user.setUpdateId("1");
    	userService.saveUser(user,"1");
    	System.out.println("saveUser:"+user);
        
    	User u = userService.findById(user.getId());
        assertThat(user.getId(), notNullValue());
        assertThat(user.getSchoolId(), is("1111ab"));
        assertThat(user.getMobile(), is("18600000000"));
    }
    
    @Test
    public void updateUser() {
    	User newUser = userService.findById(5);
    	System.out.println("newUser:"+newUser);
    	newUser.setUsername("lisi" + UUID.randomUUID().toString());
    	newUser.setGender("男");
    	newUser.setStudySection("小学");
    	newUser.setGrade("一年级");
    	newUser.setBookEdition("1.1.1");
    	userService.updateUser(newUser,"1");
    	System.out.println("updateUser:"+newUser);
    	
    	User u = userService.findById(5);
        assertThat(u.getId(), notNullValue());
        assertThat(newUser.getUsername(), is(u.getUsername()));
        assertThat(newUser.getStudySection(), is("小学"));
        assertThat(newUser.getGender(), is("男"));
        assertThat(newUser.getBookEdition(), is("1.1.1"));
    }
    
    //测试修改信息动态sql
    @Test
    public void update() {
    	Calendar c = Calendar.getInstance();
    	User user = userService.findById(1);
    	user.setUsername("zhangsan");
    	user.setPassword(PasswordUtil.generate("123456"));
    	user.setDesc("1");
    	user.setGender("女");
    	user.setMobile("18688888888");
    	user.setBirthDate(c.getTime());
    	user.setSchoolId("1234AB");
    	user.setClasId("1.1");
    	user.setSalt("1");
    	user.setCaptcha("666666");
    	user.setStudySection("初中");
    	user.setGrade("二年级");
    	user.setBookEdition("1.1.2");
    	user.setProvince("河北省");
    	user.setCity("石家庄");
    	user.setDistrict("藁城区");
    	user.setCreateId("lisi");
    	user.setCreateDate(c.getTime());
    	user.setUpdateId("lisi");
    	user.setUpdateDate(c.getTime());
    	user.setEnableFlag(0);
    	userService.updateUser(user, "5");
    	System.out.println(user);
    }
    
    @Test
    public void find() {
    	User user = new User();
    	List<User> users = userService.find();
    	for(User u:users) {
    		System.out.println(u);
    	}
    }
    
    @Test
    public void login() {
//    	User user = new User();
    	loginService.login("wangwu", "123456");
    }

}
