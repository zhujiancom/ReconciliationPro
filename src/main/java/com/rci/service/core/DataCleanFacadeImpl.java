package com.rci.service.core;

import java.text.ParseException;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rci.bean.dto.SaleLogQueryDTO;
import com.rci.enums.BusinessEnums.DataGenerateType;
import com.rci.service.IAccFlowService;
import com.rci.service.IELESDStatisticService;
import com.rci.service.IFetchMarkService;
import com.rci.service.IOrderService;
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
	

	@Override
	public void deleteOrders(String time) {
		orderService.deleteOrders(time);
	}

	@Override
	public void deleteMark(String time) {
		markService.deleteMark(time);
	}

	@Override
	public void deleteFlowInfo(String time, DataGenerateType type) {
		accFlowService.rwDeleteFlowInfo(time, DataGenerateType.AUTO);
	}

	@Deprecated
	@Override
	public void deleteTicketStatistic(String time) {
		tsService.deleteTicketStatistic(time);
	}

	@Override
	public void deleteELESDInfo(String time) {
		try {
			elesdService.clearDataByDate(DateUtil.parseDate(time, "yyyyMMdd"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void cleanAllOfOneDay(String time) {
		deleteOrders(time);
		deleteMark(time);
		deleteFlowInfo(time,DataGenerateType.AUTO);
//		deleteTicketStatistic(time);
		deleteTicketInfo(time);
		deleteELESDInfo(time);
//		deleteStockInfo(time);
		deleteInventoryInfo(time);
	}

	@Override
	public void deleteTicketInfo(String time) {
		ticketService.deleteTicketStatistic(time);
	}

	@Override
	public void deleteInventoryInfo(String time) {
		SaleLogQueryDTO queryDTO = new SaleLogQueryDTO();
		queryDTO.setDay(time);
		saleLogService.rollbackLog(queryDTO);
	}

}
