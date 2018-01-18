/**
 * @fileName:  HomeworkController.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年1月18日 下午3:24:28
 */ 
package com.xuanli.oepcms.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xuanli.oepcms.contents.ExceptionCode;
import com.xuanli.oepcms.service.HomeworkService;
import com.xuanli.oepcms.vo.RestResult;

/** 
 * @author  QiaoYu 
 */
@RestController()
//@RequestMapping(value = "/homework", method = RequestMethod.POST)
@RequestMapping(value = "/homework/")
public class HomeworkController extends BaseController{
	@Autowired
	private HomeworkService homeworkService;
	
	/**
	 * @Description:  TODO 教师布置家庭作业
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年1月18日 下午3:31:48
	 */
	@RequestMapping(value = "makeHomeWork.do")
	public RestResult<String> makeHomeWork(String name,String clasId,Date endTime,String remark,String section){
		try {
			Long createId = getCurrentUser().getId();
			homeworkService.makeHomeWork(name, clasId, endTime, remark, section,createId);
			return ok("成功.");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("布置家庭作业出现异常",e);
			return failed(ExceptionCode.UNKNOW_CODE, "布置家庭作业出现异常");
		}
	}
	
	
	
	
	
}
