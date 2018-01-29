/**
 * @fileName:  ClasController.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年1月16日 下午3:08:56
 */ 
package com.xuanli.oepcms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xuanli.oepcms.contents.ExceptionCode;
import com.xuanli.oepcms.entity.ClasEntity;
import com.xuanli.oepcms.service.ClasService;
import com.xuanli.oepcms.util.StringUtil;
import com.xuanli.oepcms.vo.RestResult;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/** 
 * @author  QiaoYu 
 */
@RestController()
//@RequestMapping(value = "/class", method = RequestMethod.POST)
@RequestMapping(value = "/class/")
public class ClasController extends BaseController{
	@Autowired
	ClasService clasService;
	
	
	@ApiOperation(value="创建班级", notes="增加班级方法")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "grade", value = "年级", required = true, dataType = "String"),
            @ApiImplicitParam(name = "name", value = "班级名称", required = true, dataType = "String")
    })
	@RequestMapping(value = "addClas.do", method = RequestMethod.POST)
	public RestResult<String> addClas(String grade,String name){
		if(StringUtil.isEmpty(grade)||StringUtil.isEmpty(name)) {
			return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE,"年级或班级名称不能为空");
		}
		ClasEntity clasEntity = new ClasEntity();
		clasEntity.setGrade(grade);
		clasEntity.setName(name);
		try {
			Long userId = getCurrentUser().getId();
			clasService.saveClas(clasEntity, userId);
			return ok("添加班级成功!");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("添加班级异常,请联系管理员.", e);
			return failed(ExceptionCode.UNKNOW_CODE, e.getMessage());
		}
	}
	
	/**
	 * @Description:  TODO
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年1月17日 上午9:57:58
	 */
	@ApiOperation(value="删除班级", notes="删除班级方法")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "clasId", value = "班级id", required = true, dataType = "Long"),
    })
	@RequestMapping(value = "updateClas.do", method = RequestMethod.DELETE)
	public RestResult<String> deleteClas(Long clasId){
		try {
			if(null==clasId) {
				return failed(ExceptionCode.PARAMETER_VALIDATE_ERROR_CODE,"请先选择班级");
			}
			//clasId = getCurrentUser().getId();
			clasService.updateClas(clasId);
			return ok("删除班级成功!");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("删除班级异常,请联系管理员",e);
			return failed(ExceptionCode.UNKNOW_CODE,e.getMessage());
		}
	}
}
