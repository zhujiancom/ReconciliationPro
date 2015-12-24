package com.rci.ui.swing.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.rci.bean.entity.EleSDStatistic;
import com.rci.enums.BusinessEnums.PaymodeCode;
import com.rci.exceptions.ExceptionConstant.SERVICE;
import com.rci.exceptions.ExceptionManage;
import com.rci.exceptions.ServiceException;
import com.rci.service.IELESDStatisticService;
import com.rci.service.IOrderService;
import com.rci.service.core.IMetadataService;
import com.rci.tools.DateUtil;
import com.rci.tools.SpringUtils;
import com.rci.tools.StringUtils;
import com.rci.ui.swing.handler.OrderDataLoader;
import com.rci.ui.swing.model.OrderItemTable;
import com.rci.ui.swing.model.OrderItemTable.OrderItemTableModel;
import com.rci.ui.swing.views.ConculsionPanel;
import com.rci.ui.swing.views.ContentPanel;
import com.rci.ui.swing.views.QueryFormPanel;
import com.rci.ui.swing.vos.OrderItemVO;

public class QueryListener implements ActionListener,ListSelectionListener{
	private static final Log logger = LogFactory.getLog(QueryListener.class);
	private ContentPanel contentPane;
	private ConculsionPanel conclusionPane;
	private String time;
	private QueryFormPanel queryPanel;
	private Set<PaymodeCode> paymodes;
	IMetadataService metaService;
	
	public QueryListener(QueryFormPanel queryPanel,ContentPanel contentPane){
		this.queryPanel =queryPanel;
		this.contentPane = contentPane;
		metaService = (IMetadataService) SpringUtils.getBean("MetadataService");
		fireWarningInfoDisplay();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		time = queryPanel.getTimeInput().getText();
		paymodes = queryPanel.getPaymodes();
		try{
			loadOrderData(time);
//			saveEleSDInfo();
			contentPane.getMainTable().getSelectionModel().addListSelectionListener(this);
			fireWarningInfoDisplay();
		}catch(ServiceException se){
			JOptionPane.showMessageDialog(null, new JLabel("<html><font color='red'>"+se.getMessage()+"</font></html>"));
		}catch (ParseException pe) {
			JOptionPane.showMessageDialog(null, pe.getMessage());
		}catch(Exception ex){
			logger.error(ex);
		}
	}
	
	/**
	 * 
	 * Describle(描述)：保存刷单信息
	 *
	 * 方法名称：saveEleSDInfo
	 *
	 * 所在类名：QueryListener
	 *
	 * Create Time:2015年8月14日 上午10:34:17
	 *
	 */
	public void saveEleSDInfo(){
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
		/* 保存饿了么刷单信息  */
		try {
			Date date = DateUtil.parseDate(time,"yyyyMMdd");
			IELESDStatisticService elesdService = (IELESDStatisticService) SpringUtils.getBean("ELESDStatisticService");
			EleSDStatistic elesd = new EleSDStatistic();
			elesd.setPayAmount(elePayAmount);
			elesd.setSdCount(eleOCount);
			elesd.setPerAllowanceAmount(eleAllowance);
			elesd.setSdDate(date);
			elesdService.saveSDInfo(elesd);
		}
		catch (ParseException e) {
			e.printStackTrace();
		}
	}

	/**
	 *  选中订单后自动加载该订单下的详细菜品消费信息
	 */
	@Override
	public void valueChanged(ListSelectionEvent event) {
		if(event.getSource() == contentPane.getMainTable().getSelectionModel()
				&& contentPane.getMainTable().getRowSelectionAllowed()){
			int row = contentPane.getMainTable().getSelectedRow();
			if(row != -1){
				String payno = (String) contentPane.getMainTable().getValueAt(row, 2);
//				loadItemData(payno);
				((OrderItemTable)contentPane.getItemTable()).reflushTable(payno);
			}
		}
	}
	
	/**
	 * 装载 order Item 数据
	 * @param payno
	 */
	public void loadItemData(String payno){
		IOrderService orderService = (IOrderService) SpringUtils.getBean("OrderService");
		List<OrderItemVO> orderItems = orderService.queryOrderItemVOsByPayno(payno);
		OrderItemTableModel oitm = (OrderItemTableModel) contentPane.getItemTable().getModel();
		oitm.setItems(orderItems);
		oitm.fireTableDataChanged();
	}
	
	/**
	 * 装载 订单order 数据
	 * @param time
	 * @throws ServiceException
	 * @throws ParseException 
	 */
	public void loadOrderData(String time) throws ParseException,ServiceException{
		contentPane.getTextArea().setText("");
		if(!DateUtil.isDateFormat(time,"yyyyMMdd")){
			ExceptionManage.throwServiceException(SERVICE.TIME_FORMAT, "日期格式错误");
		}
		Date queryDate = DateUtil.parseDate(time,"yyyyMMdd");
		Date currentDate = DateUtil.getCurrentDate();
		if(queryDate.after(currentDate)){
			ExceptionManage.throwServiceException(SERVICE.TIME_FORMAT, "查询时间不能晚于当前时间");
		}
		OrderDataLoader dataLoader = new OrderDataLoader(queryDate,paymodes);
		dataLoader.setContentPane(contentPane);
		dataLoader.setConclusionPane(conclusionPane);
		dataLoader.setQueryPane(queryPanel);
		new Thread(dataLoader).start();
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
	
	public void fireWarningInfoDisplay(){
		if(metaService.hasSellOffWarningInfo()){
			queryPanel.displayWarningInfo();
		}
	}
}
