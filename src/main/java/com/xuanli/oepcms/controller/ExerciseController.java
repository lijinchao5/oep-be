/**
 * 
 */
package com.xuanli.oepcms.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xuanli.oepcms.contents.ExceptionCode;
import com.xuanli.oepcms.entity.ExerciseDetailEntity;
import com.xuanli.oepcms.entity.ExerciseEntity;
import com.xuanli.oepcms.service.ExerciseService;
import com.xuanli.oepcms.util.PageBean;
import com.xuanli.oepcms.vo.RestResult;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @author lijinchao
 * @date 2018年3月9日 下午5:16:16
 */
@RestController()
@RequestMapping(value = "/exercise/")
public class ExerciseController extends BaseController{
	@Autowired
	ExerciseService exerciseService;
	
	@ApiOperation(value = "自主练习列表", notes = "自主练习列表")
	@ApiImplicitParams({ 
			@ApiImplicitParam(name = "level", value = "级别:A,B,C", required = false, dataType = "String"),
			@ApiImplicitParam(name = "type", value = "类型", required = false, dataType = "String"),
			@ApiImplicitParam(name = "studentId", value = "学生id", required = true, dataType = "Long"),
			@ApiImplicitParam(name = "rows", value = "每页显示条数", required = true, dataType = "Integer"),
			@ApiImplicitParam(name = "page", value = "页数", required = true, dataType = "Integer") 
	})
	@RequestMapping(value = "findExercisePage.do", method = RequestMethod.GET)
	public RestResult<PageBean> findExercisePage(String level, String type, Long studentId, Integer rows, Integer page) {
		PageBean pageBean = initPageBean(page, rows);
		ExerciseEntity exerciseEntity = new ExerciseEntity();
		exerciseEntity.setStudentId(studentId);
		exerciseEntity.setLevel(level);
		exerciseService.findExercisePage(exerciseEntity, pageBean);
		return ok(pageBean);
	}
	
	@ApiOperation(value = "自主练习详情", notes = "自主练习详情")
	@ApiImplicitParams({ 
			@ApiImplicitParam(name = "level", value = "级别:A,B,C", required = false, dataType = "String"),
			@ApiImplicitParam(name = "type", value = "类型", required = false, dataType = "String"),
			@ApiImplicitParam(name = "studentId", value = "学生id", required = true, dataType = "Long"),
			@ApiImplicitParam(name = "rows", value = "每页显示条数", required = true, dataType = "Integer"),
			@ApiImplicitParam(name = "page", value = "页数", required = true, dataType = "Integer") 
	})
	@RequestMapping(value = "getExerciseDetail.do", method = RequestMethod.GET)
	public RestResult<ExerciseDetailEntity> getExerciseDetail(Long exerciseId) {
		Long studentId = getCurrentUser().getId();
		return ok(exerciseService.getExerciseDetail(exerciseId, studentId));
	}
	
	@ApiOperation(value = "学生做练习", notes = "学生做练习")
	@ApiImplicitParams({ @ApiImplicitParam(name = "detailId", value = "练习详情id", required = true, dataType = "Long"),
			@ApiImplicitParam(name = "exerciseId", value = "练习id", required = true, dataType = "Long"),
			@ApiImplicitParam(name = "audioFile", value = "学生音频文件类答案", required = false, dataType = "String"),
			 })
	@RequestMapping(value = "doExercise.do", method = RequestMethod.POST)
	public RestResult<String> doExercise(@RequestParam Long detailId, @RequestParam Long exerciseId, @RequestParam(required=false) String file) {
		try {
			Long studentId = getCurrentUser().getId();
			String result = exerciseService.doExercise(studentId, exerciseId, detailId, file,request);
			if(result.equals("1")) {
				return ok("成功");
			}else {
				return failed(ExceptionCode.UNKNOW_CODE, "练习出现异常");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("练习出现异常", e);
			return failed(ExceptionCode.UNKNOW_CODE, "练习出现异常");
		}
	}
}
