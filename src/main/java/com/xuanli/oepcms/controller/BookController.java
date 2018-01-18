/**
 * @fileName:  BookController.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年1月18日 上午9:29:20
 */ 
package com.xuanli.oepcms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xuanli.oepcms.contents.ExceptionCode;
import com.xuanli.oepcms.entity.BookEntity;
import com.xuanli.oepcms.entity.SectionDetail;
import com.xuanli.oepcms.entity.SectionEntity;
import com.xuanli.oepcms.entity.UnitEntity;
import com.xuanli.oepcms.service.BookSectionDetailService;
import com.xuanli.oepcms.service.BookSectionService;
import com.xuanli.oepcms.service.BookService;
import com.xuanli.oepcms.service.BookUnitService;
import com.xuanli.oepcms.vo.RestResult;

/** 
 * @author  QiaoYu 
 */
@RestController()
//@RequestMapping(value = "/book/", method = RequestMethod.POST)
@RequestMapping(value = "/book/")
public class BookController extends BaseController{
	@Autowired
	BookService bookService;
	@Autowired
	BookUnitService bookUnitService;
	@Autowired
	BookSectionService bookSectionService;
	@Autowired
	BookSectionDetailService bookSectionDetailService;
	
	/**
	 * @Description:  TODO 查询课本信息
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年1月18日 上午9:50:50
	 */
	@RequestMapping(value = "getBooks.do")
	public RestResult<List<BookEntity>> getBooks(BookEntity bookEntity){
		try {
			List<BookEntity> bookEntities = bookService.getBookEntity(bookEntity);
			return ok(bookEntities);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询书籍信息出现异常.", e);
			return failed(ExceptionCode.UNKNOW_CODE, e.getMessage());
		}
	}
	
	
	
	/**
	 * @Description:  TODO 查询单元信息
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年1月18日 上午9:50:37
	 */
	@RequestMapping(value = "getUnits.do")
	public RestResult<List<UnitEntity>> getUnits(UnitEntity unitEntity){
		try {
			List<UnitEntity> unitEntities = bookUnitService.getUnitEntity(unitEntity);
			return ok(unitEntities);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询单元信息出现异常.", e);
			return failed(ExceptionCode.UNKNOW_CODE, e.getMessage());
		}
	}
	
	/**
	 * @Description:  TODO 查询单元详细信息
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年1月18日 上午9:50:37
	 */
	@RequestMapping(value = "getSections.do")
	public RestResult<List<SectionEntity>> getSections(SectionEntity sectionEntity){
		try {
			List<SectionEntity> sectionEntities = bookSectionService.getSectionEntity(sectionEntity);
			return ok(sectionEntities);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询单元详细信息出现异常.", e);
			return failed(ExceptionCode.UNKNOW_CODE, e.getMessage());
		}
	}
	
	/**
	 * @Description:  TODO 获取题目信息
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年1月18日 上午10:16:41
	 */
	@RequestMapping(value = "getSectionDetail.do")
	public RestResult<List<SectionDetail>> getSectionDetail(SectionDetail sectionDetail){
		try {
			List<SectionDetail> sectionDetails = bookSectionDetailService.getSectionDetail(sectionDetail);
			return ok(sectionDetails);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询题目详细信息出现异常.", e);
			return failed(ExceptionCode.UNKNOW_CODE, e.getMessage());
		}
	}
	
	
	
	
	
	
}
