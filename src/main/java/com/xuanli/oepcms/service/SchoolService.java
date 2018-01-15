/**
 * @fileName:  SchoolService.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年1月15日 下午5:00:36
 */
package com.xuanli.oepcms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xuanli.oepcms.entity.SchoolEntity;
import com.xuanli.oepcms.mapper.SchoolEntityMapper;

/**
 * @author QiaoYu
 */
@Service
public class SchoolService {
	@Autowired
	SchoolEntityMapper schoolEntityMapper;

	/**
	 * @Description:  TODO
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年1月15日 下午5:13:46
	 */
	public List<SchoolEntity> selectSchoolEntity(SchoolEntity schoolEntity) {
		return schoolEntityMapper.selectSchoolEntity(schoolEntity);
	}
}
