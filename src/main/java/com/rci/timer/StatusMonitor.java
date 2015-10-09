/**
 * 
 */
package com.rci.timer;

import javax.annotation.Resource;

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
	
	@Resource(name="SchemeService")
	private ISchemeService schemeService;
	
	public void monitorScheme(){
		schemeService.checkStatus(DateUtil.getCurrentDate());
	}
}
