/**
 * 
 */
package com.rci.timer;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.rci.service.ISchemeService;
import com.rci.tools.DateUtil;

/**
 * remark (备注):
 *
 * @author zj
 *	
 * 项目名称：ReconciliationPro
 *
 * 类名称：StatusMonitor
 *
 * 包名称：com.rci.timer
 *
 * Create Time: 2015年10月4日 下午4:10:28
 *
 */
@Component("statusMonitor")
public class StatusMonitor {
	private static final Log logger = LogFactory.getLog(StatusMonitor.class);
	
	@Resource(name="SchemeService")
	private ISchemeService schemeService;
	
	public void monitorScheme(){
		logger.warn("------- start check scheme status -------");
		schemeService.checkStatus(DateUtil.getCurrentDate());
		logger.warn("------- end check scheme status -------");
	}
}
