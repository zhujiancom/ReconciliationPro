package com.rci.ui.swing.views;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

import com.rci.enums.BusinessEnums.DataGenerateType;
import com.rci.exceptions.ExceptionConstant.SERVICE;
import com.rci.exceptions.ExceptionManage;
import com.rci.service.core.DataCleanFacade;
import com.rci.tools.DateUtil;
import com.rci.tools.SpringUtils;
import com.rci.tools.StringUtils;
import com.rci.ui.swing.model.OrderItemTable;
import com.rci.ui.swing.model.OrderItemTable.OrderItemTableModel;
import com.rci.ui.swing.model.OrderTable;
import com.rci.ui.swing.model.OrderTable.OrderTableModel;

public class ContentPanel extends JSplitPane {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6530007596448077812L;
	private JTable mainTable; //展示order 列表
	private JTable itemTable; //展示 orderItem 列表
	private JTextArea textArea; //警告日志展示面板
	
	public ContentPanel(int orientation,int width, int height){
		super(orientation,true);
		this.setSize(width, height);
		buildPane();
	}
	
	public void buildPane(){
		this.setDividerSize(5);
		this.setDividerLocation(0.6);
		JScrollPane mainScrollPane = new JScrollPane(); //将表格加入到滚动条组件中
		mainScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		mainTable = new OrderTable();
		mainScrollPane.setViewportView(mainTable);
		
		JPanel rightPane = new JPanel();
		rightPane.setLayout(new BoxLayout(rightPane, BoxLayout.Y_AXIS));
		JScrollPane rTopScrollPane = new JScrollPane(); //将表格加入到滚动条组件中
		rTopScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		itemTable = new OrderItemTable();
		rTopScrollPane.setViewportView(itemTable);
		
		textArea = new JTextArea(10,0);
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setForeground(Color.RED);
		JScrollPane rBottomScrollPane = new JScrollPane(textArea); //将表格加入到滚动条组件中
		rBottomScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		rBottomScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		rightPane.add(rTopScrollPane);
		rightPane.add(rBottomScrollPane);
		
		this.add(mainScrollPane);
		this.add(rightPane);
	}
	
	public void clearData(String time){
		textArea.setText("");
		if(mainTable.getModel() instanceof OrderTableModel){
			OrderTableModel orderModel = (OrderTableModel) mainTable.getModel();
			orderModel.setRowCount(0);
		}
		if(itemTable.getModel() instanceof OrderItemTableModel){
			OrderItemTableModel itemModel = (OrderItemTableModel) itemTable.getModel();
			itemModel.setRowCount(0);
		}
		if(!StringUtils.hasText(time)){
			ExceptionManage.throwServiceException(SERVICE.TIME_FORMAT, "请填写日期");
		}
		if(!DateUtil.isDateFormat(time,"yyyyMMdd")){
			ExceptionManage.throwServiceException(SERVICE.TIME_FORMAT, "日期格式错误");
		}
		DataCleanFacade datacleaner = (DataCleanFacade) SpringUtils.getBean("DataCleanFacade");
		datacleaner.deleteOrders(time);
		datacleaner.deleteMark(time);
		datacleaner.deleteFlowInfo(time,DataGenerateType.AUTO);
		datacleaner.deleteTicketStatistic(time);
		datacleaner.deleteELESDInfo(time);
		datacleaner.deleteStockInfo(time);
	}

	public JTable getMainTable() {
		return mainTable;
	}

	public void setMainTable(JTable mainTable) {
		this.mainTable = mainTable;
	}

	public JTable getItemTable() {
		return itemTable;
	}

	public void setItemTable(JTable itemTable) {
		this.itemTable = itemTable;
	}

	public JTextArea getTextArea() {
		return textArea;
	}

	public void setTextArea(JTextArea textArea) {
		this.textArea = textArea;
	}
	
	public void setDimension(int width,int height){
		this.setSize(width, height);
	}
}
