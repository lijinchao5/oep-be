/**
 * @fileName:  DicController.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年1月30日 上午9:40:49
 */ 
package com.xuanli.oepcms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xuanli.oepcms.contents.ExceptionCode;
import com.xuanli.oepcms.entity.DicDetailEntity;
import com.xuanli.oepcms.service.DicService;
import com.xuanli.oepcms.util.StringUtil;
import com.xuanli.oepcms.vo.RestResult;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/** 
 * @author  QiaoYu 
 */
@RestController()
@RequestMapping(value = "/dic/")
public class DicController extends BaseController{
	@Autowired
	DicService dicService;
	@ApiOperation(value="获取字典内容", notes="获取字典内容方法")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "类型", required = true, dataType = "String")
    })
	@RequestMapping(value = "findDicByType.do", method = RequestMethod.GET)
	public RestResult<List<DicDetailEntity>> findDicByType(String type){
		if (StringUtil.isEmpty(type)) {
			return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE, "type不能为空");
		}
		return ok(dicService.findDicByType(type));
	}
}
