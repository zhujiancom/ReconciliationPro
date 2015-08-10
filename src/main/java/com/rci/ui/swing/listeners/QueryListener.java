package com.rci.ui.swing.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumnModel;

import org.springframework.util.CollectionUtils;

import com.rci.bean.entity.EleSDStatistic;
import com.rci.enums.BusinessEnums.AccountCode;
import com.rci.enums.BusinessEnums.Vendor;
import com.rci.exceptions.ExceptionConstant.SERVICE;
import com.rci.exceptions.ExceptionManage;
import com.rci.exceptions.ServiceException;
import com.rci.service.IDataLoaderService;
import com.rci.service.IELESDStatisticService;
import com.rci.service.IOrderAccountRefService;
import com.rci.service.IOrderService;
import com.rci.service.core.StatisticCenterFacade;
import com.rci.service.impl.OrderAccountRefServiceImpl.AccountSumResult;
import com.rci.tools.DateUtil;
import com.rci.tools.SpringUtils;
import com.rci.tools.StringUtils;
import com.rci.ui.swing.model.OrderItemTableModel;
import com.rci.ui.swing.model.OrderTable.OrderTableModel;
import com.rci.ui.swing.views.ConculsionPanel;
import com.rci.ui.swing.views.ContentPanel;
import com.rci.ui.swing.views.QueryFormPanel;
import com.rci.ui.swing.vos.OrderItemVO;
import com.rci.ui.swing.vos.OrderVO;

public class QueryListener implements ActionListener,ListSelectionListener {
	private JTable mainTable;
	private JTable subTable;
	private JTextArea textArea; //警告日志展示面板
	private ContentPanel contentPane;
	private ConculsionPanel conclusionPane;
	private IOrderService orderService;
	private IDataLoaderService loaderService;
	private StatisticCenterFacade facade;
	private IOrderAccountRefService oaService;
	private Map<AccountCode,BigDecimal> sumMap;
	private List<OrderVO> orders;
	private String time;
	
	private QueryFormPanel queryPanel;
	
	public QueryListener(ContentPanel contentPane){
		this.contentPane = contentPane;
		mainTable = contentPane.getMainTable();
		subTable = contentPane.getItemTable();
		textArea = contentPane.getTextArea();
		orderService = (IOrderService) SpringUtils.getBean("OrderService");
		loaderService = (IDataLoaderService) SpringUtils.getBean("DataLoaderService");
		facade = (StatisticCenterFacade) SpringUtils.getBean("StatisticCenterFacade");
		oaService = (IOrderAccountRefService) SpringUtils.getBean("OrderAccountRefService");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		time = queryPanel.getTimeInput().getText();
		String elePayAmountText = queryPanel.getEleOnlinePayAmount().getText();
		String eleOrderCountText = queryPanel.getEleOrderCount().getText();
		String elePerAllowanceText = queryPanel.getElePerAllowanceAmount().getText();
		BigDecimal elePayAmount = BigDecimal.ZERO;
		if(StringUtils.hasText(elePayAmountText)){
			elePayAmount = new BigDecimal(elePayAmountText);	
		}
		BigDecimal eleOCount = BigDecimal.ZERO;
		if(StringUtils.hasText(eleOrderCountText)){
			eleOCount = new BigDecimal(eleOrderCountText);
		}
		BigDecimal eleAllowance = BigDecimal.ZERO;
		if(StringUtils.hasText(elePerAllowanceText)){
			eleAllowance = new BigDecimal(elePerAllowanceText);
		}
		
		try{
			loadOrderData(time);
			loadSumData(time);
			/* 保存饿了么刷单信息  */
			Date date = DateUtil.parseDate(time,"yyyyMMdd");
			IELESDStatisticService elesdService = (IELESDStatisticService) SpringUtils.getBean("ELESDStatisticService");
			EleSDStatistic elesd = new EleSDStatistic();
			elesd.setPayAmount(elePayAmount);
			elesd.setSdCount(eleOCount);
			elesd.setPerAllowanceAmount(eleAllowance);
			elesd.setSdDate(date);
			elesdService.saveSDInfo(elesd);
			
			conclusionPane.updateUI(this);
			mainTable.getSelectionModel().addListSelectionListener(this);
		}catch(ServiceException se){
			JOptionPane.showMessageDialog(null, se.getMessage());
		}catch (ParseException pe) {
			JOptionPane.showMessageDialog(null, pe.getMessage());
		}
	}
	
	/**
	 * 
	 * Describle(描述)： 统计外送率
	 *
	 * 方法名称：getExpressRateStatistic
	 *
	 * 所在类名：QueryListener
	 *
	 * Create Time:2015年6月19日 下午4:39:11
	 *  
	 * @param time
	 * @return
	 */
	public String getExpressRateStatistic(String time){
		return facade.getExpressRate(time).toString()+"%";
	}

	/**
	 * 
	 * Describle(描述)： 统计代金券数量展示
	 *
	 * 方法名称：getTicketStatistic
	 *
	 * 所在类名：QueryListener
	 *
	 * Create Time:2015年6月19日 下午4:38:40
	 *  
	 * @param vendor
	 * @return
	 */
	public String getTicketStatistic(Date time,Vendor vendor) {
		return facade.getTicketStatistic(time, vendor);
	}

	@Override
	public void valueChanged(ListSelectionEvent event) {
		if(event.getSource() == mainTable.getSelectionModel()
				&& mainTable.getRowSelectionAllowed()){
			int row = mainTable.getSelectedRow();
			if(row != -1){
				String payno = (String) mainTable.getValueAt(row, 2);
				loadItemData(payno);
			}
		}
	}
	
	/**
	 * 装载 order Item 数据
	 * @param payno
	 */
	public void loadItemData(String payno){
		List<OrderItemVO> orderItems = orderService.queryOrderItemVOsByPayno(payno);
		OrderItemTableModel otm = new OrderItemTableModel(orderItems);
		subTable.setModel(otm);
		TableColumnModel cm = subTable.getColumnModel();
		cm.getColumn(0).setHeaderValue("菜品名称");
		cm.getColumn(0).setPreferredWidth(105);
		cm.getColumn(1).setHeaderValue("数量");
		cm.getColumn(1).setPreferredWidth(105);
		cm.getColumn(2).setHeaderValue("退菜数量");
		cm.getColumn(2).setPreferredWidth(75);
		cm.getColumn(3).setHeaderValue("原价");
		cm.getColumn(3).setPreferredWidth(75);
		cm.getColumn(4).setHeaderValue("实收金额");
		cm.getColumn(4).setPreferredWidth(75);
		cm.getColumn(5).setHeaderValue("折扣率");
		cm.getColumn(5).setPreferredWidth(115);
		cm.getColumn(6).setHeaderValue("消费时间");
		cm.getColumn(6).setPreferredWidth(140);
		subTable.setColumnModel(cm);
		subTable.setRowHeight(20);
		subTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	}
	
	/**
	 * 装载 订单order 数据
	 * @param time
	 * @throws ServiceException
	 * @throws ParseException 
	 */
	public void loadOrderData(String time) throws ServiceException{
		textArea.setText("");
		if(!DateUtil.isDateFormat(time,"yyyyMMdd")){
			ExceptionManage.throwServiceException(SERVICE.TIME_FORMAT, "日期格式错误");
		}
		Date queryDate = null;
		try {
			queryDate = DateUtil.parseDate(time,"yyyyMMdd");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar queryDay = Calendar.getInstance();
		queryDay.setTime(queryDate);
		Calendar currentDay = Calendar.getInstance();
		currentDay.setTime(DateUtil.getCurrentDate());
		if(queryDay.after(currentDay)){
			ExceptionManage.throwServiceException(SERVICE.TIME_FORMAT, "查询时间不能晚于当前时间");
		}
		
		loaderService.load(queryDate);
		List<OrderVO> ordervos = orderService.accquireOrderVOsByDay(time);
		this.orders = ordervos;
		OrderTableModel otm = (OrderTableModel) mainTable.getModel();
		otm.setOrders(orders);
		if(!CollectionUtils.isEmpty(ordervos)){
			OrderVO order = otm.getOrderAt(0);
			loadItemData(order.getPayNo());
			mainTable.setRowSelectionAllowed(true);
			mainTable.setRowSelectionInterval(0, 0);
			for(OrderVO ov:orders){
				if(StringUtils.hasText(ov.getWarningInfo())){
					textArea.append("【"+ov.getPayNo()+"】"+ov.getWarningInfo());
				}
			}
		}
		mainTable.setRowHeight(20);
		mainTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	}

	/**
	 * 
	 * Describle(描述)：统计一天的收入总额
	 *
	 * 方法名称：getTotalDayAmount
	 *
	 * 所在类名：QueryListener
	 *
	 * Create Time:2015年7月29日 上午10:44:52
	 *  
	 * @param time
	 * @return
	 */
	public BigDecimal getTotalDayAmount(String time){
		BigDecimal totalAmount = new BigDecimal(BigInteger.ZERO,2);
		if(CollectionUtils.isEmpty(orders)){
			return totalAmount;
		}
		for(OrderVO order:orders){
			totalAmount = totalAmount.add(order.getTotalAmount());
		}
		Date date;
		try {
			date = DateUtil.parseDate(time, "yyyyMMdd");
			BigDecimal allowanceAmount = facade.getSDAllowanceAmount(date);
			if(allowanceAmount != null){
				totalAmount = totalAmount.add(allowanceAmount);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return totalAmount;
	}
	
	/**
	 * 
	 * Describle(描述)： 获取饿了么刷单补贴金额
	 *
	 * 方法名称：getELESDAllowanceAmount
	 *
	 * 所在类名：QueryListener
	 *
	 * Create Time:2015年7月30日 下午1:58:46
	 *  
	 * @param date
	 * @return
	 */
	public BigDecimal getELESDAllowanceAmount(Date date){
		return facade.getSDAllowanceAmount(date);
	}
	
	public BigDecimal getTotalAmount(AccountCode accountNo){
		return sumMap.get(accountNo) == null? BigDecimal.ZERO:sumMap.get(accountNo);
	}
	
	/**
	 * 
	 * Describle(描述)： 获取平台有效单数
	 *
	 * 方法名称：getValidCount
	 *
	 * 所在类名：QueryListener
	 *
	 * Create Time:2015年7月30日 下午1:58:05
	 *  
	 * @param time
	 * @param vendor
	 * @return
	 */
	public Long getValidCount(String time,Vendor vendor){
		try {
			Date postTime = DateUtil.parseDate(time,"yyyyMMdd");
			return oaService.getValidOrderCount(postTime, AccountCode.valueOf(vendor.name()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0L;
	}
	
	private void loadSumData(String time){
		sumMap = new HashMap<AccountCode,BigDecimal>();
		try {
			List<AccountSumResult> sumRes = oaService.querySumAmount(DateUtil.parseDate(time, "yyyyMMdd"));
			for(AccountSumResult res:sumRes){
				AccountCode accNo = res.getAccNo();
				BigDecimal amount = res.getSumAmount();
				sumMap.put(accNo, amount);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public ConculsionPanel getConclusionPane() {
		return conclusionPane;
	}

	public void setConclusionPane(ConculsionPanel conclusionPane) {
		this.conclusionPane = conclusionPane;
	}

	public QueryFormPanel getQueryPanel() {
		return queryPanel;
	}

	public void setQueryPanel(QueryFormPanel queryPanel) {
		this.queryPanel = queryPanel;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public ContentPanel getContentPane() {
		return contentPane;
	}

	public void setContentPane(ContentPanel contentPane) {
		this.contentPane = contentPane;
	}
}
