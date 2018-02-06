/**
 * 
 */
package com.xuanli.oepcms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xuanli.oepcms.contents.ExceptionCode;
import com.xuanli.oepcms.entity.UserEntity;
import com.xuanli.oepcms.service.SchoolService;
import com.xuanli.oepcms.vo.RestResult;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @author lijinchao
 * @date 2018年2月5日 下午4:51:31
 */
@RestController
@RequestMapping(value = "/school/")
public class SchoolController extends BaseController{
	@Autowired
	SchoolService schoolService;
	
	/**
	 * Title: getTeachingInfo 
	 * Description:   获取教学信息
	 * @date 2018年2月5日 下午4:22:42
	 * @param schoolId
	 * @return
	 */
	@ApiOperation(value = "查询学校信息", notes = "查询学校信息方法")
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "schoolId", value = "学校id", required = true, dataType = "String")})
	@RequestMapping(value = "getTeachingInfo.do", method = RequestMethod.GET)
	public RestResult<List<UserEntity>> getTeachingInfo(@RequestParam String schoolId){
		try {
			if(null==schoolId) {
				return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE, "学校id不能为空");
			}
			List<UserEntity> userEntities = schoolService.getTeachingInfo(schoolId,getCurrentUser().getId());
			if(null!=userEntities&&userEntities.size()>0) {
				return ok(userEntities);
			}else {
				return failed(ExceptionCode.UNKNOW_CODE, "获取老师教学信息出现错误");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取老师教学信息失败");
			return failed(ExceptionCode.UNKNOW_CODE, "获取老师教学信息出现错误");
		}
	}
}
