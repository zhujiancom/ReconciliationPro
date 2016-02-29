package com.rci.service.calculatecenter.filter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.rci.bean.dto.SchemeQueryDTO;
import com.rci.bean.entity.Scheme;
import com.rci.enums.BusinessEnums.ActivityStatus;
import com.rci.enums.BusinessEnums.Vendor;
import com.rci.service.ISchemeService;
import com.rci.service.calculatecenter.ParameterValue;


public abstract class AbstractPaymodeFilter implements PaymodeFilter {
	protected static final Log logger = LogFactory.getLog(AbstractPaymodeFilter.class);
	
	@Resource(name="SchemeService")
	protected ISchemeService schemeService;
	
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
	 * Describle(描述)： 获取合适的方案
	 *
	 * 方法名称：getAppropriteSchemes
	 *
	 * 所在类名：ParameterValue
	 *
	 * Create Time:2016年2月29日 上午11:14:57
	 *  
	 * @return
	 */
	protected List<Scheme> getAppropriteSchemes(Date date,Vendor vendor){
		SchemeQueryDTO queryDTO = new SchemeQueryDTO();
		queryDTO.setStatus(ActivityStatus.ACTIVE);
		queryDTO.setEndDate(date);
		queryDTO.setStartDate(date);
		queryDTO.setVendor(vendor);
		return schemeService.getSchemes(queryDTO);
	}
	
	/**
	 * 
	 * Describle(描述)：获取新用户享受的方案信息。
	 * 约定方案：方案的最高消费金额和最低消费金额同时与免单金额相同，则表示本订单享受的是新用户优惠方案
	 *
	 * 方法名称：getSchemeForNewCustomer
	 *
	 * 所在类名：AbstractPaymodeFilter
	 *
	 * Create Time:2016年2月29日 下午6:33:05
	 *  
	 * @param freeAmount
	 * @param schemes
	 * @return
	 */
	protected Scheme getSchemeForNewCustomer(BigDecimal freeAmount,List<Scheme> schemes){
		Scheme result = null;
		for(Scheme scheme:schemes){
			if(freeAmount.compareTo(scheme.getFloorAmount()) == 0 && freeAmount.compareTo(scheme.getCeilAmount()) == 0){
				result = scheme;
				break;
			}
		}
		return result;
	}
}
