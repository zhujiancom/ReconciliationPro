package com.rci.ui.swing.model;

import java.awt.Color;
import java.awt.Component;
import java.math.BigDecimal;

import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

import com.rci.enums.CommonEnums.YOrN;
import com.rci.ui.swing.renderers.AbstractLineRedMarkRenderer;
import com.rci.ui.swing.vos.OrderVO;

public class OrderTable extends JTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4935140318205918006L;
	
	public void setHeaderLabel(){
		OrderTableRedMarkRenderer redmarkRenderer = new OrderTableRedMarkRenderer();
		OrderTableZeroMarkRenderer zeromarkRenderer = new OrderTableZeroMarkRenderer();
		TableColumnModel cm = this.getColumnModel();
		cm.getColumn(0).setHeaderValue("序号");
		cm.getColumn(0).setPreferredWidth(50);
		cm.getColumn(0).setCellRenderer(redmarkRenderer);
		cm.getColumn(1).setHeaderValue("桌号");
		cm.getColumn(1).setPreferredWidth(75);
		cm.getColumn(1).setCellRenderer(redmarkRenderer);
		cm.getColumn(2).setHeaderValue("付款编号");
		cm.getColumn(2).setPreferredWidth(105);
		cm.getColumn(2).setCellRenderer(redmarkRenderer);
		cm.getColumn(3).setHeaderValue("原价");
		cm.getColumn(3).setPreferredWidth(75);
		cm.getColumn(3).setCellRenderer(redmarkRenderer);
		cm.getColumn(4).setHeaderValue("实收金额");
		cm.getColumn(4).setPreferredWidth(75);
		cm.getColumn(4).setCellRenderer(redmarkRenderer);
		cm.getColumn(5).setHeaderValue("不可打折金额");
		cm.getColumn(5).setPreferredWidth(75);
		cm.getColumn(5).setCellRenderer(redmarkRenderer);
		cm.getColumn(6).setHeaderValue("折扣方案");
		cm.getColumn(6).setPreferredWidth(175);
		cm.getColumn(6).setCellRenderer(redmarkRenderer);
		cm.getColumn(7).setHeaderValue("有临时折扣方案");
		cm.getColumn(7).setPreferredWidth(45);
		cm.getColumn(7).setCellRenderer(redmarkRenderer);
		cm.getColumn(8).setHeaderValue("结账时间");
		cm.getColumn(8).setPreferredWidth(140);
		cm.getColumn(8).setCellRenderer(redmarkRenderer);
		cm.getColumn(9).setHeaderValue("收银机现金");
		cm.getColumn(9).setPreferredWidth(70);
		cm.getColumn(9).setCellRenderer(redmarkRenderer);
		cm.getColumn(10).setHeaderValue("美团入账");
		cm.getColumn(10).setPreferredWidth(70);
		cm.getColumn(10).setCellRenderer(redmarkRenderer);
		cm.getColumn(11).setHeaderValue("大众点评团购");
		cm.getColumn(11).setPreferredWidth(105);
		cm.getColumn(11).setCellRenderer(redmarkRenderer);
		cm.getColumn(12).setHeaderValue("大众点评闪惠");
		cm.getColumn(12).setPreferredWidth(105);
		cm.getColumn(12).setCellRenderer(redmarkRenderer);
		cm.getColumn(13).setHeaderValue("饿了么");
		cm.getColumn(13).setPreferredWidth(75);
		cm.getColumn(13).setCellRenderer(redmarkRenderer);
		cm.getColumn(14).setHeaderValue("饿了么补贴");
		cm.getColumn(14).setPreferredWidth(105);
		cm.getColumn(14).setCellRenderer(redmarkRenderer);
		cm.getColumn(15).setHeaderValue("淘点点");
		cm.getColumn(15).setPreferredWidth(75);
		cm.getColumn(15).setCellRenderer(redmarkRenderer);
//		cm.getColumn(16).setHeaderValue("美团外卖");
//		cm.getColumn(16).setPreferredWidth(75);
//		cm.getColumn(16).setCellRenderer(redmarkRenderer);
//		cm.getColumn(17).setHeaderValue("美团外卖补贴");
//		cm.getColumn(17).setPreferredWidth(105);
		cm.getColumn(16).setHeaderValue("美团超级代金券");
		cm.getColumn(16).setPreferredWidth(105);
		cm.getColumn(16).setCellRenderer(redmarkRenderer);
		cm.getColumn(17).setHeaderValue("POS机");
		cm.getColumn(17).setPreferredWidth(75);
		cm.getColumn(17).setCellRenderer(redmarkRenderer);
		cm.getColumn(18).setHeaderValue("免单");
		cm.getColumn(18).setPreferredWidth(75);
		cm.getColumn(18).setCellRenderer(redmarkRenderer);
		cm.getColumn(19).setHeaderValue("总金额");
		cm.getColumn(19).setPreferredWidth(75);
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

}
