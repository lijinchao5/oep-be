/**
 * @fileName:  ExamController.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年2月6日 下午2:14:30
 */ 
package com.xuanli.oepcms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.xuanli.oepcms.contents.ExceptionCode;
import com.xuanli.oepcms.entity.ExamEntity;
import com.xuanli.oepcms.service.ExamService;
import com.xuanli.oepcms.util.PageBean;
import com.xuanli.oepcms.vo.RestResult;

/** 
 * @author  QiaoYu 
 */
@RestController()
@RequestMapping(value = "/exam/")
public class ExamController extends BaseController{
	@Autowired
	ExamService examService;
	//布置模拟考试
	
	
	
	//提交试卷
	@RequestMapping(value = "submitExam.do", method = RequestMethod.POST)
	public RestResult<String> submitExam(@RequestParam Long examId,@RequestParam String detailIds,@RequestParam(required = false) String answer, @RequestParam(required = false, value = "audiofile") MultipartFile file){
		try {
			Long studentId = getCurrentUser().getId();
			examService.submitExam(studentId,examId,detailIds,answer,file);
			return okNoResult("提交完成");
		} catch (Exception e) {
			e.printStackTrace();
			return failed(ExceptionCode.UNKNOW_CODE, e.getMessage());
		}
	}
	//分页查询考试信息
	@RequestMapping(value = "findExamByPage.do", method = RequestMethod.GET)
	public RestResult<PageBean> findExamByPage(String status, String clasId, Integer rows, Integer page) {
		PageBean pageBean = initPageBean(page, rows);
		// 保证这个班是这个老师创建的
		ExamEntity examEntity = new ExamEntity();
		examEntity.setStatus(status);
		examEntity.setClasIds(clasId);
		examEntity.setCreateId(getCurrentUser().getId());
		examService.findExamByPage(examEntity, pageBean);
		return ok(pageBean);
	}
	
	
	//
	
	
}
