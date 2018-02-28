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
import com.xuanli.oepcms.entity.UnitEntity;
import com.xuanli.oepcms.mapper.BookEntityMapper;
import com.xuanli.oepcms.mapper.SectionDetailMapper;
import com.xuanli.oepcms.mapper.SectionEntityMapper;
import com.xuanli.oepcms.mapper.UnitEntityMapper;
import com.xuanli.oepcms.service.BookService;
import com.xuanli.oepcms.thirdapp.sdk.xl.bean.BookBean;
import com.xuanli.oepcms.thirdapp.sdk.xl.bean.SectionBean;
import com.xuanli.oepcms.thirdapp.sdk.xl.bean.SectionDetailBean;
import com.xuanli.oepcms.thirdapp.sdk.xl.bean.SyncBookBean;
import com.xuanli.oepcms.thirdapp.sdk.xl.bean.SyncBookDetailBean;
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
public class SyncBookController extends BaseController {
	@Autowired
	SystemConfig systemConfig;
	@Autowired
	SyncBookService syncBookService;
	@Autowired
	BookService bookService;

	@Autowired
	BookEntityMapper bookDao;
	@Autowired
	UnitEntityMapper unitEntityDao;
	@Autowired
	SectionEntityMapper sectionEntityDao;
	@Autowired
	SectionDetailMapper sectionDetailDao;

	@ApiOperation(value = "同步教材", notes = "同步教材方法")
	@RequestMapping(value = "syncBook.do", method = RequestMethod.POST)
	public RestResult<String> syncBook() {
		try {
			List<UnitBean> unitBeans = null;
			List<SectionBean> sectionBeans = null;
			List<SectionDetailBean> sectionDetailBeans = null;
			String bookBean = SyncUtil.sendPostUTF8(systemConfig.BOOK_URL, null);
			Map<String, Object> resultMap = JSONObject.parseObject(bookBean, Map.class);
			List<BookBean> bookBeans = JSONArray.parseArray(resultMap.get("result").toString(), BookBean.class);
			List<BookEntity> bookEntities = bookService.selectBooks();
			for (BookEntity bookEntity : bookEntities) {
				Long cmsId = bookEntity.getCmsId();
				String unitBean = SyncUtil.sendPostUTF8(systemConfig.BOOK_CONTENT + "?bookId=" + cmsId, null);
				Map<String, Object> unitMap = JSONObject.parseObject(unitBean, Map.class);
				Map<String, Object> allMap = (Map<String, Object>) unitMap.get("result");
				unitBeans = JSONArray.parseArray(allMap.get("units").toString(), UnitBean.class);
				sectionBeans = JSONArray.parseArray(allMap.get("sections").toString(), SectionBean.class);
				sectionDetailBeans = JSONArray.parseArray(allMap.get("sectiondetails").toString(), SectionDetailBean.class);
			}

			for (BookBean bb2 : bookBeans) {
				String unitBean = SyncUtil.sendPostUTF8(systemConfig.BOOK_CONTENT + "?bookId=" + bb2.getId(), null);
				Map<String, Object> unitMap = JSONObject.parseObject(unitBean, Map.class);
				Map<String, Object> allMap = (Map<String, Object>) unitMap.get("result");
				unitBeans = JSONArray.parseArray(allMap.get("units").toString(), UnitBean.class);
				sectionBeans = JSONArray.parseArray(allMap.get("sections").toString(), SectionBean.class);
				sectionDetailBeans = JSONArray.parseArray(allMap.get("sectiondetails").toString(), SectionDetailBean.class);
			}

			String result = syncBookService.getBookBean(bookBeans, unitBeans, sectionBeans, sectionDetailBeans);
			if (result.equals("1")) {
				return okNoResult("同步教材成功");
			} else if (result.equals("0")) {
				return failed(ExceptionCode.ADD_BOOK_ERROR, "同步教材失败");
			} else {
				return failed(ExceptionCode.UNKNOW_CODE, "未知错误,请联系管理员");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return failed(ExceptionCode.ADD_BOOK_ERROR, "同步教材失败!");
		}
	}

	@ApiOperation(value = "同步教材", notes = "同步教材方法")
	@RequestMapping(value = "syncBook1.do", method = RequestMethod.GET)
	public RestResult<String> syncBook1() {
		try {
			String bookjson = SyncUtil.sendPostUTF8(systemConfig.BOOK_URL, null);
			SyncBookBean syncBookBean = JSONObject.parseObject(bookjson, SyncBookBean.class);
			if (null != syncBookBean && syncBookBean.getCode() == 0) {
				List<BookBean> bookBeans = syncBookBean.getResult();
				if (null != bookBeans) {
					for (BookBean bookBean : bookBeans) {
						BookEntity bookEntity = new BookEntity();
						bookEntity.setId(bookBean.getId());
						bookEntity.setName(bookBean.getName());
						bookEntity.setGrade(bookBean.getGrade());
						bookEntity.setBookVersion(bookBean.getBookVersion());
						bookEntity.setBookVolume(bookBean.getBookVolume());
						bookEntity.setCreateId(bookBean.getCreateId());
						bookEntity.setCreateDate(bookBean.getCreateDate());
						bookEntity.setUpdateId(bookBean.getUpdateId());
						bookEntity.setUpdateDate(bookBean.getUpdateDate());
						bookEntity.setEnableFlag(bookBean.getEnableFlag());
						BookEntity resultBookEntity = bookDao.selectById(bookEntity.getId());
						if (resultBookEntity != null) {
							bookDao.updateSyncBookEntity(bookEntity);
						} else {
							bookDao.insertBookEntity(bookEntity);
						}
						String bookDetailJson = SyncUtil.sendPostUTF8(systemConfig.BOOK_CONTENT + "?bookId=" + bookBean.getId().longValue(), null);
						SyncBookDetailBean syncBookDetailBean = JSONObject.parseObject(bookDetailJson, SyncBookDetailBean.class);
						if (null != syncBookDetailBean && syncBookDetailBean.getCode() == 0) {
							// 更新unit内容
							for (UnitBean unitBean : syncBookDetailBean.getResult().getUnits()) {
								UnitEntity unitEntity = new UnitEntity();
								unitEntity.setCmsId(unitBean.getId());
								unitEntity.setName(unitBean.getName());
								unitEntity.setCreateId(unitBean.getCreateId());
								unitEntity.setCreateDate(unitBean.getCreateDate());
								unitEntity.setUpdateId(unitBean.getUpdateId());
								unitEntity.setUpdateDate(unitBean.getCreateDate());
								unitEntity.setEnableFlag(unitBean.getEnableFlag());
								unitEntity.setBookId(unitBean.getBookId());
								UnitEntity syncUnitEntity = unitEntityDao.selectById(unitBean.getId());
								if (syncUnitEntity != null) {
									unitEntityDao.updateSyncUnitEntity(unitEntity);
								} else {
									unitEntityDao.insertUnitEntity(unitEntity);
								}
							}
							for (SectionBean sectionBean : syncBookDetailBean.getResult().getSections()) {
								SectionEntity sectionEntity = new SectionEntity();
								sectionEntity.setCmsId(sectionBean.getId());
								sectionEntity.setName(sectionBean.getName());
								sectionEntity.setUnitId(sectionBean.getUnitId());
								sectionEntity.setCreateId(sectionBean.getCreateId());
								sectionEntity.setCreateDate(sectionBean.getCreateDate());
								sectionEntity.setUpdateId(sectionBean.getUpdateId());
								sectionEntity.setUpdateDate(sectionBean.getUpdateDate());
								sectionEntity.setEnableFlag(sectionBean.getEnableFlag());
								SectionEntity syncSectionEntity = sectionEntityDao.selectById(sectionBean.getId());
								if (syncSectionEntity != null) {
									sectionEntityDao.updateSyncSectionEntity(sectionEntity);
								} else {
									sectionEntityDao.insertSectionEntity(sectionEntity);
								}
							}
							// 更新教材详情
							for (SectionDetailBean sectionDetailBean : syncBookDetailBean.getResult().getSectiondetails()) {
								SectionDetail sectionDetail = new SectionDetail();
								sectionDetail.setCmsId(sectionDetailBean.getId());
								sectionDetail.setName(sectionDetail.getName());
								sectionDetail.setPersonName(sectionDetailBean.getPersonName());
								sectionDetail.setSectionId(sectionDetailBean.getSectionId());
								sectionDetail.setType(Integer.parseInt(sectionDetailBean.getType()));
								sectionDetail.setText(sectionDetailBean.getText());
								sectionDetail.setAudioPath(sectionDetailBean.getAudioPath());
								sectionDetail.setCreateId(sectionDetailBean.getCreateId());
								sectionDetail.setCreateDate(sectionDetailBean.getCreateDate());
								sectionDetail.setUpdateId(sectionDetailBean.getUpdateId());
								sectionDetail.setUpdateDate(sectionDetailBean.getUpdateDate());
								sectionDetail.setEnableFlag(sectionDetailBean.getEnableFlag());
								sectionDetail.setmAudioPath(sectionDetailBean.getmAudioPath());
								sectionDetail.setwAudioPath(sectionDetailBean.getwAudioPath());
								sectionDetail.setPicturePath(sectionDetailBean.getPicturePath());
								sectionDetail.setWordType(sectionDetailBean.getWordType());
								sectionDetail.setWordMean(sectionDetailBean.getWordMean());
								sectionDetail.setPointType(sectionDetailBean.getPointType());
								sectionDetail.setOrderNum(sectionDetailBean.getOrderNum());
								sectionDetail.setDialogNum(sectionDetailBean.getDialogNum());
								SectionDetail syncSectionDetail = sectionDetailDao.selectById(sectionDetailBean.getId());
								if (syncSectionDetail != null) {
									sectionDetailDao.updateSyncSectionDetail(sectionDetail);
								} else {
									sectionDetailDao.insertSectionDetail(sectionDetail);
								}
							}
						}

					}
				} else {
					System.out.println("bookbean是空的!");
				}
				return okNoResult("成功");
			} else {
				// 失败
				return failed(ExceptionCode.ADD_BOOK_ERROR, "失败");
			}

		} catch (Exception e) {
			e.printStackTrace();
			return failed(ExceptionCode.ADD_BOOK_ERROR, "同步教材失败!");
		}
	}

}
