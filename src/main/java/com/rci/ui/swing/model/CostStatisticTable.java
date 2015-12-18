package com.rci.ui.swing.model;

import java.util.Collections;
import java.util.List;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumnModel;

import org.springframework.util.CollectionUtils;

import com.rci.tools.DateUtil;
import com.rci.ui.swing.vos.CostStatisticVO;

public class CostStatisticTable extends BaseTable<CostStatisticVO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1612516564207743303L;

	public CostStatisticTable(int columnNum){
		super(columnNum);
	}
	
	@Override
	protected void setModel() {
		this.setModel(new CostStatisticTabelModel(columnNum));
	}

	@Override
	protected void setHeaderLabel() {
		TableColumnModel cm = this.getColumnModel();
		cm.getColumn(0).setHeaderValue("日期");
		cm.getColumn(0).setPreferredWidth(105);
		cm.getColumn(1).setHeaderValue("原料成本");
		cm.getColumn(1).setPreferredWidth(275);
		cm.getColumn(2).setHeaderValue("总收入");
		cm.getColumn(2).setPreferredWidth(105);
	}
	
	public void reflushTable(List<CostStatisticVO> costs){
		CostStatisticTabelModel dm = (CostStatisticTabelModel) this.getModel();
		dm.setItems(costs);
		dm.fireTableDataChanged();
	}
	
	public static class CostStatisticTabelModel extends AbstractTableModel {
		/**
		 * 
		 */
		private static final long serialVersionUID = -2128189020769241100L;
		private int columnNum;
		private List<CostStatisticVO> items = Collections.emptyList();
		
		public CostStatisticTabelModel(int columnNum){
			this.columnNum = columnNum;
		}
		
		@Override
		public int getRowCount() {
			if(CollectionUtils.isEmpty(items)){
				return 0;
			}
			return items.size();
		}

		@Override
		public int getColumnCount() {
			return columnNum;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			CostStatisticVO costvo = items.get(rowIndex);
			switch(columnIndex){
			case 0:
				return DateUtil.date2Str(costvo.getDate());
			case 1:
				return costvo.getCostAmount();
			case 2:
				return costvo.getTurnoverAmount();
			default:
				break;
			}
			return null;
		}

		public List<CostStatisticVO> getItems() {
			return items;
		}

		public void setItems(List<CostStatisticVO> items) {
			this.items = items;
		}
		
	}

}
