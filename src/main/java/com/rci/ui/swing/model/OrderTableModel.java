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
	
	public OrderTableModel(List<OrderVO> orders){
		this.orders = orders;
	}
	
	@Override
	public int getRowCount() {
		return orders.size();
	}

	@Override
	public int getColumnCount() {
		return 22;
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
		case 7:
			return order.getSingleDiscount();
		case 8:
			return DateUtil.time2Str(order.getCheckoutTime());
		case 9:
			return order.getCashmachineAmount();
		case 10:
			return order.getMtAmount();
		case 11:
			return order.getDptgAmount();
		case 12:
			return order.getDpshAmount();
		case 13:
			return order.getEleAmount();
		case 14:
			return order.getEleFreeAmount();
		case 15:
			return order.getTddAmount();
		case 16:
			return order.getMtwmAmount();
		case 17:
			return order.getMtwmFreeAmount();
		case 18:
			return order.getMtSuperAmount();
		case 19:
			return order.getPosAmount();
		case 20:
			return order.getFreeAmount();
		case 21:
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

}
