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
import com.rci.bean.entity.account.Account;
import com.rci.enums.BusinessEnums.AccountCode;
import com.rci.enums.CommonEnums.Symbol;
import com.rci.metadata.NativeSQLBuilder;
import com.rci.metadata.service.IDataTransformService;
import com.rci.service.IAccountService;
import com.rci.service.IDishService;
import com.rci.service.IOrderAccountRefService;
import com.rci.service.IOrderService;
import com.rci.service.base.BaseServiceImpl;
import com.rci.tools.DateUtil;
import com.rci.tools.StringUtils;
import com.rci.ui.swing.vos.OrderItemVO;
import com.rci.ui.swing.vos.OrderVO;

@Service("OrderService")
public class OrderServiceImpl extends BaseServiceImpl<Order, Long> implements
		IOrderService {
	@Autowired
	private Mapper beanMapper;
	
	@Resource(name="AccountService")
	private IAccountService accountService;
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
//		for(Order order:orders){//解决lazy问题，强制加载关联属性
//			Hibernate.initialize(order.getItems());
//		}
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
					String accountNo = accountRef.getAccNo();
					AccountCode code = AccountCode.valueOf(accountNo);
					Account account = accountService.getAccount(accountNo);
					if(AccountCode.FREE_ONLINE.equals(code)){
						vo.setOnlineFreeAmount(amount);
						continue;
					}
					if(AccountCode.FREE_OFFLINE.equals(code)){
						vo.setFreeAmount(amount);
					}
					if(Symbol.P.equals(account.getSymbol())){  //是正数账户
						totalAmount = totalAmount.add(amount);
					}
				}
				vo.setSchemeName(order.getSchemeName());
				vo.setTotalAmount(totalAmount);
				vo.setPaymodecodes(StringUtils.split(order.getPaymodes(),','));
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
				if(dish != null){
					vo.setDishName(dish.getDishName());
				}else{
					vo.setDishName(item.getDishNo());
				}
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
	public void doDeleteOrders(String day) {
		List<Order> orders = queryOrdersByDay(day);
		for(Order order:orders){
			baseDAO.delete(order.getOid());
		}
		try {
			oaService.doDeleteOar(DateUtil.parseDate(day, "yyyyMMdd"));
		} catch (ParseException e) {
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
		String sql = String.format(NativeSQLBuilder.EXPRESS_ORDERS,day);
		List<Map<String,Object>> countMapList =  baseDAO.queryListBySQL(sql);
		Long count = (Long) countMapList.get(0).get("count");
		return new BigDecimal(count);
	}
}
