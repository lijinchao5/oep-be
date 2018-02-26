/**
 * @fileName:  PaperService.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年2月26日 上午11:48:45
 */
package com.xuanli.oepcms.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xuanli.oepcms.entity.UserEntity;
import com.xuanli.oepcms.mapper.PaperEntityMapper;
import com.xuanli.oepcms.util.PageBean;

/**
 * @author QiaoYu
 */
@Service
public class PaperService {
	@Autowired
	PaperEntityMapper paperEntityMapper;

	/**
	 * @Description: TODO
	 * @CreateName: QiaoYu
	 * @CreateDate: 2018年2月26日 上午11:59:39
	 */
	public void findPaperByPage(Map<String, Object> requestMap, PageBean pageBean) {
		int total = paperEntityMapper.findPaperByPageTotal(requestMap);
		pageBean.setTotal(total);
		requestMap.put("start", pageBean.getRowFrom());
		requestMap.put("end", pageBean.getPageSize());
		Map<String, Object> resultMap = paperEntityMapper.findPaperByPage(requestMap);
		pageBean.setRows(resultMap);
	}

}
