package com.rci.service;

import java.math.BigDecimal;
import java.util.List;

import com.rci.bean.entity.Order;
import com.rci.ui.swing.vos.OrderItemVO;
import com.rci.ui.swing.vos.OrderVO;

public interface IOrderService {
	Order getOrder(Long pk);
	
	void rwInsertOrder(Order order);
	
	void rwInsertOrders(Order[] orders);
	
	void rwDeleteOrders(Order[] orders);
	
	void rwDeleteOrders(String day);
	
	void rwUpdateOrder(Order order);
	
	List<Order> queryAllDayOrders();
	
	List<Order> queryOrdersByDay(String day);
	
	List<OrderVO> accquireOrderVOsByDay(String day);
	
	List<OrderItemVO> queryOrderItemVOsByPayno(String payno);
	
	/**
	 * 
	 * Describle(描述)：获取当日总单数
	 *
	 * 方法名称：getOrderCountByDay
	 *
	 * 所在类名：IOrderService
	 *
	 * Create Time:2015年6月19日 下午5:03:31
	 *  
	 * @param day
	 * @return
	 */
	BigDecimal getOrderCountByDay(String day);
	
	/**
	 * 
	 * Describle(描述)：获取当日外送单数
	 *
	 * 方法名称：getExpressOrderCountByDay
	 *
	 * 所在类名：IOrderService
	 *
	 * Create Time:2015年6月19日 下午5:03:49
	 *  
	 * @param day
	 * @return
	 */
	BigDecimal getExpressOrderCountByDay(String day);
}
