package com.rci.ui.swing.handler;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.CollectionUtils;

import com.rci.enums.BusinessEnums.AccountCode;
import com.rci.service.IDataLoaderService;
import com.rci.service.IOrderAccountRefService;
import com.rci.service.IOrderService;
import com.rci.service.impl.OrderAccountRefServiceImpl.AccountSumResult;
import com.rci.tools.DateUtil;
import com.rci.tools.SpringUtils;
import com.rci.tools.StringUtils;
import com.rci.ui.swing.model.OrderItemTable.OrderItemTableModel;
import com.rci.ui.swing.model.OrderTable.OrderTableModel;
import com.rci.ui.swing.views.ConculsionPanel;
import com.rci.ui.swing.views.ContentPanel;
import com.rci.ui.swing.vos.OrderItemVO;
import com.rci.ui.swing.vos.OrderVO;

public class OrderDataLoader implements Runnable {
	private Date queryDate;
	
	private ContentPanel contentPane;
	
	private ConculsionPanel conclusionPane;
	
	private IDataLoaderService loaderService;
	
	private IOrderService orderService;
	
	private Map<AccountCode,BigDecimal> sumMap;
	
	public OrderDataLoader(Date queryDate){
		this.queryDate = queryDate;
	}
	
	@Override
	public void run() {
		//1.加载 order 数据
		loaderService = (IDataLoaderService) SpringUtils.getBean("DataLoaderService");
		loaderService.load(queryDate);
		IOrderService orderService = (IOrderService) SpringUtils.getBean("OrderService");
		String time = DateUtil.date2Str(queryDate, "yyyyMMdd");
		List<OrderVO> ordervos = orderService.accquireOrderVOsByDay(time);
		OrderTableModel otm = (OrderTableModel) contentPane.getMainTable().getModel();
		otm.setOrders(ordervos);
		otm.fireTableDataChanged();
		if(!CollectionUtils.isEmpty(ordervos)){
			OrderVO order = otm.getOrderAt(0);
			contentPane.getMainTable().setRowSelectionAllowed(true);
			contentPane.getMainTable().setRowSelectionInterval(0, 0);
			loadItemData(order.getPayNo());
			for(OrderVO ov:ordervos){
				if(StringUtils.hasText(ov.getWarningInfo())){
					contentPane.getTextArea().append("【"+ov.getPayNo()+"】"+ov.getWarningInfo()+"\n");
				}
			}
		}
		//2. 根据订单数据统计今日收入明细
		loadSumData(queryDate);
		conclusionPane.refreshUI();
	}
	
	/**
	 * 
	 * Describle(描述)：装载 order Item 数据
	 *
	 * 方法名称：loadItemData
	 *
	 * 所在类名：OrderDataLoader
	 *
	 * Create Time:2015年8月14日 下午3:01:44
	 *  
	 * @param payno
	 */
	public void loadItemData(String payno){
		orderService = (IOrderService) SpringUtils.getBean("OrderService");
		List<OrderItemVO> orderItems = orderService.queryOrderItemVOsByPayno(payno);
		OrderItemTableModel oitm = (OrderItemTableModel) contentPane.getItemTable().getModel();
		oitm.setItems(orderItems);
		oitm.fireTableDataChanged();
	}
	
	/**
	 * 
	 * Describle(描述)：计算每日收入统计信息
	 *
	 * 方法名称：loadSumData
	 *
	 * 所在类名：OrderDataLoader
	 *
	 * Create Time:2015年8月14日 下午3:08:39
	 *  
	 * @param time
	 */
	private void loadSumData(Date date){
		sumMap = new HashMap<AccountCode,BigDecimal>();
		IOrderAccountRefService oaService = (IOrderAccountRefService) SpringUtils.getBean("OrderAccountRefService");
		List<AccountSumResult> sumRes = oaService.querySumAmount(date);
		for(AccountSumResult res:sumRes){
			AccountCode accNo = res.getAccNo();
			BigDecimal amount = res.getSumAmount();
			sumMap.put(accNo, amount);
		}
		conclusionPane.setQueryDate(date);
		conclusionPane.setSumMap(sumMap);
	}
	
	public Date getQueryDate() {
		return queryDate;
	}
	public void setQueryDate(Date queryDate) {
		this.queryDate = queryDate;
	}
	public ContentPanel getContentPane() {
		return contentPane;
	}
	public void setContentPane(ContentPanel contentPane) {
		this.contentPane = contentPane;
	}


	public ConculsionPanel getConclusionPane() {
		return conclusionPane;
	}


	public void setConclusionPane(ConculsionPanel conclusionPane) {
		this.conclusionPane = conclusionPane;
	}

}