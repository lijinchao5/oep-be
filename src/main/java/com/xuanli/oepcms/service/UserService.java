package com.xuanli.oepcms.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xuanli.oepcms.entity.SchoolEntity;
import com.xuanli.oepcms.entity.UserClasEntity;
import com.xuanli.oepcms.entity.UserEntity;
import com.xuanli.oepcms.entity.UserSchoolEntity;
import com.xuanli.oepcms.mapper.UserEntityMapper;
import com.xuanli.oepcms.util.PasswordUtil;
import com.xuanli.oepcms.util.SessionUtil;

@Service
@Transactional
public class UserService {

	@Autowired
	private UserEntityMapper userDao;
	@Autowired
	private SchoolService schoolService;

	/**
	 * @Description: TODO
	 * @CreateName: QiaoYu
	 * @CreateDate: 2018年1月15日 下午2:13:52
	 */
	public String login(String username, String password, HttpServletRequest request) {
		UserEntity userEntity = new UserEntity();
		userEntity.setMobile(username);
		List<UserEntity> userEntities = userDao.login(userEntity);
		if (null != userEntities && userEntities.size() > 0) {
			UserEntity result = userEntities.get(0);
			if (PasswordUtil.verify(password, result.getPassword())) {
				// 登陆成功
				SessionUtil.setSessionUser(request, result);
				return "1";
			} else {
				// 用户名或者密码错误
				return "2";
			}
		} else {
			// 用户名或者密码错误
			return "2";
		}

	}

	/**
	 * @Description: TODO 保存用户
	 * @CreateName: QiaoYu
	 * @CreateDate: 2018年1月15日 下午3:28:01
	 */
	public int saveUser(UserEntity userEntity) {
		userEntity.setCreateDate(new Date());
		userEntity.setEnableFlag("T");
		return userDao.insertUserEntity(userEntity);
	}

	/**
	 * @Description: TODO 查询用户
	 * @CreateName: QiaoYu
	 * @CreateDate: 2018年1月15日 下午4:30:32
	 */
	public List<UserEntity> selectUserEntity(UserEntity userEntity) {
		return userDao.selectUserEntity(userEntity);
	}

	/**
	 * @Description: TODO 教师注册
	 * @CreateName: QiaoYu
	 * @CreateDate: 2018年1月15日 下午4:50:08
	 */
	public String teacherRegist(String schoolId, String mobile, String password) {
		//判断手机号码是否已经注册
		UserEntity registUser = new UserEntity();
		registUser.setMobile(mobile);
		List<UserEntity> userEntities = userDao.selectUserEntity(registUser);
		if (null != userEntities && userEntities.size() <= 0) {
		} else {
			// 手机号码已经存在
			return "2";
		}
		// 查看校区id是否存在
		SchoolEntity schoolEntity = new SchoolEntity();
		schoolEntity.setSchoolId(schoolId);
		List<SchoolEntity> schoolEntities = schoolService.selectSchoolEntity(schoolEntity);
		if (null != schoolEntities && schoolEntities.size() > 0) {
			// 校区存在
			SchoolEntity result = schoolEntities.get(0);
			UserEntity userEntity = new UserEntity();
			userEntity.setCreateDate(new Date());
			userEntity.setEnableFlag("T");
			userEntity.setPassword(PasswordUtil.generate(password));
			userEntity.setMobile(mobile);
			//教师角色id为3
			userEntity.setRoleId(new Integer(3));
			userDao.insertUserEntity(userEntity);
			//添加教师和学校的关联关系
			UserSchoolEntity userSchoolEntity = new UserSchoolEntity();
			userSchoolEntity.setSchoolId(result.getId());
			userSchoolEntity.setUserId(userEntity.getId());
			userDao.insertUserSchool(userSchoolEntity);
			return "0";
		} else {
			return "1";
		}
	}

}
