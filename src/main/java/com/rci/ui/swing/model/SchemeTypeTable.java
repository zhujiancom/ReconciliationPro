package com.rci.ui.swing.model;

import java.util.Collections;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumnModel;

import com.rci.bean.dto.SchemeTypeQueryDTO;
import com.rci.enums.BusinessEnums.ActivityStatus;
import com.rci.tools.EnumUtils;
import com.rci.ui.swing.vos.SchemeTypeVO;

public class SchemeTypeTable extends BaseTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1192002272658617892L;

	public SchemeTypeTable(int columnNum){
		super(columnNum);
		this.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	}

	@Override
	protected void setModel() {
		SchemeTypeQueryDTO queryDTO = new SchemeTypeQueryDTO(ActivityStatus.ACTIVE);
		List<SchemeTypeVO> schemeTypeVOs = metaservice.displaySchemeTypes(queryDTO);
		SchemeTypeTabelModel dm = new SchemeTypeTabelModel(columnNum);
		dm.setItems(schemeTypeVOs);
		super.setModel(dm);
	}

	@Override
	protected void setHeaderLabel() {
		TableColumnModel cm = this.getColumnModel();
		cm.getColumn(0).setHeaderValue("活动类型编号");
		cm.getColumn(0).setPreferredWidth(100);
		cm.getColumn(1).setHeaderValue("活动类型名称");
		cm.getColumn(1).setPreferredWidth(150);
		cm.getColumn(2).setHeaderValue("最低消费金额");
		cm.getColumn(2).setPreferredWidth(150);
		cm.getColumn(3).setHeaderValue("最高消费金额");
		cm.getColumn(3).setPreferredWidth(150);
		cm.getColumn(4).setHeaderValue("适用菜品");
		cm.getColumn(4).setPreferredWidth(150);
		cm.getColumn(5).setHeaderValue("不可打折金额");
		cm.getColumn(5).setPreferredWidth(150);
		cm.getColumn(6).setHeaderValue("活动形式");
		cm.getColumn(5).setPreferredWidth(150);
	}
	
	public void refresh(SchemeTypeQueryDTO queryDTO){
		SchemeTypeTabelModel dm = (SchemeTypeTabelModel) this.getModel();
		List<SchemeTypeVO> schemeTypes = metaservice.displaySchemeTypes(queryDTO);
		dm.setItems(schemeTypes);
		dm.fireTableDataChanged();
	}
	
	public static class SchemeTypeTabelModel extends AbstractTableModel{
		

		/**
		 * 
		 */
		private static final long serialVersionUID = 5279450066010464607L;

		private List<SchemeTypeVO> items = Collections.emptyList();
		private int columnNum;
		
		public SchemeTypeTabelModel(int columnNum){
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
			SchemeTypeVO vo = items.get(rowIndex);
			switch(columnIndex){
			case 0:
				return vo.getTypeNo();
			case 1:
				return vo.getTypeName();
			case 2:
				return vo.getFloorAmount();
			case 3:
				return vo.getCeilAmount();
			case 4:
				return vo.getDishName();
			case 5:
				return vo.getDiscountAmount();
			case 6:
				return EnumUtils.getEnumMessage(vo.getActivity());
			}
			return null;
		}

		public List<SchemeTypeVO> getItems() {
			return items;
		}

		public void setItems(List<SchemeTypeVO> items) {
			this.items = items;
		}
		
		public void removeRow(int row) {
			items.remove(row);
	        fireTableRowsDeleted(row, row);
	    }
		
		public SchemeTypeVO getScheme(int rowIndex){
			return items.get(rowIndex);
		}
	}
}
