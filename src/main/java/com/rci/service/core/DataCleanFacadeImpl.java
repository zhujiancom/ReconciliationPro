package com.rci.service.core;

import java.text.ParseException;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rci.enums.BusinessEnums.DataGenerateType;
import com.rci.service.IAccFlowService;
import com.rci.service.IELESDStatisticService;
import com.rci.service.IFetchMarkService;
import com.rci.service.IOrderService;
import com.rci.service.IStockService;
import com.rci.service.ITicketStatisticService;
import com.rci.tools.DateUtil;

@Service("DataCleanFacade")
public class DataCleanFacadeImpl implements DataCleanFacade {
	@Resource(name="OrderService")
	private IOrderService orderService;
	
	@Resource(name="ELESDStatisticService")
	private IELESDStatisticService elesdService;
	
	@Resource(name="TicketStatisticService")
	private ITicketStatisticService tsService;
	@Resource(name="StockService")
	private IStockService stockService;
	@Resource(name="AccFlowService")
	private IAccFlowService accFlowService;
	@Resource(name="FetchMarkService")
	private IFetchMarkService markService;

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
	public void deleteStockInfo(String time) {
		stockService.clearStockByDay(time);
	}

}
