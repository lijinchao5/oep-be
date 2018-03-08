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

/**
 * @author QiaoYu
 */
@RestController()
@RequestMapping(value = "/exam/")
public class ExamController extends BaseController {
	@Autowired
	ExamService examService;

	// 布置模拟考试
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
	@RequestMapping(value = "generatorExamReport.do", method = RequestMethod.PUT)
	public RestResult<String> generatorExamReport(@RequestParam Long examId) {
		if (null == examId) {
			return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE, "请选择考试信息");
		}
		UserEntity userEntity = getCurrentUser();
		return examService.generatorExamReport(examId, userEntity.getId());
	}

	// 统计作业信息
	@RequestMapping(value = "getExamReport.do", method = RequestMethod.GET)
	public RestResult<Map<String, Object>> getExamReport(Long examId) {
		return examService.getExamReport(examId);
	}

	// 查看考试详情
	@RequestMapping(value = "getExamDetail.do", method = RequestMethod.GET)
	public RestResult<Map<String, Object>> getExamDetail(Long examId) {
		return examService.getExamDetail(examId);
	}

	// 学生模拟考试列表
	@RequestMapping(value = "findStudentExamByPage.do", method = RequestMethod.GET)
	public RestResult<PageBean> findStudentExamByPage(Integer rows, Integer page) {
		PageBean pageBean = initPageBean(page, rows);
		// 保证这个班是这个老师创建的
		Long userId = getCurrentUser().getId();
		examService.findStudentExamByPage(userId, pageBean);
		return ok(pageBean);
	}

	// 查看学生考试详情
	@RequestMapping(value = "findStudentExamDetail.do", method = RequestMethod.GET)
	public RestResult<Map<String, Object>> findStudentExamDetail(Long examId) {
		return examService.findStudentExamDetail(examId, getCurrentUser().getId());
	}

}
