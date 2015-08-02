package com.rci.ui.swing.model;

import java.util.Collections;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.rci.tools.DateUtil;
import com.rci.ui.swing.vos.OrderVO;

public class OrderTableModel extends AbstractTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4006879882193678115L;
	private List<OrderVO> orders = Collections.emptyList();
	private int columnNum;
	
	public OrderTableModel(int columnNum){
		this.columnNum = columnNum;
	}
	
	public OrderTableModel(List<OrderVO> orders){
		this.orders = orders;
	}
	
	@Override
	public int getRowCount() {
		return orders.size();
	}

	@Override
	public int getColumnCount() {
		return this.columnNum;
	}
	
	public OrderVO getOrderAt(int rowIndex){
		return orders.get(rowIndex);
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		OrderVO order = orders.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return ++rowIndex;
		case 1:
			return order.getTableName();
		case 2:
			return order.getPayNo();
		case 3:
			return order.getOriginAmount();
		case 4:
			return order.getActualAmount();
		case 5:
			return order.getNodiscountAmount();
		case 6:
			return order.getSchemeName();
//		case 7:
//			return order.getSingleDiscount();
		case 7:
			return DateUtil.getTimeStampOfDate(order.getCheckoutTime());
		case 8:
			return order.getCashmachineAmount();
		case 9:
			return order.getMtAmount();
		case 10:
			return order.getDptgAmount();
		case 11:
			return order.getDpshAmount();
		case 12:
			return order.getEleAmount();
		case 13:
			return order.getEleFreeAmount();
		case 14:
			return order.getTddAmount();
//		case 16:
//			return order.getMtwmAmount();
//		case 17:
//			return order.getMtwmFreeAmount();
		case 15:
			return order.getMtSuperAmount();
		case 16:
			return order.getPosAmount();
		case 17:
			return order.getFreeAmount();
		case 18:
			return order.getTotalAmount();
		default:
			break;
		}
		return null;
	}
	
	public void setRowCount(int rowCount){
		int old = getRowCount();
		orders = Collections.emptyList();
		if(old > 0){
			super.fireTableRowsDeleted(rowCount,old-1);
		}
	}

	public List<OrderVO> getOrders() {
		return orders;
	}

	public void setOrders(List<OrderVO> orders) {
		this.orders = orders;
	}

}
