/**
 * 
 */
package com.xuanli.oepcms.thirdapp.sdk.xl.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xuanli.oepcms.config.SystemConfig;
import com.xuanli.oepcms.contents.ExceptionCode;
import com.xuanli.oepcms.controller.BaseController;
import com.xuanli.oepcms.entity.BookEntity;
import com.xuanli.oepcms.entity.UnitEntity;
import com.xuanli.oepcms.mapper.BookEntityMapper;
import com.xuanli.oepcms.thirdapp.sdk.xl.service.SyncUnitService;
import com.xuanli.oepcms.util.SyncUtil;
import com.xuanli.oepcms.vo.RestResult;

import io.swagger.annotations.ApiOperation;

/**
 * @author lijinchao
 * @date 2018年2月27日 下午5:05:40
 */
@RestController
@RequestMapping(value = "/syncBookDetail/")
public class SyncUnitController extends BaseController{
	@Autowired
	SystemConfig systemConfig;
	@Autowired
	SyncUnitService syncUintService;
	@Autowired
	BookEntityMapper bookEntityDao;
	
	@ApiOperation(value = "同步教材内容", notes = "同步教材内容方法")
	@RequestMapping(value = "syncBookDetails.do", method = RequestMethod.POST)
    public RestResult<String> syncBookDetails() {
		try {
			String result = null;
			List<BookEntity> bookEntities = bookEntityDao.selectBooks();
			for(BookEntity bookEntity : bookEntities) {
				Long cmsId = bookEntity.getCmsId();
				String unitBean = SyncUtil.sendPostUTF8(systemConfig.BOOK_CONTENT+"?bookId="+cmsId, null);
				Map<String,Object> ResultMap = JSONObject.parseObject(unitBean,Map.class);
				Map<String,Object> allMap = (Map<String,Object>)ResultMap.get("result");
		    	List<UnitEntity> unitEntities = (List<UnitEntity>)allMap.get("units");
		    	List<UnitEntity> unitBeans = JSONArray.parseArray(unitEntities.toString(), UnitEntity.class);
		    	result = syncUintService.getUnitBean(unitBeans);
			}
	    	if(result.equals("1")) {
	    		return okNoResult("同步教材内容成功");
	    	}else if(result.equals("0")) {
	    		return failed(ExceptionCode.ADD_BOOK_ERROR, "同步教材内容失败");
	    	}else {
	    		return failed(ExceptionCode.UNKNOW_CODE, "未知错误,请联系管理员");
	    	}
		} catch (Exception e) {
			e.printStackTrace();
			return failed(ExceptionCode.ADD_BOOK_ERROR, "同步教材内容失败!");
		}
    }
}
