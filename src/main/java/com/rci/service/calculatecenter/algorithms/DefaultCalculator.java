package com.rci.service.calculatecenter.algorithms;

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
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.rci.bean.SchemeWrapper;
import com.rci.bean.dto.SchemeQueryDTO;
import com.rci.bean.entity.Scheme;
import com.rci.bean.entity.SchemeType;
import com.rci.bean.entity.TicketInfo;
import com.rci.enums.BusinessEnums.ActivityStatus;
import com.rci.enums.BusinessEnums.ActivityType;
import com.rci.enums.BusinessEnums.Vendor;
import com.rci.exceptions.ExceptionManage;
import com.rci.exceptions.ServiceException;
import com.rci.service.ISchemeService;
import com.rci.service.ISchemeTypeService;
import com.rci.service.ITicketInfoService;
import com.rci.service.calculatecenter.Calculator;
import com.rci.tools.DigitUtil;

@Component("calculator")
public class DefaultCalculator implements Calculator {
	private static final Log logger = LogFactory.getLog(DefaultCalculator.class);
	@Resource(name="SchemeService")
	protected ISchemeService schemeService;
	
	@Resource(name="SchemeTypeService")
	protected ISchemeTypeService schemeTypeService;
	
	@Resource(name="TicketInfoService")
	private ITicketInfoService ticketService;

	@Override
	public BigDecimal[] doCalculateAmountForTG(Scheme scheme, Integer count) {
		BigDecimal freeAmount = scheme.getSpread();
		BigDecimal postAmount = scheme.getPostPrice();
		BigDecimal commission = scheme.getCommission();
		//有佣金
		if(commission != null && commission.compareTo(BigDecimal.ZERO) != 0){
			//计算每张代金券商家优惠的金额和商家到账金额
			BigDecimal commissionAmount = DigitUtil.mutiplyDown(postAmount, scheme.getCommission()); 
			freeAmount = freeAmount.add(commissionAmount);
			postAmount = postAmount.subtract(commissionAmount);
		}
		postAmount = DigitUtil.mutiplyDown(postAmount,new BigDecimal(count));
		freeAmount = DigitUtil.mutiplyDown(freeAmount,new BigDecimal(count));
		return new BigDecimal[]{postAmount,freeAmount};
	}
	
	public Map<SchemeType,SchemeWrapper> getSchemesForTG(BigDecimal amount,Vendor vendor,Map<SchemeType, Integer> suitMap,Date queryDate){
		Map<SchemeType,SchemeWrapper> schemes = new HashMap<SchemeType,SchemeWrapper>();
		BigDecimal suitAmount = BigDecimal.ZERO;
		//如果有套餐
		if(!CollectionUtils.isEmpty(suitMap)){
			for(Iterator<Entry<SchemeType, Integer>> it=suitMap.entrySet().iterator();it.hasNext();){
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
		loopSchemes(leftAmount,schemes,vendor,queryDate);
		return schemes;
	}
	
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

	@Override
	public BigDecimal[] doCalculateAmountForOnlinePay(BigDecimal payAmount,
			Date queryDate, Vendor vendor) throws ServiceException {
		List<Scheme> schemes = getAppropriteSchemes(queryDate, vendor);
		if(!CollectionUtils.isEmpty(schemes) && schemes.size() > 1){
			ExceptionManage.throwServiceException("优惠买单活动重复！");
			for(Scheme scheme:schemes){
				logger.error("----优惠买单重复活动名称："+scheme);
			}
		}
		if(CollectionUtils.isEmpty(schemes)){
			logger.warn("----没有匹配到活动----");
			return new BigDecimal[]{payAmount,BigDecimal.ZERO};
		}
		Scheme scheme = schemes.get(0);
		BigDecimal chitAmount = scheme.getPrice();//需要消费满的金额
		BigDecimal count = payAmount.divideToIntegralValue(chitAmount);//倍数
		BigDecimal singleActualAmount = scheme.getPostPrice();
		BigDecimal totalChitAmount = DigitUtil.mutiplyDown(singleActualAmount, count);
		BigDecimal balance = payAmount.subtract(chitAmount.multiply(count));
		BigDecimal onlineFreeAmount = DigitUtil.mutiplyDown(chitAmount.subtract(singleActualAmount),count);
		/* 入账金额  */
		BigDecimal postAmount =totalChitAmount.add(balance);
		return new BigDecimal[]{postAmount,onlineFreeAmount};
	}

	@Override
	public List<Scheme> getAppropriteSchemes(Date date, Vendor vendor) {
		SchemeQueryDTO queryDTO = new SchemeQueryDTO();
		queryDTO.setStatus(ActivityStatus.ACTIVE);
		queryDTO.setEndDate(date);
		queryDTO.setStartDate(date);
		queryDTO.setVendor(vendor);
		return schemeService.getSchemes(queryDTO);
	}
	
	@Override
	public Scheme getAppropriteScheme(Date date, BigDecimal amount,
			Vendor vendor) {
		return schemeService.getScheme(vendor, amount, date);
	}

	@Override
	public Scheme getSchemeForNewCustomer(BigDecimal freeAmount,
			List<Scheme> schemes) {
		Scheme result = null;
		for(Scheme scheme:schemes){
			if(freeAmount.compareTo(scheme.getFloorAmount()) == 0 && freeAmount.compareTo(scheme.getCeilAmount()) == 0){
				result = scheme;
				break;
			}
		}
		return result;
	}

	@Override
	public void createTicketRecord(Date queryDate, Vendor vendor,
			Map<SchemeType, SchemeWrapper> schemes) {
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
	}

	
}
