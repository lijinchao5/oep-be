/**
 * 
 */
package com.xuanli.oepcms.thirdapp.sdk.xl.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xuanli.oepcms.entity.BookEntity;
import com.xuanli.oepcms.entity.SectionDetail;
import com.xuanli.oepcms.entity.SectionEntity;
import com.xuanli.oepcms.entity.UnitEntity;
import com.xuanli.oepcms.mapper.BookEntityMapper;
import com.xuanli.oepcms.mapper.SectionDetailMapper;
import com.xuanli.oepcms.mapper.SectionEntityMapper;
import com.xuanli.oepcms.mapper.UnitEntityMapper;
import com.xuanli.oepcms.service.BaseService;
import com.xuanli.oepcms.thirdapp.sdk.xl.bean.BookBean;
import com.xuanli.oepcms.thirdapp.sdk.xl.bean.SectionBean;
import com.xuanli.oepcms.thirdapp.sdk.xl.bean.SectionDetailBean;
import com.xuanli.oepcms.thirdapp.sdk.xl.bean.UnitBean;

/**
 * @author lijinchao
 * @date 2018年2月26日 下午9:03:05
 */
@Service
@Transactional
public class SyncBookService extends BaseService{

	@Autowired
	BookEntityMapper bookDao;
	@Autowired
	UnitEntityMapper unitEntityDao;
	@Autowired
	SectionEntityMapper sectionEntityDao;
	@Autowired
	SectionDetailMapper sectionDetailDao;

	/**Title: getBookBean 
	 * Description:  
	 * @param sectionDetailBeans 
	 * @param sectionBeans 
	 * @date 2018年2月26日 下午9:06:57  
	 */
	public String getBookBean(List<BookBean> bookBeans,List<UnitBean> unitBeans, List<SectionBean> sectionBeans, List<SectionDetailBean> sectionDetailBeans) {
    	try {
    		for (BookBean bookBean : bookBeans) {
    			BookEntity bookEntity = new BookEntity();
    			bookEntity.setCmsId(bookBean.getId());
    			bookEntity.setName(bookBean.getName());
    			bookEntity.setGrade(bookBean.getGrade());
    			bookEntity.setBookVersion(bookBean.getBookVersion());
    			bookEntity.setBookVolume(bookBean.getBookVolume());
    			bookEntity.setCreateId(bookBean.getCreateId());
    			bookEntity.setCreateDate(bookBean.getCreateDate());
    			bookEntity.setUpdateId(bookBean.getUpdateId());
    			bookEntity.setUpdateDate(bookBean.getUpdateDate());
    			bookEntity.setEnableFlag(bookBean.getEnableFlag());
    			BookEntity syncBookEntity = bookDao.selectByCmsId(bookBean.getId());
    			if(syncBookEntity!=null) {
    				bookDao.updateSyncBookEntity(bookEntity);
    			}else {
    				bookDao.insertBookEntity(bookEntity);
    				//更新教材内容
    				for(UnitBean unitBean:unitBeans) {
    					UnitEntity unitEntity = new UnitEntity();
    					unitEntity.setCmsId(unitBean.getId());
    					unitEntity.setName(unitBean.getName());
    					unitEntity.setBookId(unitBean.getBookId());
    					unitEntity.setCreateId(unitBean.getCreateId());
    					unitEntity.setCreateDate(unitBean.getCreateDate());
    					unitEntity.setUpdateId(unitBean.getUpdateId());
    					unitEntity.setUpdateDate(unitBean.getCreateDate());
    					unitEntity.setEnableFlag(unitBean.getEnableFlag());
    					UnitEntity syncUnitEntity = unitEntityDao.selectByCmsId(unitBean.getId());
    					if(syncUnitEntity!=null) {
    						unitEntityDao.updateSyncUnitEntity(unitEntity);
    					}else {
    						unitEntityDao.insertUnitEntity(unitEntity);
    					}
    				}
    				//更新章节内容
    				for(SectionBean sectionBean:sectionBeans) {
    					SectionEntity sectionEntity = new SectionEntity();
    					sectionEntity.setCmsId(sectionBean.getId());
    					sectionEntity.setName(sectionBean.getName());
    					sectionEntity.setUnitId(sectionBean.getUnitId());
    					sectionEntity.setCreateId(sectionBean.getCreateId());
    					sectionEntity.setCreateDate(sectionBean.getCreateDate());
    					sectionEntity.setUpdateId(sectionBean.getUpdateId());
    					sectionEntity.setUpdateDate(sectionBean.getUpdateDate());
    					sectionEntity.setEnableFlag(sectionBean.getEnableFlag());
    					SectionEntity syncSectionEntity = sectionEntityDao.selectByCmsId(sectionBean.getId());
    					if(syncSectionEntity!=null) {
    						sectionEntityDao.updateSyncSectionEntity(sectionEntity);
    					}else {
    						sectionEntityDao.insertSectionEntity(sectionEntity);
    					}
    				}
    				//更新教材详情
    				for(SectionDetailBean sectionDetailBean:sectionDetailBeans) {
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
    					SectionDetail syncSectionDetail = sectionDetailDao.selectByCmsId(sectionDetailBean.getId());
    					if(syncSectionDetail!=null) {
    						sectionDetailDao.updateSyncSectionDetail(sectionDetail);
    					}else {
    						sectionDetailDao.insertSectionDetail(sectionDetail);
    					}
    				}
    			}
    		}
    		return "1";
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}
}
