/**
 * 
 */
package com.rci.timer;

import java.util.Calendar;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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
@Component("fetchDataJob")
public class FetchDataJob {
	@Resource(name="DataLoaderService")
	private IDataLoaderService dataloadService;
	
	/**
	 * 
	 * Describle(描述)：每天上午10点到下午23点每隔半小时触发一次
	 *
	 * 方法名称：fetchData
	 *
	 * 所在类名：FetchDataJob
	 *
	 * Create Time:2015年7月17日 上午11:28:51
	 *
	 */
	@Scheduled(cron="0 0/30 10-23 * * ? ?")
	public void fetchData(){
		dataloadService.load(DateUtil.truncate(DateUtil.getCurrentDate(),Calendar.DATE));
	}
	
}
