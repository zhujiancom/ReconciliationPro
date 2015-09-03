package com.rci.ui.swing.model;

import java.awt.Color;
import java.util.Collections;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumnModel;

import com.rci.ui.swing.renderers.AbstractLineColorMarkRenderer;
import com.rci.ui.swing.vos.TurnoverVO;

public class TurnoverTable extends JTable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8950690637325143110L;

	public TurnoverTable(int columnNum){
		super(new TurnoverTableModel(columnNum));
		setHeaderLabel();
		this.setRowHeight(30);
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
		cm.getColumn(5).setHeaderValue("饿了么刷单补贴");
		cm.getColumn(5).setMinWidth(215);
		cm.getColumn(6).setHeaderValue("点评团购券");
		cm.getColumn(6).setMinWidth(125);
		cm.getColumn(7).setHeaderValue("点评闪惠");
		cm.getColumn(7).setMinWidth(105);
		cm.getColumn(8).setHeaderValue("美团团购券");
		cm.getColumn(8).setMinWidth(125);
		cm.getColumn(9).setHeaderValue("美团超券");
		cm.getColumn(9).setMinWidth(105);
		cm.getColumn(10).setHeaderValue("支付宝");
		cm.getColumn(10).setMinWidth(105);
		cm.getColumn(11).setHeaderValue("店内优惠");
		cm.getColumn(11).setMinWidth(105);
		cm.getColumn(12).setHeaderValue("在线优惠");
		cm.getColumn(12).setMinWidth(105);
		cm.getColumn(13).setHeaderValue("当日总收入");
		cm.getColumn(13).setMinWidth(225);
	}
	
	public void makeStatisticRowFace(){
		TurnoverTableStatisticColorMarkRenderer redmarkRenderer = new TurnoverTableStatisticColorMarkRenderer();
		redmarkRenderer.setColor(Color.blue);
		TableColumnModel cm = this.getColumnModel();
		for(int i=0;i<this.getColumnCount();i++){
			cm.getColumn(i).setCellRenderer(redmarkRenderer);
		}
	}
	
	private class TurnoverTableStatisticColorMarkRenderer extends AbstractLineColorMarkRenderer<TurnoverTableModel>{

		/**
		 * 
		 */
		private static final long serialVersionUID = 4436771966347867353L;

		@Override
		public boolean markColor(TurnoverTableModel tm, int rowIndex) {
			if(rowIndex == tm.getRowCount()-1){
				return true;
			}
			return false;
		}
		
		
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
			if(rowIndex == getRowCount()-1){
				switch(columnIndex){
				case 0:
					return turnover.getDisplayTitle();
				case 1:
					return turnover.getCashMachineAmount();
				case 2:
					return turnover.getPosAmount();
				case 3:
					return turnover.getEleAmount();
				case 4:
					return turnover.getElebtAmount();
				case 5:
					return turnover.getElesdAmount();
				case 6:
					return turnover.getDptgAmount();
				case 7:
					return turnover.getDpshAmount();
				case 8:
					return turnover.getMtAmount();
				case 9:
					return turnover.getMtSuperAmount();
				case 10:
					return turnover.getAliPayAmount();
				case 11:
					return turnover.getTsFreeAmount();
				case 12:
					return turnover.getOnlineFreeAmount();
				case 13:
					return turnover.getTotalAmount();
				}
			}else{
				switch(columnIndex){
				case 0:
					return turnover.getDisplayTitle();
				case 1:
					return "<html><font color='red'>"+turnover.getCashMachineAmount()+"</font></html>";
				case 2:
					return "<html><font color='red'>"+turnover.getPosAmount()+"</font></html>";
				case 3:
					return "<html><font color='red'>"+turnover.getEleAmount()+"</font></html>";
				case 4:
					return "<html><font color='red'>"+turnover.getElebtAmount()+"</font></html>";
				case 5:
					return "<html><font color='red'>"+turnover.getElesdAmount()+"</font></html>";
				case 6:
					return "<html><font color='red'>"+turnover.getDptgAmount()+"</font></html>";
				case 7:
					return "<html><font color='red'>"+turnover.getDpshAmount()+"</font></html>";
				case 8:
					return "<html><font color='red'>"+turnover.getMtAmount()+"</font></html>";
				case 9:
					return "<html><font color='red'>"+turnover.getMtSuperAmount()+"</font></html>";
				case 10:
					return "<html><font color='red'>"+turnover.getAliPayAmount()+"</font></html>";
				case 11:
					return "<html><font color='green'>"+turnover.getTsFreeAmount()+"</font></html>";
				case 12:
					return "<html><font color='green'>"+turnover.getOnlineFreeAmount()+"</font></html>";
				case 13:
					return turnover.getTotalAmount();
				}
			}
			
			return null;
		}

		/**
		 * @return the columnNum
		 */
		public int getColumnNum() {
			return columnNum;
		}

		/**
		 * @param columnNum the columnNum to set
		 */
		public void setColumnNum(int columnNum) {
			this.columnNum = columnNum;
		}

		/**
		 * @return the turnovers
		 */
		public List<TurnoverVO> getTurnovers() {
			return turnovers;
		}

		/**
		 * @param turnovers the turnovers to set
		 */
		public void setTurnovers(List<TurnoverVO> turnovers) {
			this.turnovers = turnovers;
		}
		
	}
	
}
