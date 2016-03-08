package com.rci.service.facade;

import java.text.ParseException;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rci.bean.dto.SaleLogQueryDTO;
import com.rci.enums.BusinessEnums.DataGenerateType;
import com.rci.service.IAccFlowService;
import com.rci.service.IELESDStatisticService;
import com.rci.service.IFetchMarkService;
import com.rci.service.IOrderAccountRefService;
import com.rci.service.IOrderService;
import com.rci.service.IStatisticRecordService;
import com.rci.service.ITicketInfoService;
import com.rci.service.ITicketStatisticService;
import com.rci.service.inventory.IInventorySellLogService;
import com.rci.tools.DateUtil;

@Service("DataCleanFacade")
public class DataCleanFacadeImpl implements DataCleanFacade {
	@Resource(name="OrderService")
	private IOrderService orderService;
	
	@Resource(name="ELESDStatisticService")
	private IELESDStatisticService elesdService;
	
	@Resource(name="TicketStatisticService")
	private ITicketStatisticService tsService;
	
	@Resource(name="TicketInfoService")
	private ITicketInfoService ticketService;
	@Resource(name="AccFlowService")
	private IAccFlowService accFlowService;
	@Resource(name="FetchMarkService")
	private IFetchMarkService markService;
	
	@Resource(name="InventorySellLogService")
	private IInventorySellLogService saleLogService;
	
	@Resource(name="StatisticRecordService")
	private IStatisticRecordService srService;
	
	@Resource(name="OrderAccountRefService")
	private IOrderAccountRefService oarService;
	

	@Override
	public void deleteOrders(String time) {
//		orderService.deleteOrders(time);
		orderService.doDeleteOrders(time);
	}

	@Override
	public void deleteMark(String time) {
//		markService.deleteMark(time);
		markService.doDeleteMark(time);
	}

	@Override
	public void deleteFlowInfo(String time, DataGenerateType type) {
		accFlowService.rwDeleteFlowInfo(time, DataGenerateType.AUTO);
	}

	@Deprecated
	@Override
	public void deleteTicketStatistic(String time) {
//		tsService.deleteTicketStatistic(time);
	}

	@Deprecated
	@Override
	public void deleteELESDInfo(String time) {
//		try {
//			elesdService.clearDataByDate(DateUtil.parseDate(time, "yyyyMMdd"));
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
	}

	@Override
	public void doCleanAllOfOneDay(String time) {
		deleteAccountAmount(time);
		deleteOrders(time);
		deleteMark(time);
		//		deleteFlowInfo(time,DataGenerateType.AUTO);
		//		deleteTicketStatistic(time);
		deleteTicketInfo(time);
		//		deleteELESDInfo(time);
		//		deleteStockInfo(time);
		deleteInventoryInfo(time);
		deleteStatisticRecord(time);
	}

	@Override
	public void deleteTicketInfo(String time) {
//		ticketService.deleteTicketStatistic(time);
		ticketService.doDeleteTicketStatistic(time);
	}

	@Override
	public void deleteInventoryInfo(String time) {
		SaleLogQueryDTO queryDTO = new SaleLogQueryDTO();
		queryDTO.setDay(time);
		saleLogService.rollbackLog(queryDTO);
	}
	
	@Override
	public void deleteStatisticRecord(String time){
		try {
			Date date = DateUtil.parseDate(time, "yyyyMMdd");
			srService.doDeleteByDate(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public void deleteAccountAmount(String time){
		try {
			Date date = DateUtil.parseDate(time, "yyyyMMdd");
			oarService.doRollbackAccount(date);
		} catch (ParseException e) {
		}
	}
}
