package com.rci.ui.swing.handler;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.springframework.util.CollectionUtils;

import com.rci.enums.BusinessEnums.AccountCode;
import com.rci.exceptions.ServiceException;
import com.rci.service.IDataLoaderService;
import com.rci.service.IOrderAccountRefService;
import com.rci.service.IOrderService;
import com.rci.service.impl.OrderAccountRefServiceImpl.AccountSumResult;
import com.rci.tools.DateUtil;
import com.rci.tools.SpringUtils;
import com.rci.tools.StringUtils;
import com.rci.ui.swing.model.OrderItemTable;
import com.rci.ui.swing.model.OrderItemTable.OrderItemTableModel;
import com.rci.ui.swing.model.OrderTable.OrderTableModel;
import com.rci.ui.swing.views.ConculsionPanel;
import com.rci.ui.swing.views.ContentPanel;
import com.rci.ui.swing.views.QueryFormPanel;
import com.rci.ui.swing.vos.OrderItemVO;
import com.rci.ui.swing.vos.OrderVO;

public class OrderDataLoader implements Runnable {
	private Date queryDate;
	
//	private Set<PaymodeCode> paymodes;
	
	private ContentPanel contentPane;
	
	private ConculsionPanel conclusionPane;
	
	private QueryFormPanel queryPane;
	
	private IDataLoaderService loaderService;
	
	private IOrderService orderService;
	
//	private Map<String,BigDecimal> sumMap;
	
	private Map<AccountCode,BigDecimal> dailyPostAccountMap;
	
	public OrderDataLoader(Date queryDate){
		this.queryDate = queryDate;
	}
	
	@Override
	public void run() {
		long startTime = System.currentTimeMillis();
		try{
			SwingUtilities.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					queryPane.displayInfoLoading("正在查询，请稍后。。。");
				}
			});
			//1.加载 order 数据
			loaderService = (IDataLoaderService) SpringUtils.getBean("DBDataLoaderService");
			loaderService.load(queryDate);
			IOrderService orderService = (IOrderService) SpringUtils.getBean("OrderService");
			String time = DateUtil.date2Str(queryDate, "yyyyMMdd");
			List<OrderVO> ordervos = orderService.accquireOrderVOsByDay(time);
			
//			List<OrderVO> displayOrders = new ArrayList<OrderVO>();
			final StringBuffer warningInfo = new StringBuffer();
			int count = 0;
			for(OrderVO ordervo:ordervos){
				if(StringUtils.hasText(ordervo.getWarningInfo())){
					warningInfo.append(++count).append(". ")
								.append("[").append(ordervo.getPayNo()).append("]-")
								.append(ordervo.getWarningInfo())
								.append("\n");
				}
//				displayOrders.add(ordervo);
			}
			
			OrderTableModel otm = (OrderTableModel) contentPane.getMainTable().getModel();
	//		OrderTableModel otm = (OrderTableModel) contentPane.getMainTable().getFixedTable().getModel();
			OrderItemTableModel ottm = (OrderItemTableModel) contentPane.getItemTable().getModel();
			if(!CollectionUtils.isEmpty(ordervos)){
				contentPane.getMainTable().reflushTable(ordervos);
	//			((FixedOrderTable)contentPane.getMainTable()).reflushTable(displayOrders);
				OrderVO order = otm.getOrderAt(0);
				((OrderItemTable)contentPane.getItemTable()).reflushTable(order.getPayNo());
				//2. 根据订单数据统计今日收入明细
				loadSumData(queryDate);
				conclusionPane.refreshUI();
				SwingUtilities.invokeLater(new Runnable() {
					
					@Override
					public void run() {
						queryPane.displayInfoDone("查询完毕");
						contentPane.getTextArea().setText(warningInfo.toString());
					}
				});
			}else{
				otm.setRowCount(0);
				ottm.setRowCount(0);
				SwingUtilities.invokeLater(new Runnable() {
				
					@Override
					public void run() {
						conclusionPane.clearData();
						queryPane.displayInfoDone("没有记录！");
					}
				});
			}
		}catch(ServiceException se){
			JOptionPane.showMessageDialog(null, se.getMessage());
			SwingUtilities.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					queryPane.displayErrorInfo("订单加载失败");
				}
			});
		}finally{
			long timeoffset = System.currentTimeMillis()-startTime;
			System.out.println("--- 运行时间： "+timeoffset/1000);
		}
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
		dailyPostAccountMap = new HashMap<AccountCode,BigDecimal>();
		IOrderAccountRefService oaService = (IOrderAccountRefService) SpringUtils.getBean("OrderAccountRefService");
		List<AccountSumResult> sumRes = oaService.querySumAmount(date);
		for(AccountSumResult res:sumRes){
			String accNo = res.getAccNo();
			BigDecimal amount = res.getSumAmount();
			dailyPostAccountMap.put(AccountCode.valueOf(accNo), amount);
		}
		conclusionPane.setQueryDate(date);
//		conclusionPane.setSumMap(sumMap);
		conclusionPane.setDailyPostAccountMap(dailyPostAccountMap);
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

	public QueryFormPanel getQueryPane() {
		return queryPane;
	}

	public void setQueryPane(QueryFormPanel queryPane) {
		this.queryPane = queryPane;
	}

}
