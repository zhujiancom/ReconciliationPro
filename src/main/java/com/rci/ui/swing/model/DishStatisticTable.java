package com.rci.ui.swing.model;

import java.awt.Color;
import java.util.Collections;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import com.rci.ui.swing.vos.DishStatisticVO;

public class DishStatisticTable extends BaseTable<DishStatisticVO> {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4016030443355350908L;

	public DishStatisticTable(int columnNum){
		super(columnNum);
		this.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	}
	
	@Override
	protected void setModel() {
		this.setModel(new DishStatisticTableModel(columnNum));
	}

	@Override
	protected void setHeaderLabel() {
		TableColumnModel cm = this.getColumnModel();
		cm.getColumn(0).setHeaderValue("序号");
		cm.getColumn(0).setPreferredWidth(55);
		cm.getColumn(1).setHeaderValue("菜品名称");
		cm.getColumn(1).setPreferredWidth(255);
		cm.getColumn(2).setHeaderValue("菜品价格");
		cm.getColumn(2).setPreferredWidth(100);
		cm.getColumn(3).setHeaderValue("销量");
		cm.getColumn(3).setPreferredWidth(100);
		cm.getColumn(4).setHeaderValue("销售额");
		cm.getColumn(4).setPreferredWidth(255);
		JTableHeader header = this.getTableHeader();
		header.setBackground(Color.WHITE);
	}
	
	public void reflushTable(List<DishStatisticVO> dishStatisticInfoList){
		DishStatisticTableModel model = (DishStatisticTableModel) this.getModel();
		model.setItems(dishStatisticInfoList);
		model.fireTableDataChanged();
	}
	
	public static class DishStatisticTableModel extends AbstractTableModel{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1691205174474388254L;
		private int columnNum;
		private List<DishStatisticVO> items = Collections.emptyList();
		
		public DishStatisticTableModel(int columnNum){
			this.columnNum = columnNum;
		}
		
		@Override
		public int getRowCount() {
			return items.size();
		}

		@Override
		public int getColumnCount() {
			return columnNum;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			DishStatisticVO item = items.get(rowIndex);
			switch(columnIndex){
			case 0:
				return ++rowIndex;
			case 1:
				return item.getDishName();
			case 2:
				return item.getDishPrice();
			case 3:
				return item.getSaleAmount();
			case 4:
				return item.getSaleroom();
			}
			return null;
		}

		public List<DishStatisticVO> getItems() {
			return items;
		}

		public void setItems(List<DishStatisticVO> items) {
			this.items = items;
		}
		
	}

}
