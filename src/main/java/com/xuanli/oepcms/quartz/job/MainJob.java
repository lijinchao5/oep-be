package com.xuanli.oepcms.quartz.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainJob implements BaseJob{
	private static Logger _log = LoggerFactory.getLogger(MainJob.class);

	public MainJob() {

	}

	public void execute(JobExecutionContext context) throws JobExecutionException {
		_log.info("主要的定时任务!");
	}
}
