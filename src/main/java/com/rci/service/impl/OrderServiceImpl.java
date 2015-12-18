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
import com.rci.enums.BusinessEnums.AccountCode;
import com.rci.metadata.service.IDataTransformService;
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
					totalAmount = totalAmount.add(amount);
					AccountCode accountNo = accountRef.getAccNo();
					switch(accountNo){
					case CASH_MACHINE:vo.setCashmachineAmount(amount);break;
					case POS:vo.setPosAmount(amount);break;
					case MT:vo.setMtAmount(amount);break;
					case DPTG:vo.setDptgAmount(amount);break;
					case DPSH:vo.setDpshAmount(amount);break;
					case ELE:vo.setEleAmount(amount);break;
					case FREE_ELE:vo.setEleFreeAmount(amount);break;
					case ALIPAY:vo.setAliPayAmount(amount);break;
					case MTWM:vo.setMtwmAmount(amount);break;
					case FREE_MTWM:vo.setMtwmFreeAmount(amount);break;
					case MT_SUPER:vo.setMtSuperAmount(amount);break;
					case WMCR:vo.setWmcrAmount(amount);break;
					case FREE_WMCR:vo.setWmcrbtAmount(amount);break;
					//PLQ,BDNM 用于表格列展示，未实现，不影响运行
					case FREE:
						vo.setFreeAmount(amount);
						totalAmount = totalAmount.subtract(amount);
						break;
					case FREE_ONLINE:
						vo.setOnlineFreeAmount(amount);
						totalAmount = totalAmount.subtract(amount);
						break;
					default:
							break;
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
