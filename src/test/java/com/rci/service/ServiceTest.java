package com.rci.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.dozer.Mapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.rci.bean.entity.Scheme;
import com.rci.bean.entity.StockOpLog;
import com.rci.bean.entity.TableInfo;
import com.rci.enums.BusinessEnums.AccountCode;
import com.rci.enums.BusinessEnums.DataGenerateType;
import com.rci.enums.BusinessEnums.PaymodeCode;
import com.rci.enums.BusinessEnums.StockOpType;
import com.rci.metadata.dto.TableDTO;
import com.rci.metadata.service.IDataFetchService;
import com.rci.metadata.service.IDataTransformService;
import com.rci.service.core.IMetadataService;
import com.rci.service.core.StatisticCenterFacade;
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
	@Resource(name="OrderService")
	private IOrderService orderService;
	@Resource(name="StatisticCenterFacade")
	private StatisticCenterFacade facade;
	@Resource(name="DishTypeService")
	private IDishTypeService dishtypeService;
	@Resource(name="FetchMarkService")
	private IFetchMarkService markService;
	@Resource(name="PayModeService")
	private IPayModeService paymodeService;
	@Resource(name="TicketStatisticService")
	private ITicketStatisticService ticketService;
	@Resource(name="MetadataService")
	private IMetadataService metadataService;
	@Resource(name="StockService")
	private IStockService stockService;
	
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
		tbService.rwUpdate(table);
	}
	
	@Test
	public void testDeleteTableInfo(){
		tbService.deleteAll();
	}
	
	@Test
	public void testRowCountOfOrder(){
		orderService.getOrderCountByDay("20150618");
	}
	
	@Test
	public void testRowCountOfExpressRateOrder(){
		orderService.getExpressOrderCountByDay("20150618");
	}
	
	@Test
	public void testGetExpressRate(){
		System.out.println(facade.getExpressRate("20150618"));
	}
	
	@Test
	public void testDishTypeDelete(){
//		dishtypeService.rwDelete(2L);
		dishtypeService.deleteAll();
//		DishType type = dishtypeService.get(3L);
//		dishtypeService.delete(type);
//		List<DishType> types = dishtypeService.getAll();
//		for(DishType type:types){
//			System.out.println(type.getDtid());
//			dishtypeService.delete(type);
//		}
	}
	
	@Test
	public void testTransformDish(){
		transformSevice.transformDishInfo();
	}
	
	@Test
	public void testMarkDelete(){
		markService.deleteMark("20150310");
//		markService.rwDeleteMark("20150426");
	}
	
	@Test
	public void testMarkOrder(){
		markService.markOrder("20150310");
	}
	
	@Test
	public void testPayModeAll(){
		paymodeService.deleteAll();
	}
	
	@Test
	public void testDeleteTicketStatistic(){
		ticketService.deleteTicketStatistic("20150518");
	}
	
	@Test
	public void testClearMetadata(){
		metadataService.clearMetadata();
	}
	
	@Test
	public void testInsertStockLog(){
		StockOpLog sol = new StockOpLog();
		sol.setDay("20150711");
		sol.setConsumeTime(DateUtil.getCurrentDate());
		sol.setDishName("雪碧");
		sol.setConsumeAmount(new BigDecimal(2));
		sol.setType(StockOpType.CONSUME);
		stockService.insertStockOpLog(sol);
	}
	
	@Test
	public void testCleanStock(){
		stockService.clearStockByDay("20150715");
	}
	
	@Test
	public void testGetValidOrderCount(){
		Long result = oaService.getValidOrderCount(DateUtil.parseDate("2015-07-29"), AccountCode.ELE);
		System.out.println(result);
	}
	
	@Test
	public void testPaymodeCodeEnum(){
		System.out.println(PaymodeCode.paymodeCode("18"));
		System.out.println(PaymodeCode.ELE.getPaymodeno());
	}
}
