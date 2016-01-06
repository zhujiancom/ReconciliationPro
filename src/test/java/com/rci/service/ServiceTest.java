package com.rci.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
import com.rci.bean.entity.TableInfo;
import com.rci.bean.entity.inventory.Inventory;
import com.rci.bean.entity.inventory.InventoryDishRef;
import com.rci.bean.entity.inventory.InventorySellLog;
import com.rci.enums.BusinessEnums.AccountCode;
import com.rci.enums.BusinessEnums.DataGenerateType;
import com.rci.enums.BusinessEnums.PaymodeCode;
import com.rci.metadata.dto.TableDTO;
import com.rci.metadata.service.IDataFetchService;
import com.rci.metadata.service.IDataTransformService;
import com.rci.service.core.IMetadataService;
import com.rci.service.core.StatisticCenterFacade;
import com.rci.service.impl.OrderAccountRefServiceImpl.AccountSumResult;
import com.rci.service.inventory.IInventoryDishRefService;
import com.rci.service.inventory.IInventorySellLogService;
import com.rci.service.inventory.IInventoryService;
import com.rci.tools.DateUtil;
import com.rci.tools.properties.PropertyUtils;
import com.rci.ui.swing.vos.DishVO;

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
	@Resource(name="DishService")
	private IDishService dishService;
	@Resource(name="FetchMarkService")
	private IFetchMarkService markService;
	@Resource(name="PayModeService")
	private IPayModeService paymodeService;
	@Resource(name="TicketStatisticService")
	private ITicketStatisticService ticketService;
	@Resource(name="MetadataService")
	private IMetadataService metadataService;
	@Resource(name="DishSeriesService")
	private IDishSeriesService dishseriesService;
	@Resource(name="SchemeTypeDishRefService")
	private ISchemeTypeDishRefService sdrefService;
	@Resource(name="InventoryService")
	private IInventoryService inventoryService;
	@Resource(name="InventorySellLogService")
	private IInventorySellLogService sellLogService;
	@Resource(name="InventoryDishRefService")
	private IInventoryDishRefService idrservice;
	
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
//		BigDecimal a = new BigDecimal("161");
//		BigDecimal b = new BigDecimal("100");
//		System.out.println(a.divideToIntegralValue(b));
		//测试银行家算法
//		BigDecimal b = new BigDecimal("10.525");
//		System.out.println(b);
//		b = b.setScale(0, RoundingMode.HALF_EVEN);
//		System.out.println(b);
		BigDecimal a = new BigDecimal("26");
//		BigDecimal b = new BigDecimal("3000");
//		BigDecimal c = new BigDecimal("22");
//		BigDecimal d = a.divide(b).multiply(c);
//		System.out.println(d.setScale(0, RoundingMode.HALF_EVEN));
		System.out.println(a.negate());
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
//		s.setUnitCode("PC");
//		SchemeVO vo = beanMapper.map(s, SchemeVO.class);
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
	public void testDishSeriesDelete(){
		dishseriesService.deleteAll();
	}
	
	@Test
	public void testTransformDish(){
		String dishno="763";
		transformSevice.transformDishInfo(dishno);
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
	public void testGetValidOrderCount(){
		Long result = oaService.getValidOrderCount(DateUtil.parseDate("2015-07-29"), AccountCode.ELE);
		System.out.println(result);
	}
	
	@Test
	public void testPaymodeCodeEnum(){
		System.out.println(PaymodeCode.paymodeCode("18"));
		System.out.println(PaymodeCode.ELE.getPaymodeno());
	}
	
	@Test
	public void testQueryDishes(){
		List<DishVO> dishes = dishService.queryDishes(false);
		System.out.println(dishes.size());
		
	}
	
	@Test
	public void testInventoryConsume(){
		Inventory inventory = inventoryService.consume("002", new BigDecimal(9));
		InventorySellLog sellLog = new InventorySellLog();
		sellLog.setDay("20151127");
		sellLog.setCheckoutTime(DateUtil.getCurrentDate());
		sellLog.setDishno("362");
		sellLog.setConsumeAmount(new BigDecimal(9));
		sellLog.setIname(inventory.getIname());
		sellLog.setPayno("552255545552");
		sellLogService.rwCreate(sellLog);
	}
	
	@Test
	public void testInventoryDishRef(){
		List<InventoryDishRef> result =idrservice.queryByDishNo("544");
	}
	
	@Test
	public void testOthers(){
		System.out.println(Integer.MAX_VALUE);
	}
}
