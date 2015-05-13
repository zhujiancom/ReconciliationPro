package com.rci.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.dozer.Mapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.rci.bean.entity.Scheme;
import com.rci.enums.BusinessEnums.DataGenerateType;
import com.rci.service.impl.OrderAccountRefServiceImpl.AccountSumResult;
import com.rci.tools.DateUtil;
import com.rci.tools.properties.PropertyUtils;
import com.rci.ui.swing.vos.SchemeVO;

@ContextConfiguration({"classpath:spring/spring-db.xml","classpath:spring/spring-common.xml"})
public class ServiceTest extends AbstractJUnit4SpringContextTests{
	@Resource(name="OrderAccountRefService")
	private IOrderAccountRefService oaService;
	
	@Resource(name="AccFlowService")
	private IAccFlowService flowService;
	
	@Autowired
	private Mapper beanMapper;
	
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
		BigDecimal a = new BigDecimal("15");
		BigDecimal b = new BigDecimal("15");
		System.out.println(a.remainder(b));
		System.out.println(a.remainder(b).equals(BigDecimal.ZERO));
	}
	
	@Test
	public void testPropertiesUtils(){
//		System.out.println(PropertyUtils.getIntegerValue("mtwm.base.free.amount"));
		System.out.println(PropertyUtils.getBigDecimalValue("mtwm.base.free.amount"));
	}
	
	@Test
	public void testDate() throws ParseException{
		Date orderDate = DateUtil.parseDate("20150328","yyyyMMdd");
		Date sdate = DateUtil.parseDate("2015-03-02");
		Date edate = DateUtil.parseDate("2015-04-10");
		System.out.println(orderDate.before(edate));
		System.out.println(orderDate.after(sdate));
	}
	
	@Test
	public void testDozer(){
		Scheme s = new Scheme();
		s.setUnitCode("PC");
		SchemeVO vo = beanMapper.map(s, SchemeVO.class);
	}
}
