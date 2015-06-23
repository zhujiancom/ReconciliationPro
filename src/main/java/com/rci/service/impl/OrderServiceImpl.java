package com.rci.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.dozer.Mapper;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.rci.bean.entity.Dish;
import com.rci.bean.entity.Order;
import com.rci.bean.entity.OrderAccountRef;
import com.rci.bean.entity.OrderItem;
import com.rci.contants.BusinessConstant;
import com.rci.metadata.service.IDataTransformService;
import com.rci.service.IDishService;
import com.rci.service.IOrderAccountRefService;
import com.rci.service.IOrderService;
import com.rci.service.base.BaseServiceImpl;
import com.rci.tools.DateUtil;
import com.rci.ui.swing.vos.OrderItemVO;
import com.rci.ui.swing.vos.OrderVO;

@Service("OrderService")
public class OrderServiceImpl extends BaseServiceImpl<Order, Long> implements
		IOrderService {
	@Autowired
	private Mapper beanMapper;
	@Resource(name="OrderAccountRefService")
	private IOrderAccountRefService oaService;
	@Resource(name="DishService")
	private IDishService dishService;
	@Resource(name="DataTransformService")
	private IDataTransformService transformService;

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
					if (BusinessConstant.POS_ACC.equals(accountNo)) {
						vo.setPosAmount(amount);
					}
					if (BusinessConstant.MT_ACC.equals(accountNo)) {
						vo.setMtAmount(amount);
					}
					if (BusinessConstant.DPTG_ACC.equals(accountNo)) {
						vo.setDptgAmount(amount);
					}
					if (BusinessConstant.DPSH_ACC.equals(accountNo)) {
						vo.setDpshAmount(amount);
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
				if(dish == null){
					dish = transformService.transformDishInfo(item.getDishNo());
				}
				vo.setDishName(dish.getDishName());
				vos.add(vo);
			}
		}
		return vos;
	}

	@Override
	public void deleteOrders(String day) {
		List<Order> orders = queryOrdersByDay(day);
		for(Order order:orders){
			((IOrderService)AopContext.currentProxy()).rwDelete(order.getOid());
		}
		try {
			oaService.deleteOar(DateUtil.parseDate(day, "yyyyMMdd"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Override
	public BigDecimal getOrderCountByDay(String day) {
		DetachedCriteria dc = DetachedCriteria.forClass(Order.class);
		dc.setProjection(Projections.rowCount()).add(Restrictions.eq("day", day));
		Long count =  baseDAO.getRowCount(dc);
		return new BigDecimal(count);
	}

	@Override
	public BigDecimal getExpressOrderCountByDay(String day) {
		String sql = "SELECT COUNT(1) as 'count' FROM bus_tb_order o LEFT JOIN bus_tb_table t ON o.table_no=t.table_no \n"
				+ "WHERE o.day='"+day+"' \n"
				+ "AND t.table_type='03'";
		List<Map<String,Object>> countMapList =  baseDAO.queryListBySQL(sql);
		Long count = (Long) countMapList.get(0).get("count");
		return new BigDecimal(count);
	}
}
