/**
 * @fileName:  ExamController.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年2月6日 下午2:14:30
 */
package com.xuanli.oepcms.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xuanli.oepcms.contents.ExceptionCode;
import com.xuanli.oepcms.controller.bean.ExamAnswerBean;
import com.xuanli.oepcms.entity.ExamEntity;
import com.xuanli.oepcms.entity.UserEntity;
import com.xuanli.oepcms.service.ExamService;
import com.xuanli.oepcms.util.PageBean;
import com.xuanli.oepcms.vo.RestResult;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author QiaoYu
 */
@RestController()
@RequestMapping(value = "/exam/")
public class ExamController extends BaseController {
	@Autowired
	ExamService examService;

	// 布置模拟考试
	@ApiOperation(value = "布置模拟考试", notes = "布置模拟考试方法")
	@ApiImplicitParams({ @ApiImplicitParam(name = "name", value = "考试名称", required = true, dataType = "String"),
			@ApiImplicitParam(name = "notice", value = "考试须知", required = true, dataType = "String"),
			@ApiImplicitParam(name = "classIds", value = "班级id,可以多个:1,2,3", required = true, dataType = "String"),
			@ApiImplicitParam(name = "startTime", value = "作业开始时间 格式 yyyy-MM-dd HH:mm:ss", required = true, dataType = "String"),
			@ApiImplicitParam(name = "endTime", value = "作业完成时间 格式 yyyy-MM-dd HH:mm:ss", required = true, dataType = "String"),
			@ApiImplicitParam(name = "paperId", value = "作业列表", required = true, dataType = "Long") })
	@RequestMapping(value = "genteratorExam.do", method = RequestMethod.POST)
	public RestResult<String> genteratorExam(String name, String notice, String classIds, Date startTime, Date endTime, Long paperId) {
		try {
			Long userId = getCurrentUser().getId();
			return examService.genteratorExam(userId, name, notice, classIds, startTime, endTime, paperId);
		} catch (Exception e) {
			e.printStackTrace();
			return failed(ExceptionCode.UNKNOW_CODE, e.getMessage());
		}
	}

	// 提交试卷
	@ApiOperation(value = "提及试卷", notes = "提及试卷方法")
	@ApiImplicitParams({ @ApiImplicitParam(name = "examId", value = "考试id", required = true, dataType = "Long"),
			@ApiImplicitParam(name = "answers", value = " 格式: [{\"key\":1,\"value\":\"A\"},{\"key\":2,\"value\":\"B\"}] key:subjectDetailId value:答案", dataType = "String"),
			@ApiImplicitParam(name = "timeout", value = "剩余时间", required = true, dataType = "Integer") })
	@RequestMapping(value = "submitExam.do", method = RequestMethod.POST)
	public RestResult<String> submitExam(@RequestParam Long examId, @RequestParam List<ExamAnswerBean> answers, @RequestParam Integer timeout) {
		try {
			Long studentId = getCurrentUser().getId();
			examService.submitExam(studentId, examId, answers, timeout);
			return okNoResult("提交完成");
		} catch (Exception e) {
			e.printStackTrace();
			return failed(ExceptionCode.UNKNOW_CODE, e.getMessage());
		}
	}

	// 分页查询考试信息
	@ApiOperation(value = "分页查询考试信息", notes = "分页查询考试信息")
	@ApiImplicitParams({ @ApiImplicitParam(name = "status", value = "考试状态: start_time > NOW():未开始; NOW() > start_time:进行中; NOW() > end_time:结束", required = false, dataType = "String"),
			@ApiImplicitParam(name = "clasId", value = "班级id", required = false, dataType = "Long"),
			@ApiImplicitParam(name = "rows", value = "每页显示条数", required = true, dataType = "Integer"),
			@ApiImplicitParam(name = "page", value = "页数", required = true, dataType = "Integer") })
	@RequestMapping(value = "findExamByPage.do", method = RequestMethod.GET)
	public RestResult<PageBean> findExamByPage(String status, Long clasId, Integer rows, Integer page) {
		PageBean pageBean = initPageBean(page, rows);
		// 保证这个班是这个老师创建的
		ExamEntity examEntity = new ExamEntity();
		examEntity.setStatus(status);
		examEntity.setClassId(clasId);
		examEntity.setCreateId(getCurrentUser().getId());
		examService.findExamByPage(examEntity, pageBean);
		return ok(pageBean);
	}

	// 生成模拟考试报告
	@ApiIgnore
	@RequestMapping(value = "generatorExamReport.do", method = RequestMethod.PUT)
	public RestResult<String> generatorExamReport(@RequestParam Long examId) {
		if (null == examId) {
			return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE, "请选择考试信息");
		}
		UserEntity userEntity = getCurrentUser();
		return examService.generatorExamReport(examId, userEntity.getId());
	}

	// 统计作业信息
	@ApiOperation(value = "统计作业信息", notes = "统计作业信息")
	@ApiImplicitParams({ @ApiImplicitParam(name = "examId", value = "考试id", required = true, dataType = "Long") })
	@RequestMapping(value = "getExamReport.do", method = RequestMethod.GET)
	public RestResult<Map<String, Object>> getExamReport(Long examId) {
		return examService.getExamReport(examId);
	}

	// 查看考试详情
	@ApiOperation(value = "查看考试详情", notes = "查看考试详情")
	@ApiImplicitParams({ @ApiImplicitParam(name = "examId", value = "考试id", required = true, dataType = "Long") })
	@RequestMapping(value = "getExamDetail.do", method = RequestMethod.GET)
	public RestResult<Map<String, Object>> getExamDetail(Long examId) {
		return examService.getExamDetail(examId);
	}

	// 学生模拟考试列表
	@ApiIgnore
	@RequestMapping(value = "findStudentExamByPage.do", method = RequestMethod.GET)
	public RestResult<PageBean> findStudentExamByPage(Integer rows, Integer page) {
		PageBean pageBean = initPageBean(page, rows);
		// 保证这个班是这个老师创建的
		Long userId = getCurrentUser().getId();
		examService.findStudentExamByPage(userId, pageBean);
		return ok(pageBean);
	}

	// 查看学生考试详情
	@ApiIgnore
	@RequestMapping(value = "findStudentExamDetail.do", method = RequestMethod.GET)
	public RestResult<Map<String, Object>> findStudentExamDetail(Long examId) {
		return examService.findStudentExamDetail(examId, getCurrentUser().getId());
	}

}
