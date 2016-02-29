package com.rci.metadata.service;

import java.util.Date;
import java.util.List;

import com.rci.bean.entity.Dish;
import com.rci.bean.entity.Order;
import com.rci.ui.swing.vos.HangupTabelInfoVO;

public interface IDataTransformService {
	/**
	 * 
	 * Describle(描述)： 同步远程指定日期的订单信息
	 *
	 * 方法名称：transformOrderInfo
	 *
	 * 所在类名：IDataTransformService
	 *
	 * Create Time:2015年4月23日 下午4:45:00
	 *  
	 * @param sdate
	 */
	List<Order> transformOrderInfo(Date sdate);
	
	/**
	 * 
	 * Describle(描述)：【初始化菜品】同步远程所有菜品信息
	 *
	 * 方法名称：transformDishInfo
	 *
	 * 所在类名：IDataTransformService
	 *
	 * Create Time:2015年4月23日 下午4:44:28
	 *
	 */
	void transformDishInfo();
	
	/**
	 * 
	 * Describle(描述)：【初始化支付方式】
	 *
	 * 方法名称：transformPaymodeInfo
	 *
	 * 所在类名：IDataTransformService
	 *
	 * Create Time:2015年4月23日 下午4:57:23
	 *
	 */
	void transformPaymodeInfo();
	
	/**
	 * 
	 * Describle(描述)： 同步收银机支付方式
	 *
	 * 方法名称：transformPaymodeInfo
	 *
	 * 所在类名：IDataTransformService
	 *
	 * Create Time:2016年2月29日 下午5:46:06
	 *  
	 * @param paymode
	 */
	void transformPaymodeInfo(String paymode);
	/**
	 * 
	 * Describle(描述)：同步桌号信息
	 *
	 * 方法名称：transformTableInfo
	 *
	 * 所在类名：IDataTransformService
	 *
	 * Create Time:2015年6月18日 下午2:12:08
	 *
	 */
	void transformTableInfo();
	
	/**
	 * 
	 * Describle(描述)：增量同步菜品信息。 当远程数据库新增菜品后将新增的菜品信息同步到本地数据库
	 *
	 * 方法名称：transformDishInfo
	 *
	 * 所在类名：IDataTransformService
	 *
	 * Create Time:2015年6月18日 下午2:10:23
	 *  
	 * @param dishno
	 * @return
	 */
	public Dish transformDishInfo(String dishno);
	
	List<HangupTabelInfoVO> transformHangupTableInfo();
}
