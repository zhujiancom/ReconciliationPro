package com.rci.service.calculatecenter.filter;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.rci.bean.entity.Dish;
import com.rci.bean.entity.OrderItem;
import com.rci.enums.CommonEnums.YOrN;
import com.rci.metadata.service.IDataTransformService;
import com.rci.service.IAccountService;
import com.rci.service.IDishService;
import com.rci.service.IOrderAccountRefService;
import com.rci.service.IOrderService;
import com.rci.service.ISchemeTypeDishRefService;
import com.rci.service.calculatecenter.Calculator;
import com.rci.service.calculatecenter.ParameterValue;
import com.rci.tools.DigitUtil;


public abstract class AbstractPaymodeFilter implements PaymodeFilter {
	protected static final Log logger = LogFactory.getLog(AbstractPaymodeFilter.class);
	
	@Resource(name="calculator")
	protected Calculator calculator;
	
//	@Resource(name="SchemeService")
//	protected ISchemeService schemeService;
//	
	@Resource(name="DishService")
	protected IDishService dishService;
	
	@Resource(name="DataTransformService")
	private IDataTransformService transformService;
//	
//	@Resource(name="SchemeTypeService")
//	protected ISchemeTypeService schemeTypeService;
//	
	@Resource(name="SchemeTypeDishRefService")
	protected ISchemeTypeDishRefService sdrefService;
//	
//	@Resource(name="TicketInfoService")
//	private ITicketInfoService ticketService;
	
	@Resource(name="AccountService")
	protected IAccountService accService;
	
	@Resource(name="OrderAccountRefService")
	protected IOrderAccountRefService oarService;
	
	@Resource(name="OrderService")
	protected IOrderService orderService;
	
	@Override
	public void doFilter(ParameterValue value, PaymodeFilterChain chain) {
		if(support(value.getPayInfo())){
			doExtractOrderInfo(value);
		}
		chain.doFilter(value);
	}
	
	protected abstract void doExtractOrderInfo(ParameterValue value);

	protected int index;
	
	@Override
	public int index() {
		return index;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
	/**
	 * 
	 * Describle(描述)：所有真正的菜品金额，去除了餐盒费，外送费等附加菜品的金额
	 *
	 * 方法名称：wipeoutAccessoryAmount
	 *
	 * 所在类名：AbstractPaymodeFilter
	 *
	 * Create Time:2016年3月1日 上午8:53:33
	 *  
	 * @param order
	 * @return
	 */
	protected BigDecimal[] wipeoutAccessoryAmount(List<OrderItem> items){
		BigDecimal unAccessoryAmount = BigDecimal.ZERO;
		BigDecimal boxfeeAmount = BigDecimal.ZERO;
		BigDecimal takeoutfeeAmount = BigDecimal.ZERO;
		for(OrderItem item:items){
			String dishno = item.getDishNo();
			BigDecimal singlePrice = item.getPrice();
			BigDecimal count = item.getCount();
			BigDecimal countback = item.getCountback();
			BigDecimal ratepercent = item.getDiscountRate();
			BigDecimal rate = DigitUtil.precentDown(ratepercent);
			BigDecimal price = DigitUtil.mutiplyDown(DigitUtil.mutiplyDown(singlePrice, count.subtract(countback)),rate);
			if(isBoxfeeDish(dishno)){
				boxfeeAmount = boxfeeAmount.add(price);
			}
			if(isTakeoutfeeDish(dishno)){
				takeoutfeeAmount = takeoutfeeAmount.add(price);
			}
			if(!isBoxfeeDish(dishno) && !isTakeoutfeeDish(dishno)){
				unAccessoryAmount = unAccessoryAmount.add(price);
			}
		}
		return new BigDecimal[]{unAccessoryAmount,takeoutfeeAmount,boxfeeAmount};
	}
	
	/**
	 * 
	 * Describle(描述)：判断是否是餐盒费
	 *
	 * 方法名称：isBoxfeeDish
	 *
	 * 所在类名：AbstractPaymodeFilter
	 *
	 * Create Time:2016年3月1日 上午8:51:20
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
	
	/**
	 * 
	 * Describle(描述)：判断是否是外送费
	 *
	 * 方法名称：isTakeoutfeeDish
	 *
	 * 所在类名：AbstractPaymodeFilter
	 *
	 * Create Time:2016年3月1日 上午8:51:58
	 *  
	 * @param dishno
	 * @return
	 */
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
	 * Describle(描述)： 判断菜品是否可打折
	 *
	 * 方法名称：isUndiscount
	 *
	 * 所在类名：AbstractPaymodeFilter
	 *
	 * Create Time:2016年3月1日 下午3:19:55
	 *  
	 * @param dishNo
	 * @return
	 */
	protected boolean isUndiscount(String dishNo){
		Dish dish = dishService.findDishByNo(dishNo);
		if(dish == null){
			dish = transformService.transformDishInfo(dishNo);
			if(dish == null){
				logger.warn("--- 不能同步菜品["+dishNo+"].");
				return false;
			}
		}
		if(!YOrN.isY(dish.getDiscountFlag())){
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * Describle(描述)： 获取所有不可打折菜品的总额
	 *
	 * 方法名称：getUndiscountAmount
	 *
	 * 所在类名：AbstractPaymodeFilter
	 *
	 * Create Time:2016年3月1日 下午3:20:10
	 *  
	 * @param items
	 * @return
	 */
	protected BigDecimal getUndiscountAmount(List<OrderItem> items){
		BigDecimal undiscountAmount = BigDecimal.ZERO;
		for(OrderItem item:items){
			String dishNo = item.getDishNo();
			if(isUndiscount(dishNo)){
				BigDecimal singlePrice = item.getPrice();
				BigDecimal count = item.getCount();
				BigDecimal countback = item.getCountback();
				BigDecimal totalPrice = DigitUtil.mutiplyDown(singlePrice, count.subtract(countback));
				undiscountAmount = undiscountAmount.add(totalPrice);
			}
		}
		return undiscountAmount;
	}
}
