package com.xuanli.oepcms.quartz.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xuanli.oepcms.config.ApplicationContextProvider;
import com.xuanli.oepcms.service.ReportService;

public class MainJob implements BaseJob{
	private static Logger _log = LoggerFactory.getLogger(MainJob.class);

	public MainJob() {

	}

	public void execute(JobExecutionContext context) throws JobExecutionException {
		ReportService reportService = ApplicationContextProvider.getApplicationContext().getBean(ReportService.class);
		System.out.println(reportService);
		_log.info("主要的定时任务!");
	}
}
