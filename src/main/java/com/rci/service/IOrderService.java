package com.rci.service;

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
	
	List<Order> queryAllDayOrders();
	
	List<Order> queryOrdersByDay(String day);
	
	List<OrderVO> queryOrderVOsByDay(String day);
	
	List<OrderItemVO> queryOrderItemVOsByPayno(String payno);
}
