/**
 * @fileName:  PaperController.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年2月26日 上午11:46:14
 */
package com.xuanli.oepcms.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xuanli.oepcms.service.PaperService;
import com.xuanli.oepcms.util.PageBean;
import com.xuanli.oepcms.vo.RestResult;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @author QiaoYu
 */
@RestController
@RequestMapping(value = "/paper/")
public class PaperController extends BaseController{
	@Autowired
	PaperService paperService;

	@ApiOperation(value = "分页查询试卷信息", notes = "分页查询试卷信息方法")
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "gradeLevelId", value = "年级", required = true, dataType = "Integer"),
		@ApiImplicitParam(name = "term", value = "学期", required = true, dataType = "Integer"),
		@ApiImplicitParam(name = "addressArea", value = "地区", required = true, dataType = "Integer"),
		@ApiImplicitParam(name = "questionType", value = "题型", required = true, dataType = "Integer"),
		@ApiImplicitParam(name = "paperType", value = "试卷类型", required = true, dataType = "Integer"),
		@ApiImplicitParam(name = "ob", value = "排序字段:size,create_date", required = true, dataType = "String"),
		@ApiImplicitParam(name = "sb", value = "排序顺序 asc,desc", required = true, dataType = "String"),
		@ApiImplicitParam(name = "rows", value = "每页显示条数", required = true, dataType = "String"),
		@ApiImplicitParam(name = "page", value = "页数", required = true, dataType = "String") 
	})
	@RequestMapping(value = "findPaperByPage.do", method = RequestMethod.GET)
	public RestResult<PageBean> findPaperByPage(Integer rows,Integer page,Integer gradeLevelId,Integer term,Integer addressArea,Integer questionType,Integer paperType) {
		PageBean pageBean = initPageBean(page, rows);
		Map<String, Object> requestMap = new HashMap<String, Object>();
		Map<String, String[]> requestMap1 = request.getParameterMap();
		requestMap = requestParamToMap(requestMap1);
//		requestMap.put("gradeLevelId", gradeLevelId);
//		requestMap.put("term", term);
//		requestMap.put("addressArea", addressArea);
//		requestMap.put("questionType", questionType);
//		requestMap.put("paperType", paperType);
		paperService.findPaperByPage(requestMap, pageBean);
		return ok(pageBean);
	}
	
	@ApiOperation(value = "获取试卷详细信息", notes = "获取试卷信息信息方法")
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "paperId", value = "试卷id", required = true, dataType = "Long")
	})
	@RequestMapping(value = "getPaperDetail.do", method = RequestMethod.GET)
	public RestResult<Map<String, Object>> getPaperDetail(Long paperId){
		return paperService.getPaperDetail(paperId);
	}
	
}
