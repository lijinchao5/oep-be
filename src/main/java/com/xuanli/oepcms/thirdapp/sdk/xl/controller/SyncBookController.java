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
import com.xuanli.oepcms.entity.SectionDetail;
import com.xuanli.oepcms.entity.SectionEntity;
import com.xuanli.oepcms.service.BookService;
import com.xuanli.oepcms.thirdapp.sdk.xl.bean.BookBean;
import com.xuanli.oepcms.thirdapp.sdk.xl.bean.SectionBean;
import com.xuanli.oepcms.thirdapp.sdk.xl.bean.SectionDetailBean;
import com.xuanli.oepcms.thirdapp.sdk.xl.bean.UnitBean;
import com.xuanli.oepcms.thirdapp.sdk.xl.service.SyncBookService;
import com.xuanli.oepcms.util.SyncUtil;
import com.xuanli.oepcms.vo.RestResult;

import io.swagger.annotations.ApiOperation;

/**
 * @author lijinchao
 * @date 2018年2月26日 下午7:55:07
 */
@RestController
@RequestMapping(value = "/syncBook/")
public class SyncBookController extends BaseController{
	@Autowired
	SystemConfig systemConfig;
	@Autowired
	SyncBookService syncBookService;
	@Autowired
	BookService bookService;
	
	@ApiOperation(value = "同步教材", notes = "同步教材方法")
	@RequestMapping(value = "syncBook.do", method = RequestMethod.POST)
    public RestResult<String> syncBook() {
		try {
			List<UnitBean> unitBeans = null;
			List<SectionBean> sectionBeans = null;
			List<SectionDetailBean> sectionDetailBeans = null;
	    	String bookBean = SyncUtil.sendPostUTF8(systemConfig.BOOK_URL, null);
	    	Map<String,Object> resultMap = JSONObject.parseObject(bookBean,Map.class);
	    	List<BookBean> bookBeans = JSONArray.parseArray(resultMap.get("result").toString(), BookBean.class);
	    	
			List<BookEntity> bookEntities = bookService.selectBooks();
			for(BookEntity bookEntity : bookEntities) {
				Long cmsId = bookEntity.getCmsId();
				String unitBean = SyncUtil.sendPostUTF8(systemConfig.BOOK_CONTENT+"?bookId="+cmsId, null);
				Map<String,Object> unitMap = JSONObject.parseObject(unitBean,Map.class);
				Map<String,Object> allMap = (Map<String,Object>)unitMap.get("result");
		    	unitBeans = JSONArray.parseArray(allMap.get("units").toString(), UnitBean.class);
		    	sectionBeans = JSONArray.parseArray(allMap.get("sections").toString(), SectionBean.class);
		    	sectionDetailBeans = JSONArray.parseArray(allMap.get("sectiondetails").toString(), SectionDetailBean.class);
			}
	    	
	    	String result = syncBookService.getBookBean(bookBeans,unitBeans,sectionBeans,sectionDetailBeans);
	    	if(result.equals("1")) {
	    		return okNoResult("同步教材成功");
	    	}else if(result.equals("0")) {
	    		return failed(ExceptionCode.ADD_BOOK_ERROR, "同步教材失败");
	    	}else {
	    		return failed(ExceptionCode.UNKNOW_CODE, "未知错误,请联系管理员");
	    	}
		} catch (Exception e) {
			e.printStackTrace();
			return failed(ExceptionCode.ADD_BOOK_ERROR, "同步教材失败!");
		}
    }
}
