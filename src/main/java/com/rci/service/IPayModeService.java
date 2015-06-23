package com.rci.service;

import com.rci.bean.entity.Paymode;
import com.rci.service.base.IBaseService;

public interface IPayModeService extends IBaseService<Paymode, Long>{

	/**
	 * 
	 * Describle(描述)： 根据支付编号获取支付方式
	 *
	 * 方法名称：getPayModeByNo
	 *
	 * 所在类名：IPayModeService
	 *
	 * Create Time:2015年6月23日 上午10:44:19
	 *  
	 * @param pmno
	 * @return
	 */
	public Paymode getPayModeByNo(String pmno);

	/**
	 * 
	 * Describle(描述)： 删除所有支付方式
	 *
	 * 方法名称：deleteAll
	 *
	 * 所在类名：IPayModeService
	 *
	 * Create Time:2015年6月23日 上午10:44:44
	 *
	 */
	void deleteAll();
}
