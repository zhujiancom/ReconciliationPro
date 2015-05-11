package com.rci.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.dozer.Mapper;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.rci.bean.entity.Dish;
import com.rci.bean.entity.Order;
import com.rci.bean.entity.OrderAccountRef;
import com.rci.bean.entity.OrderItem;
import com.rci.contants.BusinessConstant;
import com.rci.service.IDishService;
import com.rci.service.IOrderAccountRefService;
import com.rci.service.IOrderService;
import com.rci.service.base.BaseService;
import com.rci.tools.DateUtil;
import com.rci.ui.swing.vos.OrderItemVO;
import com.rci.ui.swing.vos.OrderVO;

@Service("OrderService")
public class OrderServiceImpl extends BaseService<Order, Long> implements
		IOrderService {
	@Autowired
	private Mapper beanMapper;
	@Resource(name="OrderAccountRefService")
	private IOrderAccountRefService oaService;
	@Resource(name="DishService")
	private IDishService dishService;

	@Override
	public Order getOrder(Long pk) {
		return super.get(pk);
	}

	@Override
	public void rwInsertOrder(Order order) {
		rwCreate(order);
	}
	
	@Override
	public void rwUpdateOrder(Order order){
		rwUpdate(order);
	}

	@Override
	public List<Order> queryAllDayOrders() {
		return null;
	}

	@Override
	public List<Order> queryOrdersByDay(String day) {
		DetachedCriteria dc = DetachedCriteria.forClass(Order.class);
		dc.add(Restrictions.eq("day", day)).addOrder(
				org.hibernate.criterion.Order.asc("checkoutTime"));
		List<Order> orders = baseDAO.queryListByCriteria(dc);
		return orders;
	}

	@Override
	public List<OrderVO> accquireOrderVOsByDay(String day) {
		List<OrderVO> vos = new LinkedList<OrderVO>();
		List<Order> orders = queryOrdersByDay(day);
		if (!CollectionUtils.isEmpty(orders)) {
			for (Order order : orders) {
				OrderVO vo = beanMapper.map(order, OrderVO.class);
				List<OrderAccountRef> oaRefs = oaService.getOARef(order.getOrderNo());
				BigDecimal totalAmount = BigDecimal.ZERO;
				for (OrderAccountRef accountRef : oaRefs) {
					BigDecimal amount = accountRef.getRealAmount();
					totalAmount = totalAmount.add(amount);
					String accountNo = accountRef.getAccNo();
					if (BusinessConstant.CASHMACHINE_ACC.equals(accountNo)) {
						vo.setCashmachineAmount(amount);
					}
					if (BusinessConstant.MT_ACC.equals(accountNo)) {
						vo.setMtAmount(amount);
					}
					if (BusinessConstant.DPTG_ACC.equals(accountNo)) {
						vo.setDptgAmount(amount);
					}
					if (BusinessConstant.ELE_ACC.equals(accountNo)) {
						vo.setEleAmount(amount);
					}
					if (BusinessConstant.TDD_ACC.equals(accountNo)) {
						vo.setTddAmount(amount);
					}
					if (BusinessConstant.MTWM_ACC.equals(accountNo)) {
						vo.setMtwmAmount(amount);
					}
					if (BusinessConstant.FREE_ELE_ACC.equals(accountNo)) {
						vo.setEleFreeAmount(amount);
					}
					if (BusinessConstant.FREE_MTWM_ACC.equals(accountNo)) {
						vo.setMtwmFreeAmount(amount);
					}
					if (BusinessConstant.MT_SUPER_ACC.equals(accountNo)){
						vo.setMtSuperAmount(amount);
					}
					if (BusinessConstant.FREE_MT_SUPER_ACC.equals(accountNo)){
						vo.setMtSuperFreeAmount(amount);
					}
					if (BusinessConstant.FREE_ACC.equals(accountNo)) {
						vo.setFreeAmount(amount);
						totalAmount = totalAmount.subtract(amount);
					}
				}
				vo.setSchemeName(order.getSchemeName());
				vo.setTotalAmount(totalAmount);
				vos.add(vo);
			}
		}
		return vos;
	}

	@Override
	public List<OrderItemVO> queryOrderItemVOsByPayno(String payno) {
		DetachedCriteria dc = DetachedCriteria.forClass(Order.class);
		dc.add(Restrictions.eq("payNo", payno));
		Order order = baseDAO.queryUniqueByCriteria(dc);
		List<OrderItem> items = order.getItems();
		List<OrderItemVO> vos = new ArrayList<OrderItemVO>();
		if (!CollectionUtils.isEmpty(items)) {
			for (OrderItem item : items) {
				OrderItemVO vo = beanMapper.map(item, OrderItemVO.class);
				Dish dish = dishService.findDishByNo(item.getDishNo());
				vo.setDishName(dish.getDishName());
				vos.add(vo);
			}
		}
		return vos;
	}

	@Override
	public void rwDeleteOrders(Order[] orders) {
		baseDAO.delete(orders);
	}

	@Override
	public void rwDeleteOrders(String day) {
		List<Order> orders = queryOrdersByDay(day);
		baseDAO.delete(orders.toArray(new Order[0]));
		try {
			oaService.rwDeleteOar(DateUtil.parseDate(day, "yyyyMMdd"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void rwInsertOrders(Order[] orders) {
		rwCreate(orders);
	}
}
