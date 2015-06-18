package com.rci.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.dozer.Mapper;
import org.hibernate.mapping.Collection;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.rci.bean.entity.Scheme;
import com.rci.bean.entity.TableInfo;
import com.rci.enums.BusinessEnums.DataGenerateType;
import com.rci.metadata.dto.TableDTO;
import com.rci.metadata.service.IDataFetchService;
import com.rci.metadata.service.IDataTransformService;
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
	
	@Resource(name="SchemeService")
	private ISchemeService schemeService;
	
	@Resource(name="DataFetchService")
	private IDataFetchService fetchService;
	
	@Resource(name="DataTransformService")
	private IDataTransformService transformSevice;
	@Resource(name="TableInfoService")
	private ITableInfoService tbService;
	
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
		BigDecimal a = new BigDecimal("153");
		BigDecimal b = new BigDecimal("100");
//		System.out.println(a.remainder(b));
//		System.out.println(a.remainder(b).equals(BigDecimal.ZERO));
		System.out.println(a.divideToIntegralValue(b));
	}
	
	@Test
	public void testBigDecimalDivide(){
		BigDecimal a = new BigDecimal("161");
		BigDecimal b = new BigDecimal("100");
		System.out.println(a.divideToIntegralValue(b));
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
	
	@Test
	public void testGetScheme() throws ParseException{
		Date date = DateUtil.parseDate("20150527", "yyyyMMdd");
	}
	
	@Test
	public void testdozermapperList(){
		List<TableDTO> tableDTOs = fetchService.fetchTables();
		List<TableInfo> tables = new ArrayList<TableInfo>();
//		TableInfo[] tables = new TableInfo[tableDTOs.size()];
		beanMapper.map(tableDTOs,tables);
//		System.out.println(tables.length);
		System.out.println(tables.get(0).getTbName());
		
//		for(TableDTO tdto:tableDTOs){
//			tables.add(beanMapper.map(tdto, TableInfo.class));
//		}
//		System.out.println(tables.get(0).getTbName());
	}
	
	@Test
	public void testTransformTableInfo(){
		transformSevice.transformTableInfo();
	}
	
	@Test
	public void testUpdateTableInfo(){
		TableInfo table = tbService.get(1L);
		table.setTbName("test");
		tbService.rwUpdateTableInfo(table);
	}
}
