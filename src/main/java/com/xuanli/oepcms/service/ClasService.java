package com.xuanli.oepcms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xuanli.oepcms.entity.ClasEntity;
import com.xuanli.oepcms.mapper.ClasEntityMapper;


@Service
@Transactional
public class ClasService {
	@Autowired
	private ClasEntityMapper clasDao;

	/**
	 * @Description:  TODO
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年1月16日 上午9:37:05
	 */
	public List<ClasEntity> selectClasEntity(ClasEntity clasEntity) {
		return clasDao.selectClasEntity(clasEntity);
	}
	

}
