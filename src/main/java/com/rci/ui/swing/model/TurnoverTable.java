package com.rci.ui.swing.model;

import java.util.Collections;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumnModel;

import com.rci.tools.DateUtil;
import com.rci.ui.swing.vos.TurnoverVO;

public class TurnoverTable extends JTable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8950690637325143110L;

	public TurnoverTable(int columnNum){
		super(new TurnoverTableModel(columnNum));
		setHeaderLabel();
	}
	
	public void setHeaderLabel(){
		TableColumnModel cm = this.getColumnModel();
		cm.getColumn(0).setHeaderValue("日期");
		cm.getColumn(0).setMinWidth(75);
		cm.getColumn(1).setHeaderValue("收银机");
		cm.getColumn(1).setMinWidth(105);
		cm.getColumn(2).setHeaderValue("银联刷卡");
		cm.getColumn(2).setMinWidth(105);
		cm.getColumn(3).setHeaderValue("饿了么");
		cm.getColumn(3).setMinWidth(105);
		cm.getColumn(4).setHeaderValue("饿了么补贴");
		cm.getColumn(4).setMinWidth(105);
		cm.getColumn(5).setHeaderValue("点评团购券");
		cm.getColumn(5).setMinWidth(125);
		cm.getColumn(6).setHeaderValue("点评闪惠");
		cm.getColumn(6).setMinWidth(105);
		cm.getColumn(7).setHeaderValue("美团团购券");
		cm.getColumn(7).setMinWidth(125);
		cm.getColumn(8).setHeaderValue("美团超券");
		cm.getColumn(8).setMinWidth(105);
		cm.getColumn(9).setHeaderValue("淘点点");
		cm.getColumn(9).setMinWidth(105);
		cm.getColumn(10).setHeaderValue("店内优惠");
		cm.getColumn(10).setMinWidth(105);
		cm.getColumn(11).setHeaderValue("在线优惠");
		cm.getColumn(11).setMinWidth(105);
	}

	public static class TurnoverTableModel extends AbstractTableModel{
		/**
		 * 
		 */
		private static final long serialVersionUID = -5607847833630941480L;
		private int columnNum;
		private List<TurnoverVO> turnovers = Collections.emptyList();
		
		public TurnoverTableModel(int columnNum){
			this.columnNum = columnNum;
		}
		
		@Override
		public int getRowCount() {
			return turnovers.size();
		}

		@Override
		public int getColumnCount() {
			return columnNum;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			TurnoverVO turnover = turnovers.get(rowIndex);
			switch(columnIndex){
			case 0:
				return DateUtil.date2Str(turnover.getDate());
			case 1:
				return "<html><font color='red'>"+turnover.getCashMachineAmount()+"</font></html>";
			case 2:
				return "<html><font color='red'>"+turnover.getPosAmount()+"</font></html>";
			case 3:
				return "<html><font color='red'>"+turnover.getEleAmount()+"</font></html>";
			case 4:
				return "<html><font color='red'>"+turnover.getElebtAmount()+"</font></html>";
			case 5:
				return "<html><font color='red'>"+turnover.getDptgAmount()+"</font></html>";
			case 6:
				return "<html><font color='red'>"+turnover.getDpshAmount()+"</font></html>";
			case 7:
				return "<html><font color='red'>"+turnover.getMtAmount()+"</font></html>";
			case 8:
				return "<html><font color='red'>"+turnover.getMtSuperAmount()+"</font></html>";
			case 9:
				return "<html><font color='red'>"+turnover.getTddAmount()+"</font></html>";
			case 10:
				return "<html><font color='green'>"+turnover.getTsFreeAmount()+"</font></html>";
			case 11:
				return "<html><font color='green'>"+turnover.getOnlineFreeAmount()+"</font></html>";
			}
			return null;
		}
		
	}
	
}
