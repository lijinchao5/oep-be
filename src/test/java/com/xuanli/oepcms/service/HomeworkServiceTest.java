/**
 * @fileName:  HomeworkServiceTest.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年1月22日 下午4:56:25
 */ 
package com.xuanli.oepcms.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.xuanli.oepcms.BaseTest;

/** 
 * @author  QiaoYu 
 */
public class HomeworkServiceTest extends BaseTest{
	@Autowired
	HomeworkService homeworkService;
	@Test
	public void test() {
		homeworkService.test(1, 3);
	}
}
