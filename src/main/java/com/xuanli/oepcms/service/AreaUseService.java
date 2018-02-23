/**
 * 
 */
package com.xuanli.oepcms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xuanli.oepcms.entity.AreaUseEntity;
import com.xuanli.oepcms.mapper.AreaUseEntityMapper;

/**
 * @author lijinchao
 * @date 2018年2月23日 下午5:18:16
 */
@Service
public class AreaUseService extends BaseService{
	@Autowired
	AreaUseEntityMapper areaUseEntityMapper;
	
	/**
	 * Title: saveAreaDate 
	 * Description:  新增区县使用时间方法
	 * @date 2018年2月23日 下午5:22:35
	 * @param areaUseEntity
	 * @return
	 */
	public int saveAreaDate(AreaUseEntity areaUseEntity) {
		return areaUseEntityMapper.insertAreaUseEntity(areaUseEntity);
	}
	
	/**
	 * Title: deleteAreaDate 
	 * Description:  删除区县使用时间
	 * @date 2018年2月23日 下午5:22:50
	 * @param id
	 * @return
	 */
	public int deleteAreaDate(AreaUseEntity areaUseEntity) {
		return areaUseEntityMapper.deleteAreaUseEntity(areaUseEntity.getId());
	}
}
