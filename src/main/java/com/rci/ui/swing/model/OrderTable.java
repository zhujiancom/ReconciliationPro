package com.rci.ui.swing.model;

import java.awt.Color;
import java.awt.Component;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

import com.rci.enums.CommonEnums.YOrN;
import com.rci.tools.DateUtil;
import com.rci.ui.swing.renderers.AbstractLineRedMarkRenderer;
import com.rci.ui.swing.vos.OrderVO;

public class OrderTable extends JTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4935140318205918006L;
	
	public OrderTable(){
		super(new OrderTableModel(20));
		setHeaderLabel();
	}
	
	public void setHeaderLabel(){
		OrderTableRedMarkRenderer redmarkRenderer = new OrderTableRedMarkRenderer();
		OrderTableZeroMarkRenderer zeromarkRenderer = new OrderTableZeroMarkRenderer();
		TableColumnModel cm = this.getColumnModel();
		cm.getColumn(0).setHeaderValue("序号");
//		cm.getColumn(0).setPreferredWidth(50);
		cm.getColumn(0).setMinWidth(50);
		cm.getColumn(0).setCellRenderer(redmarkRenderer);
		cm.getColumn(1).setHeaderValue("桌号");
//		cm.getColumn(1).setPreferredWidth(75);
		cm.getColumn(1).setMinWidth(75);
		cm.getColumn(1).setCellRenderer(redmarkRenderer);
		cm.getColumn(2).setHeaderValue("付款编号");
//		cm.getColumn(2).setPreferredWidth(105);
		cm.getColumn(2).setMinWidth(105);
		cm.getColumn(2).setCellRenderer(redmarkRenderer);
		cm.getColumn(3).setHeaderValue("原价");
//		cm.getColumn(3).setPreferredWidth(75);
		cm.getColumn(3).setMinWidth(75);
		cm.getColumn(3).setCellRenderer(redmarkRenderer);
		cm.getColumn(4).setHeaderValue("实收金额");
		cm.getColumn(4).setMinWidth(75);
		cm.getColumn(4).setCellRenderer(redmarkRenderer);
		cm.getColumn(5).setHeaderValue("不可打折金额");
		cm.getColumn(5).setMinWidth(105);
		cm.getColumn(5).setCellRenderer(redmarkRenderer);
		cm.getColumn(6).setHeaderValue("折扣方案");
		cm.getColumn(6).setMinWidth(215);
		cm.getColumn(6).setCellRenderer(redmarkRenderer);
//		cm.getColumn(6).setHeaderValue("有临时折扣方案");
//		cm.getColumn(6).setMinWidth(45);
//		cm.getColumn(6).setCellRenderer(redmarkRenderer);
		cm.getColumn(7).setHeaderValue("结账时间");
		cm.getColumn(7).setMinWidth(100);
		cm.getColumn(7).setCellRenderer(redmarkRenderer);
		cm.getColumn(8).setHeaderValue("收银机现金");
		cm.getColumn(8).setMinWidth(70);
		cm.getColumn(8).setCellRenderer(redmarkRenderer);
		cm.getColumn(9).setHeaderValue("美团入账");
		cm.getColumn(9).setMinWidth(70);
		cm.getColumn(9).setCellRenderer(redmarkRenderer);
		cm.getColumn(10).setHeaderValue("大众点评团购");
		cm.getColumn(10).setMinWidth(105);
		cm.getColumn(10).setCellRenderer(redmarkRenderer);
		cm.getColumn(11).setHeaderValue("大众点评闪惠");
		cm.getColumn(11).setMinWidth(105);
		cm.getColumn(11).setCellRenderer(redmarkRenderer);
		cm.getColumn(12).setHeaderValue("饿了么");
		cm.getColumn(12).setMinWidth(75);
		cm.getColumn(12).setCellRenderer(redmarkRenderer);
		cm.getColumn(13).setHeaderValue("饿了么补贴");
		cm.getColumn(13).setMinWidth(105);
		cm.getColumn(13).setCellRenderer(redmarkRenderer);
		cm.getColumn(14).setHeaderValue("淘点点");
		cm.getColumn(14).setMinWidth(75);
		cm.getColumn(14).setCellRenderer(redmarkRenderer);
//		cm.getColumn(16).setHeaderValue("美团外卖");
//		cm.getColumn(16).setPreferredWidth(75);
//		cm.getColumn(16).setCellRenderer(redmarkRenderer);
//		cm.getColumn(17).setHeaderValue("美团外卖补贴");
//		cm.getColumn(17).setPreferredWidth(105);
		cm.getColumn(15).setHeaderValue("美团超级代金券");
		cm.getColumn(15).setMinWidth(105);
		cm.getColumn(15).setCellRenderer(redmarkRenderer);
		cm.getColumn(16).setHeaderValue("POS机");
		cm.getColumn(16).setMinWidth(75);
		cm.getColumn(16).setCellRenderer(redmarkRenderer);
		cm.getColumn(17).setHeaderValue("堂食免单");
		cm.getColumn(17).setMinWidth(115);
		cm.getColumn(17).setCellRenderer(redmarkRenderer);
		cm.getColumn(18).setHeaderValue("在线免单");
		cm.getColumn(18).setMinWidth(115);
		cm.getColumn(18).setCellRenderer(redmarkRenderer);
		cm.getColumn(19).setHeaderValue("入账总金额");
		cm.getColumn(19).setMinWidth(75);
		cm.getColumn(19).setCellRenderer(zeromarkRenderer);
	}
	
	/**
	 * 
	 * remark (备注): 行标记红色 renderer
	 *
	 * @author zj
	 *	
	 * 项目名称：ReconciliationPro
	 *
	 * 类名称：OrderTableRedMarkRenderer
	 *
	 * 包名称：com.rci.ui.swing.model
	 *
	 * Create Time: 2015年7月27日 下午2:46:45
	 *
	 */
	private class OrderTableRedMarkRenderer extends AbstractLineRedMarkRenderer<OrderTableModel>{

		/**
		 * 
		 */
		private static final long serialVersionUID = 4436771966347867353L;

		@Override
		public boolean markRed(OrderTableModel tm, int rowIndex) {
			OrderVO order = tm.getOrderAt(rowIndex);
			if(YOrN.Y.equals(order.getUnusual())){
				return true;
			}
			return false;
		}
	}
	
	private class OrderTableZeroMarkRenderer extends DefaultTableCellRenderer{

		/**
		 * 
		 */
		private static final long serialVersionUID = 9016998600727653300L;

		@Override
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			OrderTableModel tm = (OrderTableModel) table.getModel();
			OrderVO order = tm.getOrderAt(row);
			boolean zeroFlag = order.getTotalAmount().compareTo(BigDecimal.ZERO) == 0?true:false;
			if(zeroFlag){
				setBackground(Color.ORANGE);
				setForeground(Color.WHITE);
			}else{
				setBackground(Color.WHITE);
			}
			if(zeroFlag && isSelected){
				table.setSelectionBackground(Color.ORANGE);
				table.setSelectionForeground(Color.WHITE);
			}else{
				table.setSelectionBackground(UIManager.getColor("Table.selectionBackground"));
				table.setSelectionForeground(UIManager.getColor("Table.selectionForeground"));
			}
			return super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
					row, column);
		}
	}

	public static class OrderTableModel extends AbstractTableModel {
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
//			case 7:
//				return order.getSingleDiscount();
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
//			case 16:
//				return order.getMtwmAmount();
//			case 17:
//				return order.getMtwmFreeAmount();
			case 15:
				return order.getMtSuperAmount();
			case 16:
				return order.getPosAmount();
			case 17:
				return order.getFreeAmount();
			case 18:
				return order.getOnlineFreeAmount();
			case 19:
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
}
