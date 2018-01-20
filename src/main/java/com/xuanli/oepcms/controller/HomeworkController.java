/**
 * @fileName:  HomeworkController.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年1月18日 下午3:24:28
 */
package com.xuanli.oepcms.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.xuanli.oepcms.contents.ExceptionCode;
import com.xuanli.oepcms.controller.bean.HomeworkBean;
import com.xuanli.oepcms.service.HomeworkService;
import com.xuanli.oepcms.vo.RestResult;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;

/**
 * @author QiaoYu
 */

@RestController()
// @RequestMapping(value = "/homework", method = RequestMethod.POST)
@RequestMapping(value = "/homework/")
public class HomeworkController extends BaseController {
	@Autowired
	private HomeworkService homeworkService;

	/**
	 * @Description: TODO 教师布置家庭作业
	 * @CreateName: QiaoYu
	 * @CreateDate: 2018年1月18日 下午3:31:48
	 */
	@ApiOperation(value="布置作业", notes="老师布置作业方法")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "作业名称", required = true, dataType = "String"),
            @ApiImplicitParam(name = "clasId", value = "班级id", required = true, dataType = "String"),
            @ApiImplicitParam(name = "endTime", value = "作业完成时间 格式 yyyy-MM-dd HH:mm:ss", required = true, dataType = "String"),
            @ApiImplicitParam(name = "remark", value = "", required = true, dataType = "String"),
            @ApiImplicitParam(name = "homeworkBeans", value = "作业列表", required = true, dataType = "String")
    })
	@RequestMapping(value = "makeHomeWork.do", method = RequestMethod.POST)
	public RestResult<String> makeHomeWork(String name, String clasId, Date endTime, String remark, List<HomeworkBean> homeworkBeans) {
		try {
			Long createId = getCurrentUser().getId();
			homeworkService.makeHomeWork(name, clasId, endTime, remark, homeworkBeans, createId);
			return ok("成功.");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("布置家庭作业出现异常", e);
			return failed(ExceptionCode.UNKNOW_CODE, "布置家庭作业出现异常");
		}
	}

	/**
	 * 学生做作业
	 * 
	 * @Description: TODO
	 * @CreateName: QiaoYu
	 * @CreateDate: 2018年1月19日 上午9:32:12
	 */
	@ApiOperation(value="学生做作业", notes="学生做作业方法")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sectionId", value = "作业详情id", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "homeworkId", value = "作业id", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "audioFile", value = "学生音频文件类答案", required = true, dataType = "File"),
            @ApiImplicitParam(name = "text", value = "学生文本类答案", required = true, dataType = "String"),
    })
	@RequestMapping(value = "doHomeWork.do", method = RequestMethod.POST)
	public RestResult<String> doHomeWork(Long sectionId, Long homeworkId, @RequestParam("audioFile") MultipartFile file, String text) {
		try {
			Long studentId = getCurrentUser().getId();
			String result = homeworkService.doHomeWork(studentId, sectionId, homeworkId, file, text, request);
			if (result.equals("0")) {
				return ok("成功.");
			} else if (result.equals("1")) {
				return failed(ExceptionCode.UNKNOW_CODE, "上传录音出现异常");
			} else {
				return failed(ExceptionCode.UNKNOW_CODE, "未知错误");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("布置家庭作业出现异常", e);
			return failed(ExceptionCode.UNKNOW_CODE, "布置家庭作业出现异常");
		}
	}

}
