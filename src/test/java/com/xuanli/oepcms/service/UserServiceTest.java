package com.xuanli.oepcms.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

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
        user.setName("test" + UUID.randomUUID().toString());
        user.setDesc("test desc");
        user.setPassword("password");
        userService.add(user);

        User addedUser = userService.findByName(user.getName());
        assertThat(addedUser, notNullValue());
        assertThat(addedUser.getId(), notNullValue());
        assertThat(addedUser.getId().longValue(), greaterThan(0L));
        assertThat(addedUser.getName(), is(user.getName()));
        assertThat(addedUser.getDesc(), is(user.getDesc()));
        assertThat(addedUser.getPassword(), is(not(user.getPassword())));
        assertThat(PasswordUtil.verify(user.getPassword(), addedUser.getPassword()), is(true));
    }
}
