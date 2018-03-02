/**
 * @fileName:  HomeWorkJob.java 
 * @Description:  TODO
 * @CreateName:  codelion[QiaoYu]
 * @CreateDate:  2018年3月2日 下午3:37:53
 */ 
package com.xuanli.oepcms.quartz.job;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xuanli.oepcms.service.HomeworkService;
import com.xuanli.oepcms.service.ReportService;

/** 
 * @author  codelion[QiaoYu]
 */
@Service
public class HomeWorkJob implements BaseJob{

	/** 
	 * @CreateUser:codelion[QiaoYu]
	 * @CreateDate:2018年3月2日 下午3:39:22
	 */
	
	@Autowired
	HomeworkService homeworkService;
	@Autowired
	ReportService reportService;
	
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
		Long homeworkId = jobDataMap.getLong("homeworkId");
		reportService.generatorHomeworkReport(homeworkId, null);
	}
	
}
