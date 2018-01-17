/**
 * @fileName:  ClasController.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年1月16日 下午3:08:56
 */ 
package com.xuanli.oepcms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xuanli.oepcms.contents.ExceptionCode;
import com.xuanli.oepcms.entity.ClasEntity;
import com.xuanli.oepcms.service.ClasService;
import com.xuanli.oepcms.vo.RestResult;

/** 
 * @author  QiaoYu 
 */
@RestController()
//@RequestMapping(value = "/class", method = RequestMethod.POST)
@RequestMapping(value = "/class/")
public class ClasController extends BaseController{
	@Autowired
	ClasService clasService;
	
	
	
	@RequestMapping(value = "addClas.do")
	public RestResult<String> addClas(ClasEntity clasEntity){
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
	@RequestMapping(value = "updateClas.do")
	public RestResult<String> deleteClas(Long clasId){
		try {
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
