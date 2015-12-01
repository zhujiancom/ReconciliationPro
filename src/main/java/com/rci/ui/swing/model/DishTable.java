package com.rci.ui.swing.model;

import java.util.Collections;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumnModel;

import com.rci.bean.dto.QueryDishDTO;
import com.rci.tools.EnumUtils;
import com.rci.ui.swing.vos.DishVO;

public class DishTable extends BaseTable<DishVO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5963311643086290870L;
	
	public DishTable(int columnNum){
		super(columnNum);
		this.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	}

	@Override
	protected void setModel() {
		DishTableModel dm = new DishTableModel(columnNum);
		List<DishVO> dishes = metaservice.getAllDishes();
		dm.setItems(dishes);
		super.setModel(dm);
	}

	@Override
	protected void setHeaderLabel() {
		TableColumnModel cm = this.getColumnModel();
		cm.getColumn(0).setHeaderValue("菜品编号");
		cm.getColumn(0).setPreferredWidth(100);
		cm.getColumn(1).setHeaderValue("菜品名称");
		cm.getColumn(1).setPreferredWidth(150);
		cm.getColumn(2).setHeaderValue("价格");
		cm.getColumn(2).setPreferredWidth(80);
		cm.getColumn(3).setHeaderValue("状态");
		cm.getColumn(3).setPreferredWidth(80);
		cm.getColumn(4).setHeaderValue("是否套餐");
		cm.getColumn(4).setPreferredWidth(80);
		cm.getColumn(5).setHeaderValue("不可打折");
		cm.getColumn(5).setPreferredWidth(80);
	}
	
	public void reflushTableData(QueryDishDTO queryDTO){
		DishTableModel dm = (DishTableModel) this.getModel();
		List<DishVO> dishes = metaservice.queryDishes(queryDTO);
		dm.setItems(dishes);
		dm.fireTableDataChanged();
	}
	
	public static class DishTableModel extends AbstractTableModel{
		/**
		 * 
		 */
		private static final long serialVersionUID = 5373491518877348933L;
		private int columnNum;
		private List<DishVO> items = Collections.emptyList();
		
		public DishTableModel(int columnNum){
			this.columnNum = columnNum;
		}
		
		@Override
		public int getColumnCount() {
			return columnNum;
		}
		@Override
		public int getRowCount() {
			return items.size();
		}
		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			DishVO dish = items.get(rowIndex);
			switch(columnIndex){
			case 0:
				return dish.getDishNo();
			case 1:
				return dish.getDishName();
			case 2:
				return dish.getDishPrice();
			case 3:
				return EnumUtils.getEnumMessage(dish.getStopFlag());
			case 4:
				return EnumUtils.getEnumMessage(dish.getSuitFlag());
			case 5:
				return EnumUtils.getEnumMessage(dish.getDiscountFlag());
			}
			return null;
		}

		public List<DishVO> getItems() {
			return items;
		}

		public void setItems(List<DishVO> items) {
			this.items = items;
		}
		
	}

}
