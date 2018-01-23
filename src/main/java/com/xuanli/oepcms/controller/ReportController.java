/**
 * @fileName:  ReportController.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年1月22日 上午9:32:57
 */ 
package com.xuanli.oepcms.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xuanli.oepcms.entity.UserEntity;
import com.xuanli.oepcms.service.ReportService;
import com.xuanli.oepcms.vo.RestResult;

/** 
 * @author  QiaoYu 
 */
@RestController
@RequestMapping(value = "/report/")
public class ReportController extends BaseController{
	@Autowired
	ReportService reportService;
	/**
	 * @Description:  TODO 生成作业报告
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年1月22日 上午9:40:32
	 */
	@RequestMapping(value = "generatorHomeworkReport.do")
	public RestResult<String> generatorHomeworkReport(long homeworkId) {
		UserEntity userEntity = getCurrentUser();
		return reportService.generatorHomeworkReport(homeworkId,userEntity.getId());
	}
	/**
	 * @Description:  TODO 获取作业报告信息
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年1月22日 上午9:40:43
	 */
	@RequestMapping(value = "homeworkReport.do", method = RequestMethod.POST)
	public RestResult<Map<String, Object>> homeworkReport(long homeworkId) {
		return reportService.homeworkReport(homeworkId);
	}
}
