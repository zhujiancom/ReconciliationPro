package com.rci.service.filter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.CollectionUtils;

import com.rci.bean.SchemeWrapper;
import com.rci.bean.dto.SchemeQueryDTO;
import com.rci.bean.entity.Dish;
import com.rci.bean.entity.DishType;
import com.rci.bean.entity.Order;
import com.rci.bean.entity.OrderAccountRef;
import com.rci.bean.entity.Scheme;
import com.rci.bean.entity.SchemeType;
import com.rci.bean.entity.TicketInfo;
import com.rci.bean.entity.account.Account;
import com.rci.enums.BusinessEnums.AccountCode;
import com.rci.enums.BusinessEnums.ActivityStatus;
import com.rci.enums.BusinessEnums.ActivityType;
import com.rci.enums.BusinessEnums.Vendor;
import com.rci.exceptions.ExceptionConstant.SERVICE;
import com.rci.exceptions.ExceptionManage;
import com.rci.metadata.service.IDataTransformService;
import com.rci.service.IAccountService;
import com.rci.service.IDishService;
import com.rci.service.IOrderAccountRefService;
import com.rci.service.IOrderService;
import com.rci.service.ISchemeService;
import com.rci.service.ISchemeTypeDishRefService;
import com.rci.service.ISchemeTypeService;
import com.rci.service.ITicketInfoService;
import com.rci.service.ITicketStatisticService;
import com.rci.tools.DateUtil;
import com.rci.tools.DigitUtil;

public abstract class AbstractFilter implements CalculateFilter {
	public static final Log logger = LogFactory.getLog(AbstractFilter.class);
	
	@Resource(name="DishService")
	protected IDishService dishService;
	
	@Resource(name="OrderAccountRefService")
	private IOrderAccountRefService oarService;
	
	@Resource(name="AccountService")
	private IAccountService accService;
	
	@Resource(name="SchemeService")
	protected ISchemeService schemeService;
	
	@Resource(name="OrderService")
	protected IOrderService orderService;
	
	@Resource(name="DataTransformService")
	private IDataTransformService transformService;
	
	@Resource(name="TicketStatisticService")
	private ITicketStatisticService statisticService;
	
	@Resource(name="TicketInfoService")
	private ITicketInfoService ticketService;
	
	@Resource(name="SchemeTypeService")
	protected ISchemeTypeService schemeTypeService;
	
	@Resource(name="SchemeTypeDishRefService")
	protected ISchemeTypeDishRefService sdrefService;
	
	
	@Override
	public void doFilter(Order order,FilterChain chain) {
		if (support(order.getPaymodeMapping())) {
			generateScheme(order,chain);
		}
		chain.doFilter(order, chain);
	}

	protected abstract void generateScheme(Order order,FilterChain chain);

	/**
	 * 菜品是否不可以打折
	 * @param dishNo
	 * @return
	 */
	protected Boolean isNodiscount(String dishNo){
		Dish dish = dishService.findDishByNo(dishNo);
		if(dish ==null){
			dish = transformService.transformDishInfo(dishNo);
		}
		DishType type = dish.getDishType();
//		if(type != null && YOrN.isY(type.getNotDiscount())){
//			return true;
//		}
		return false;
	}
	
//	protected SchemeType getSuitSchemeType(String dishNo){
//		Dish dish = dishService.findDishByNo(dishNo);
//		if(dish.getDishName().indexOf("套餐A") != -1){
//			return SchemeType.SUIT_32;
//		}
//		if(dish.getDishName().indexOf("套餐B") != -1){
//			return SchemeType.SUIT_68;
//		}
//		if(dish.getDishName().indexOf("套餐C") != -1){
//			return SchemeType.SUIT_98;
//		}
//		if(dish.getDishName().indexOf("套餐D+") != -1){
//			return SchemeType.SUIT_128;
//		}
//		if(dish.getDishName().indexOf("套餐D") != -1){
//			return SchemeType.SUIT_138;
//		}
//		if(dish.getDishName().indexOf("套餐E+") != -1){
//			return SchemeType.SUIT_200;
//		}
//		if(dish.getDishName().indexOf("套餐B-1") != -1){
//			return SchemeType.SUIT_50;
//		}
//		return null;
//	}
	
	protected Boolean isSingleDiscount(BigDecimal rate){
		if(rate.compareTo(new BigDecimal(80)) == 0 || rate.compareTo(new BigDecimal(100)) == 0){
			return false;
		}
		return true;
	}
	
	/**
	 * 
	 * Describle(描述)：反推计算使用了什么样的代金券
	 *
	 * 方法名称：createSchemes
	 *
	 * 所在类名：AbstractFilter
	 *
	 * Create Time:2015年4月28日 下午2:14:20
	 *  
	 * @param amount
	 * @param paymodeno
	 * @param suitFlag
	 * @return
	 */
	protected Map<SchemeType,SchemeWrapper> createSchemes(BigDecimal amount, Vendor vendor,boolean suitFlag,Date queryDate) {
		Map<SchemeType,SchemeWrapper> schemes = new HashMap<SchemeType,SchemeWrapper>();
		BigDecimal suitAmount = BigDecimal.ZERO;
		// 1.如果有套餐
		if (suitFlag) {
			for(Iterator<Entry<SchemeType, Integer>> it=getSuitMap().entrySet().iterator();it.hasNext();){
				Entry<SchemeType, Integer> entry = it.next();
				SchemeType type = entry.getKey();
				Integer count = entry.getValue();
				Scheme scheme = schemeService.getScheme(type.getTypeNo(),vendor,queryDate);
				BigDecimal suitPrice = scheme.getPrice();
				suitAmount = suitAmount.add(suitPrice.multiply(new BigDecimal(count)));
				SchemeWrapper schemewrapper = new SchemeWrapper(scheme,count);
				schemes.put(type, schemewrapper);
			}
		}
		BigDecimal leftAmount = amount.subtract(suitAmount);
//		loopSchemes(leftAmount.intValue(),schemes,vendor,queryDate);
		loopSchemes(leftAmount,schemes,vendor,queryDate);
		return schemes;
	}
	
	/**
	 * 
	 * Describle(描述)： 计算代金券消费情况
	 *
	 * 方法名称：loopSchemes
	 *
	 * 所在类名：AbstractFilter
	 *
	 * Create Time:2015年11月11日 下午3:35:30
	 *  
	 * @param amount
	 * @param schemes
	 * @param vendor
	 * @param queryDate
	 */
//	private void loopSchemes(Integer amount, Map<SchemeType,SchemeWrapper> schemes,Vendor vendor,Date queryDate) {
//		if(amount <= 0){
//			return;
//		}
//		if (amount > 100) {
//			// 金额在大于100，使用100元代金券
//			SchemeWrapper schemewrapper = null;
//			SchemeType chit100 = schemeTypeService.getSchemeType();
//			if(schemes.get(SchemeType.CHIT_100) != null){
//				schemewrapper = schemes.get(SchemeType.CHIT_100);
//				schemewrapper.increasement();
//			}else{
//				Scheme scheme = schemeService.getScheme(SchemeType.CHIT_100,vendor,queryDate);
//				schemewrapper = new SchemeWrapper(scheme,1);
//				schemes.put(SchemeType.CHIT_100, schemewrapper);
//			}
//			amount = amount - 100;
//		}else if (50 < amount && amount <= 100) {
//			// 金额在50-100之间，使用100元代金券
//			SchemeWrapper schemewrapper = null;
//			if(schemes.get(SchemeType.CHIT_100) != null){
//				schemewrapper = schemes.get(SchemeType.CHIT_100);
//				schemewrapper.increasement();
//			}else{
//				Scheme scheme = schemeService.getScheme(SchemeType.CHIT_100,vendor,queryDate);
//				schemewrapper = new SchemeWrapper(scheme,1);
//				schemes.put(SchemeType.CHIT_100, schemewrapper);
//			}
//			amount = amount - 100;
//		}else if(amount >0 && amount <= 50){
//			// 金额在小于等于50，使用50元代金券
//			SchemeWrapper schemewrapper = null;
//			if(schemes.get(SchemeType.CHIT_50) != null){
//				schemewrapper = schemes.get(SchemeType.CHIT_50);
//				schemewrapper.increasement();
//			}else{
//				Scheme scheme = schemeService.getScheme(SchemeType.CHIT_50,vendor,queryDate);
//				schemewrapper = new SchemeWrapper(scheme,1);
//				schemes.put(SchemeType.CHIT_50, schemewrapper);
//			}
//			amount = amount - 50;
//		}
//		loopSchemes(amount,schemes,vendor,queryDate);
//	}
	
	private void loopSchemes(BigDecimal amount, Map<SchemeType,SchemeWrapper> schemes,Vendor vendor,Date queryDate) {
		if(amount.compareTo(BigDecimal.ZERO) <= 0){
			return;
		}
		SchemeType type = schemeTypeService.getSchemeType(amount, ActivityType.VOUCHER);
		SchemeWrapper schemewrapper = null;
		if(schemes.get(type) != null){
			schemewrapper = schemes.get(type);
			schemewrapper.increasement();
		}else{
			Scheme scheme = schemeService.getScheme(type.getTypeNo(),vendor,queryDate);
			schemewrapper = new SchemeWrapper(scheme,1);
			schemes.put(type, schemewrapper);
		}
		amount = amount.subtract(type.getRealAmount());
		loopSchemes(amount,schemes,vendor,queryDate);
	}
	
	protected abstract Map<SchemeType, Integer> getSuitMap();
	
	/**
	 * 
	 *
	 * Describle(描述)：大众点评团购，美团团购算法
	 *
	 * 方法名称：calculateTG
	 *
	 * 所在类名：AbstractFilter
	 *
	 * Create Time:2015年4月26日 上午9:43:55
	 *  
	 * @param scheme
	 * @param count
	 * @return
	 */
	protected BigDecimal calculateTG(Scheme scheme,Integer count){
		BigDecimal postAmount = BigDecimal.ZERO;
		BigDecimal singlePrice = BigDecimal.ZERO;
		if(scheme.getCommission() != null && scheme.getCommission().compareTo(BigDecimal.ZERO) != 0){
			BigDecimal rate = BigDecimal.ONE.subtract(DigitUtil.precentDown(scheme.getCommission()));
			singlePrice = DigitUtil.mutiplyDown(scheme.getPostPrice(), rate);
		}else{
			singlePrice = scheme.getPostPrice();
		}
//		if(scheme.getSpread() != null){
//			singlePrice = singlePrice.add(scheme.getSpread());
//		}
		postAmount = DigitUtil.mutiplyDown(singlePrice,new BigDecimal(count));
		return postAmount;
	}
	
	/**
	 * 
	 * Describle(描述)：计算代金券优惠金额
	 *
	 * 方法名称：calculateOnlineFreeAmount
	 *
	 * 所在类名：AbstractFilter
	 *
	 * Create Time:2015年8月3日 下午4:28:36
	 *  
	 * @param scheme
	 * @param count
	 * @return
	 */
	protected BigDecimal calculateOnlineFreeAmount(Scheme scheme,Integer count){
		BigDecimal onlineFreeAmount = BigDecimal.ZERO;
		BigDecimal singlePrice = scheme.getPrice().subtract(scheme.getPostPrice());
		onlineFreeAmount = DigitUtil.mutiplyDown(singlePrice,new BigDecimal(count));
		return onlineFreeAmount;
	}
	
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
	
//	@Deprecated
//	protected void createTicketStatistic(String day,Vendor vendor,Map<SchemeType,SchemeWrapper> schemes){
//		try{
//			Date queryDate = DateUtil.parseDate(day,"yyyyMMdd");
//			for(Iterator<Entry<SchemeType,SchemeWrapper>> it = schemes.entrySet().iterator();it.hasNext();){
//				Entry<SchemeType,SchemeWrapper> entry = it.next();
//				SchemeType type = entry.getKey();
//				SchemeWrapper wrapper = entry.getValue();
//				TicketStatistic ts = statisticService.queryTicketStatisticByDateAndType(type, queryDate, vendor);
//				if(ts == null){
//					ts = new TicketStatistic();
//					ts.setValifyCount(wrapper.getCount());
//					ts.setVendor(vendor);
//					ts.setDate(queryDate);
//					statisticService.rwCreate(ts);
//				}else{
//					ts.setValifyCount(ts.getValifyCount()+wrapper.getCount());
//					statisticService.rwUpdate(ts);
//				}
//			}
//		}catch(Exception e){
//			
//		}
//	try{
//		Date queryDate = DateUtil.parseDate(day,"yyyyMMdd");
//		TicketStatistic ts = statisticService.queryTicketStatisticByDate(queryDate, vendor);
		//统计代金券信息
//		if(ts == null){
//			ts = new TicketStatistic();
//			ts.setChit50(schemes.get(SchemeType.CHIT_50)==null?0:schemes.get(SchemeType.CHIT_50).getCount());
//			ts.setChit100(schemes.get(SchemeType.CHIT_100)==null?0:schemes.get(SchemeType.CHIT_100).getCount());
//			ts.setSuit32(schemes.get(SchemeType.SUIT_32)==null?0:schemes.get(SchemeType.SUIT_32).getCount());
//			ts.setSuit68(schemes.get(SchemeType.SUIT_68)==null?0:schemes.get(SchemeType.SUIT_68).getCount());
//			ts.setSuit98(schemes.get(SchemeType.SUIT_98)==null?0:schemes.get(SchemeType.SUIT_98).getCount());
//			ts.setVendor(vendor);
//			ts.setDate(queryDate);
//			statisticService.rwCreate(ts);
//		}else{
//			Integer chit50 = schemes.get(SchemeType.CHIT_50)==null?0:schemes.get(SchemeType.CHIT_50).getCount();
//			if(chit50 > 0){
//				ts.setChit50(ts.getChit50()+chit50);
//			}
//			Integer chit100 = schemes.get(SchemeType.CHIT_100)==null?0:schemes.get(SchemeType.CHIT_100).getCount();
//			if(chit100 > 0){
//				ts.setChit100(ts.getChit100()+chit100);
//			}
//			Integer suit32 = schemes.get(SchemeType.SUIT_32)==null?0:schemes.get(SchemeType.SUIT_32).getCount();
//			if(suit32 > 0){
//				ts.setSuit32(ts.getSuit32()+suit32);
//			}
//			Integer suit68 = schemes.get(SchemeType.SUIT_68)==null?0:schemes.get(SchemeType.SUIT_68).getCount();
//			if(suit68 > 0){
//				ts.setSuit68(ts.getSuit68()+suit68);
//			}
//			Integer suit98 = schemes.get(SchemeType.SUIT_98)==null?0:schemes.get(SchemeType.SUIT_98).getCount();
//			if(suit98 > 0){
//				ts.setSuit98(ts.getSuit98()+suit98);
//			}
//			statisticService.rwUpdate(ts);
//		}
//	} catch (ParseException e) {
//		e.printStackTrace();
//	}
//	}
	
	protected void createTicketRecord(Order order,Vendor vendor,Map<SchemeType,SchemeWrapper> schemes){
		try{
			Date queryDate = DateUtil.parseDate(order.getDay(),"yyyyMMdd");
			for(Iterator<Entry<SchemeType,SchemeWrapper>> it = schemes.entrySet().iterator();it.hasNext();){
				Entry<SchemeType,SchemeWrapper> entry = it.next();
				SchemeType type = entry.getKey();
				SchemeWrapper wrapper = entry.getValue();
				TicketInfo ticketInfo = ticketService.queryTicketStatisticByDateAndType(type, queryDate, vendor);
				if(ticketInfo == null){
					ticketInfo = new TicketInfo();
					ticketInfo.setSchemeType(type);
					ticketInfo.setName(type.getTypeName());
					ticketInfo.setValifyCount(wrapper.getCount());
					ticketInfo.setVendor(vendor);
					ticketInfo.setDate(queryDate);
					ticketService.rwCreate(ticketInfo);
				}else{
					ticketInfo.setValifyCount(ticketInfo.getValifyCount()+wrapper.getCount());
					ticketService.rwUpdate(ticketInfo);
				}
			}
		}catch(Exception e){
			
		}
	}
	
	protected abstract void validation(Order order);
	
	protected BigDecimal[] calculatePostAmount(BigDecimal payAmount,String day,Vendor vendor){
		try{
			SchemeQueryDTO queryDTO = new SchemeQueryDTO();
			queryDTO.setVendor(vendor);
			Date queryDate = DateUtil.parseDate(day, "yyyyMMdd");
			queryDTO.setStartDate(queryDate);
			queryDTO.setEndDate(queryDate);
			queryDTO.setStatus(ActivityStatus.ACTIVE);
			List<Scheme> schemes = schemeService.getSchemes(queryDTO);
			if(!CollectionUtils.isEmpty(schemes) && schemes.size() > 1){
				ExceptionManage.throwServiceException(SERVICE.DATA_ERROR, "活动重复");
				for(Scheme scheme:schemes){
					logger.error("----重复活动名称："+scheme);
				}
			}
			if(CollectionUtils.isEmpty(schemes)){
				logger.warn("----没有匹配到活动----");
				return new BigDecimal[]{payAmount,BigDecimal.ZERO};
			}
			Scheme scheme = schemes.get(0);
			BigDecimal chitAmount = scheme.getPrice();
			BigDecimal count = payAmount.divideToIntegralValue(chitAmount);
			BigDecimal singleActualAmount = scheme.getPostPrice();
			BigDecimal totalChitAmount = DigitUtil.mutiplyDown(singleActualAmount, count);
			BigDecimal balance = payAmount.subtract(chitAmount.multiply(count));
			BigDecimal onlineFreeAmount = DigitUtil.mutiplyDown(chitAmount.subtract(singleActualAmount),count);
			/* 入账金额  */
			BigDecimal postAmount =totalChitAmount.add(balance);
			return new BigDecimal[]{postAmount,onlineFreeAmount};
		}catch(Exception e){
			e.printStackTrace();
		}
		return new BigDecimal[]{payAmount,BigDecimal.ZERO};
	}
	
}
