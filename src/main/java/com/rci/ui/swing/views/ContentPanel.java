package com.rci.ui.swing.views;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

import com.rci.exceptions.ExceptionConstant.SERVICE;
import com.rci.exceptions.ExceptionManage;
import com.rci.service.facade.DataCleanFacade;
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
//	private FixedOrderTable mainTable; //展示order 列表
	private OrderTable mainTable;
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
		this.setResizeWeight(0.6);
		this.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.LIGHT_GRAY));
		JScrollPane mainScrollPane = new JScrollPane(); //将表格加入到滚动条组件中
		mainScrollPane.setBorder(BorderFactory.createEmptyBorder());
		mainScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		mainTable = new OrderTable(11);
		mainScrollPane.setViewportView(mainTable);
		mainScrollPane.getViewport().setBackground(Color.WHITE);
		
		/**
		 * 使用固定列表格，排序有问题
		 */
//		mainTable = new FixedOrderTable(2, 22);
//		JTable fixedTable = mainTable.getFixedTable();
//		JTable flexibleTable = mainTable.getFlexibleTable();
//		JScrollPane mainScrollPane = new JScrollPane(flexibleTable);
//		mainScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//		mainScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
//		mainScrollPane.setCorner(JScrollPane.UPPER_LEFT_CORNER, fixedTable.getTableHeader());  
//		mainScrollPane.setRowHeaderView(fixedTable);
//		mainScrollPane.getViewport().setBackground(Color.WHITE);
//		mainScrollPane.getRowHeader().setBackground(Color.WHITE);
		
		JPanel rightPane = new JPanel();
		rightPane.setLayout(new BoxLayout(rightPane, BoxLayout.Y_AXIS));
		JScrollPane rTopScrollPane = new JScrollPane(); //将表格加入到滚动条组件中
		rTopScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		rTopScrollPane.setBorder(BorderFactory.createEmptyBorder());
		itemTable = new OrderItemTable(7);
		rTopScrollPane.setViewportView(itemTable);
		rTopScrollPane.getViewport().setBackground(Color.WHITE);
		
		textArea = new JTextArea(10,0);
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setForeground(Color.RED);
		JScrollPane rBottomScrollPane = new JScrollPane(textArea); //将表格加入到滚动条组件中
		rBottomScrollPane.setBorder(BorderFactory.createEmptyBorder());
		rBottomScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		rBottomScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		rightPane.add(rTopScrollPane);
		rightPane.add(rBottomScrollPane);
		
		this.add(mainScrollPane);
		this.add(rightPane);
	}
	
	public void clearData(String time){
		if(!StringUtils.hasText(time)){
			ExceptionManage.throwServiceException(SERVICE.TIME_FORMAT, "请填写日期");
		}
		if(!DateUtil.isDateFormat(time,"yyyyMMdd")){
			ExceptionManage.throwServiceException(SERVICE.TIME_FORMAT, "日期格式错误");
		}
		textArea.setText("");
		if(mainTable.getModel() instanceof OrderTableModel){
			OrderTableModel orderModel = (OrderTableModel) mainTable.getModel();
			orderModel.setRowCount(0);
//			OrderTableModel fixedOrderModel = (OrderTableModel) mainTable.getFixedTable().getModel();
//			fixedOrderModel.setRowCount(0);
//			OrderTableModel flexibleOrderModel = (OrderTableModel) mainTable.getFlexibleTable().getModel();
//			flexibleOrderModel.setRowCount(0);
		}
		if(itemTable.getModel() instanceof OrderItemTableModel){
			OrderItemTableModel itemModel = (OrderItemTableModel) itemTable.getModel();
			itemModel.setRowCount(0);
		}
		DataCleanFacade datacleaner = (DataCleanFacade) SpringUtils.getBean("DataCleanFacade");
		datacleaner.doCleanAllOfOneDay(time);
	}


//	public FixedOrderTable getMainTable() {
//		return mainTable;
//	}
//
//	public void setMainTable(FixedOrderTable mainTable) {
//		this.mainTable = mainTable;
//	}

	public OrderTable getMainTable() {
		return mainTable;
	}

	public void setMainTable(OrderTable mainTable) {
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
