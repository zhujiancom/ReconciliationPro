/**
 * 
 */
package com.rci.timer;

import javax.annotation.Resource;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.rci.service.IDataLoaderService;
import com.rci.tools.DateUtil;

/**
 * remark (备注):每隔30分钟从sqlserver 数据库中fetch 数据
 *
 * @author zj
 *	
 * 项目名称：ReconciliationPro
 *
 * 类名称：FetchDataMonitor
 *
 * 包名称：com.rci.timer
 *
 * Create Time: 2015年7月15日 下午11:16:28
 *
 */
public class FetchDataMonitor extends QuartzJobBean {
	@Resource(name="DataLoaderService")
	private IDataLoaderService dataloadService;

	/* 
	 * @see org.springframework.scheduling.quartz.QuartzJobBean#executeInternal(org.quartz.JobExecutionContext)
	 */
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		dataloadService.load(DateUtil.getCurrentDate());
	}

}
