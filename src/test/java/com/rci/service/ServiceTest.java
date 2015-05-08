package com.rci.service;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.rci.enums.BusinessEnums.DataGenerateType;
import com.rci.service.impl.OrderAccountRefServiceImpl.AccountSumResult;
import com.rci.tools.DateUtil;
import com.rci.tools.properties.PropertyUtils;

@ContextConfiguration({"classpath:spring/spring-db.xml","classpath:spring/spring-common.xml"})
public class ServiceTest extends AbstractJUnit4SpringContextTests{
	@Resource(name="OrderAccountRefService")
	private IOrderAccountRefService oaService;
	
	@Resource(name="AccFlowService")
	private IAccFlowService flowService;
	
	@Test
	public void testQuerySumAmount() {
		List<AccountSumResult> result = oaService.querySumAmount(DateUtil.parseDate("2015-04-18"));
		for(AccountSumResult as: result){
			System.out.println(as.getAccId()+" - "+as.getAccNo()+" - "+as.getSumAmount());
		}
	}
	
	@Test
	public void testRWDeleteFlowInfo(){
		flowService.rwDeleteFlowInfo("2015-04-18", DataGenerateType.AUTO);
	}

	@Test
	public void testBigDecimalRemainder(){
		BigDecimal a = new BigDecimal("23");
		BigDecimal b = new BigDecimal("15");
		System.out.println(a.remainder(b));
	}
	
	@Test
	public void testPropertiesUtils(){
//		System.out.println(PropertyUtils.getIntegerValue("mtwm.base.free.amount"));
		System.out.println(PropertyUtils.getBigDecimalValue("mtwm.base.free.amount"));
	}
}
