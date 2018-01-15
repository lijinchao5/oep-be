package com.xuanli.oepcms.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xuanli.oepcms.entity.UserEntity;
import com.xuanli.oepcms.mapper.UserEntityMapper;
import com.xuanli.oepcms.util.PasswordUtil;
import com.xuanli.oepcms.util.SessionUtil;


@Service
@Transactional
public class UserService {
	
	@Autowired
	private UserEntityMapper userDao;

	/**
	 * @Description:  TODO
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年1月15日 下午2:13:52
	 */
	public String login(String username,String password,HttpServletRequest request) {
		UserEntity userEntity = new UserEntity();
		userEntity.setMobile(username);
		List<UserEntity> userEntities = userDao.login(userEntity);
		if (userEntities.size()>0) {
			UserEntity result = userEntities.get(0);
			if (PasswordUtil.verify(password, result.getPassword())) {
				//登陆成功
				SessionUtil.setSessionUser(request, result);
				return "1";
			}else {
				//用户名或者密码错误
				return "2";
			}
		}else {
			//用户名或者密码错误
			return "2";
		}
		
	}
	

}
