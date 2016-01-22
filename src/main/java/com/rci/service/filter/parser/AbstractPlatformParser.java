package com.rci.service.filter.parser;

import java.math.BigDecimal;

import javax.annotation.Resource;

import com.rci.bean.entity.Dish;
import com.rci.bean.entity.Order;
import com.rci.bean.entity.OrderAccountRef;
import com.rci.bean.entity.account.Account;
import com.rci.enums.BusinessEnums.AccountCode;
import com.rci.enums.CommonEnums.YOrN;
import com.rci.metadata.service.IDataTransformService;
import com.rci.service.IAccountService;
import com.rci.service.IDishService;
import com.rci.service.IOrderAccountRefService;
import com.rci.service.IOrderService;

public abstract class AbstractPlatformParser implements IPlatformParser{
	@Resource(name="AccountService")
	private IAccountService accService;
	
	@Resource(name="OrderAccountRefService")
	private IOrderAccountRefService oarService;
	
	@Resource(name="OrderService")
	protected IOrderService orderService;
	
	@Resource(name="DishService")
	protected IDishService dishService;
	
	@Resource(name="DataTransformService")
	private IDataTransformService transformService;
	/**
	 * 
	 *
	 * Describle(描述)：保存订单和账户关联信息
	 *
	 * 方法名称：preserveOAR
	 *
	 * 所在类名：AbstractFilter
	 *
	 * Create Time:2015年4月26日 上午9:54:30
	 *  
	 * @param postAmount
	 * @param accNo
	 * @param order
	 */
	protected void preserveOAR(BigDecimal postAmount,AccountCode accNo,Order order){
		OrderAccountRef oar = new OrderAccountRef();
		Account account = accService.getAccByNo(accNo);
		oar.setAccId(account.getAccId());
		oar.setAccNo(accNo);
		oar.setOrderNo(order.getOrderNo());
		oar.setPostTime(order.getCheckoutTime());
		oar.setRealAmount(postAmount);
		oar.setFramework(order.getFramework());
		//保存关联数据
		oarService.rwCreate(oar);
		orderService.rwUpdate(order);
	}
	
	/**
	 * 
	 * Describle(描述)：判断是否是餐盒费
	 *
	 * 方法名称：isAccessoryDish
	 *
	 * 所在类名：AbstractPlatformParser
	 *
	 * Create Time:2016年1月22日 下午2:12:04
	 *  
	 * @param dishno
	 * @return
	 */
	protected boolean isBoxfeeDish(String dishno){
		Dish dish = dishService.findDishByNo(dishno);
		if(dish == null){
			dish = transformService.transformDishInfo(dishno);
			if(dish == null){
				return false;
			}
		}
		if(YOrN.isY(dish.getBoxFeeFlag())){
			return true;
		}
		return false;
	}
	
	protected boolean isTakeoutfeeDish(String dishno){
		Dish dish = dishService.findDishByNo(dishno);
		if(dish == null){
			dish = transformService.transformDishInfo(dishno);
			if(dish == null){
				return false;
			}
		}
		if(YOrN.isY(dish.getTakeoutFeeFlag())){
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * Describle(描述)：判断是否是可打折菜品
	 *
	 * 方法名称：isDishcountDish
	 *
	 * 所在类名：AbstractPlatformParser
	 *
	 * Create Time:2016年1月22日 下午2:13:34
	 *  
	 * @param dishno
	 * @return
	 */
	protected boolean isDishcountDish(String dishno){
		Dish dish = dishService.findDishByNo(dishno);
		if(dish == null){
			dish = transformService.transformDishInfo(dishno);
			if(dish == null){
				return false;
			}
		}
		if(YOrN.isY(dish.getDiscountFlag())){
			return true;
		}
		return false;
	}
}
