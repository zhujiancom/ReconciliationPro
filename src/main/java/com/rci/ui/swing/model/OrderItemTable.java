package com.rci.ui.swing.model;

import java.util.Collections;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumnModel;

import org.springframework.util.CollectionUtils;

import com.rci.tools.DateUtil;
import com.rci.ui.swing.renderers.AbstractLineColorMarkRenderer;
import com.rci.ui.swing.vos.OrderItemVO;

public class OrderItemTable extends JTable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3873201330957234091L;

	public OrderItemTable(){
		super(new OrderItemTableModel(7));
		setHeaderLabel();
		this.setRowHeight(20);
		this.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	}
	
	public void setHeaderLabel(){
		TableColumnModel cm = this.getColumnModel();
		BackDishRenderer backDishRenderer = new BackDishRenderer();
		cm.getColumn(0).setHeaderValue("菜品名称");
		cm.getColumn(0).setPreferredWidth(105);
		cm.getColumn(1).setHeaderValue("数量");
		cm.getColumn(1).setPreferredWidth(105);
		cm.getColumn(2).setHeaderValue("退菜数量");
		cm.getColumn(2).setPreferredWidth(75);
		cm.getColumn(2).setCellRenderer(backDishRenderer);
		cm.getColumn(3).setHeaderValue("原价");
		cm.getColumn(3).setPreferredWidth(75);
		cm.getColumn(4).setHeaderValue("实收金额");
		cm.getColumn(4).setPreferredWidth(75);
		cm.getColumn(5).setHeaderValue("折扣率");
		cm.getColumn(5).setPreferredWidth(115);
		cm.getColumn(6).setHeaderValue("消费时间");
		cm.getColumn(6).setPreferredWidth(140);
	}

	public static class OrderItemTableModel extends AbstractTableModel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 763876241766196751L;
		private List<OrderItemVO> items = Collections.emptyList();
		private int columnNum;
		
		public OrderItemTableModel(int columnNum){
			this.columnNum = columnNum;
		}
		
		public OrderItemTableModel(List<OrderItemVO> items){
			this.items = items;
		}
		
		@Override
		public int getRowCount() {
			return items.size();
		}

		@Override
		public int getColumnCount() {
			return this.columnNum;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			OrderItemVO item = items.get(rowIndex);
			switch(columnIndex){
			case 0:
				return item.getDishName();
			case 1:
				return item.getCount();
			case 2:
				return item.getCountback();
			case 3:
				return item.getPrice();
			case 4:
				return item.getActualAmount();
			case 5:
				return item.getDiscountRate();
			case 6:
				return DateUtil.time2Str(item.getConsumeTime());
			default:
				break;
			}
			return null;
		}

		public void setRowCount(int rowCount){
			int old = getRowCount();
			items = Collections.emptyList();
			if(old > 0){
				super.fireTableRowsDeleted(rowCount,old-1);
			}
		}

		public List<OrderItemVO> getItems() {
			return items;
		}

		public void setItems(List<OrderItemVO> items) {
			this.items = items;
		}
		
		public OrderItemVO getOrderItemAt(int rowIndex){
			if(CollectionUtils.isEmpty(items)){
				return null;
			}
			return items.get(rowIndex);
		}
	}

	private class BackDishRenderer extends AbstractLineColorMarkRenderer<OrderItemTableModel>{

		/**
		 * 
		 */
		private static final long serialVersionUID = 3004289115913083755L;

		@Override
		public boolean markColor(OrderItemTableModel tm, int rowIndex) {
			OrderItemVO item = tm.getOrderItemAt(rowIndex);
			if(item.getCountback() != 0){
				return true;
			}
			return false;
		}
	}
}
