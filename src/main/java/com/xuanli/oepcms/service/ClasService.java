package com.xuanli.oepcms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xuanli.oepcms.mapper.ClasEntityMapper;


@Service
@Transactional
public class ClasService {
	@Autowired
	private ClasEntityMapper clasDao;
	

}
