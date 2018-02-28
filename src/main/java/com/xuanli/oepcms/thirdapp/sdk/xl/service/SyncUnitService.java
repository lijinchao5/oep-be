/**
 * 
 */
package com.xuanli.oepcms.thirdapp.sdk.xl.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xuanli.oepcms.entity.UnitEntity;
import com.xuanli.oepcms.mapper.UnitEntityMapper;
import com.xuanli.oepcms.service.BaseService;

/**
 * @author lijinchao
 * @date 2018年2月27日 下午4:46:20
 */
@Service
@Transactional
public class SyncUnitService extends BaseService{
	
	@Autowired
	UnitEntityMapper unitEntityDao;
	
	/**Title: getUnitBean 
	 * Description:  
	 * @date 2018年2月27日 下午4:29:23
	 * @param unitEntities
	 * @return  
	 */
	public String getUnitBean(List<UnitEntity> unitBeans) {
		try {
			for(UnitEntity unitBean:unitBeans) {
				UnitEntity unitEntity = new UnitEntity();
				unitEntity.setCmsId(unitBean.getId());
				unitEntity.setName(unitBean.getName());
				unitEntity.setBookId(unitBean.getBookId());
				unitEntity.setCreateId(unitBean.getCreateId());
				unitEntity.setCreateDate(unitBean.getCreateDate());
				unitEntity.setUpdateId(unitBean.getUpdateId());
				unitEntity.setUpdateDate(unitBean.getCreateDate());
				unitEntity.setEnableFlag(unitBean.getEnableFlag());
				UnitEntity syncunitEntity = unitEntityDao.selectByCmsId(unitBean.getId());
				if(syncunitEntity!=null) {
					unitEntityDao.updateSyncUnitEntity(unitEntity);
				}else {
					unitEntityDao.insertUnitEntity(unitEntity);
				}
			}
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}
}
