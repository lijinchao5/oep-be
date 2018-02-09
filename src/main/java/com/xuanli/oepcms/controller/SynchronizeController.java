/**
 * 
 */
package com.xuanli.oepcms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.xuanli.oepcms.contents.ExceptionCode;
import com.xuanli.oepcms.entity.UserEntity;
import com.xuanli.oepcms.service.UserService;
import com.xuanli.oepcms.vo.RestResult;

/**
 * @author lijinchao
 * @date 2018年2月7日 下午5:55:10
 */
@RestController
@RequestMapping(value = "/syncUser/")
public class SynchronizeController extends BaseController{
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "getUsers.do", method = RequestMethod.GET)
	public RestResult<List<UserEntity>> getUsers(){
		List<UserEntity> UserEntities = userService.getUsers();
		return ok(UserEntities);
	}
	/**
	 * Title: saveUser 
	 * Description:  增加用户
	 * @date 2018年2月7日 下午6:34:00
	 * @param userEntity
	 * @return
	 */
	@RequestMapping(value = "saveUser.do", method = RequestMethod.POST)
	public String saveUser(@RequestBody String userInfo){
		UserEntity userEntity = JSONObject.parseObject(userInfo,UserEntity.class);
		userEntity.setId(null);
		try {
			int result = userService.saveUser(userEntity);
			if(result>0) {
				return "0";
			}else {
				return "1";
			}
		} catch (Exception e) {
			logger.error("增加用户失败",e);
			e.printStackTrace();
			return "1";
		}
	}
	@RequestMapping(value = "updateUser.do", method = RequestMethod.PUT)
	public String updateUser(@RequestBody String userInfo){
		UserEntity userEntity = JSONObject.parseObject(userInfo,UserEntity.class);
		try {
			int result = userService.updateUser(userEntity);
			if(result>0) {
				return "0";
			}else {
				return "1";
			}
		} catch (Exception e) {
			logger.error("修改用户失败",e);
			e.printStackTrace();
			return "1";
		}
	}
	
	@RequestMapping(value = "deleteUser.do", method = RequestMethod.DELETE)
	public RestResult<String> deleteUser(Long userId){
		if (null == userId) {
			return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE, "删除用户id不能为空");
		}
		try {
			int result = userService.disableUser(userId);
			if(result>0) {
				return okNoResult("删除用户成功");
			}else {
				return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE, "删除用户失败.");
			}
		} catch (Exception e) {
			logger.error("删除用户失败",e);
			e.printStackTrace();
			return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE, "删除用户失败.");
		}
	}
}