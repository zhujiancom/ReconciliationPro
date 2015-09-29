package com.rci.ui.swing.model;

import java.util.Collections;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumnModel;

import com.rci.service.core.IMetadataService;
import com.rci.tools.DateUtil;
import com.rci.tools.SpringUtils;
import com.rci.ui.swing.vos.SchemeVO;

public class SchemeTable extends BaseTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1397324428014908211L;
	
	public SchemeTable(int columnNum){
		super(columnNum);
		this.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	}
	
	/**
	 * 
	 * Describle(描述)： 设置表格标题烂
	 *
	 * 方法名称：setHeaderLabel
	 *
	 * 所在类名：DZDPSchemeTable
	 *
	 * Create Time:2015年5月13日 下午2:42:10
	 *
	 */
	public void setHeaderLabel(){
		TableColumnModel cm = this.getColumnModel();
		cm.getColumn(0).setHeaderValue("活动名称");
		cm.getColumn(0).setPreferredWidth(250);
		cm.getColumn(1).setHeaderValue("活动平台");
		cm.getColumn(1).setPreferredWidth(150);
		cm.getColumn(2).setHeaderValue("活动状态");
		cm.getColumn(2).setPreferredWidth(150);
		cm.getColumn(3).setHeaderValue("优惠金额");
		cm.getColumn(3).setPreferredWidth(150);
		cm.getColumn(4).setHeaderValue("餐厅补贴");
		cm.getColumn(4).setPreferredWidth(150);
		cm.getColumn(5).setHeaderValue("平台补贴");
		cm.getColumn(5).setPreferredWidth(150);
		cm.getColumn(6).setHeaderValue("开始时间");
		cm.getColumn(6).setPreferredWidth(140);
		cm.getColumn(7).setHeaderValue("结束时间");
		cm.getColumn(7).setPreferredWidth(140);
		cm.getColumn(8).setHeaderValue("最低消费金额");
		cm.getColumn(8).setPreferredWidth(200);
		cm.getColumn(9).setHeaderValue("最高消费金额");
		cm.getColumn(9).setPreferredWidth(200);
	}

	@Override
	protected void setModel(int columnNum) {
		IMetadataService metaservice = (IMetadataService) SpringUtils.getBean("MetadataService");
		List<SchemeVO> schemes = metaservice.dishplaySchemes();
		SchemeTabelModel dm = new SchemeTabelModel(columnNum);
		dm.setItems(schemes);
		super.setModel(dm);
	}
	
	public static class SchemeTabelModel extends AbstractTableModel{
		/**
		 * 
		 */
		private static final long serialVersionUID = -2413115263590198586L;
		private List<SchemeVO> items = Collections.emptyList();;
		private int columnNum;
		
		public SchemeTabelModel(int columnNum){
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
			SchemeVO scheme = items.get(rowIndex);
			switch(columnIndex){
			case 0:
				return scheme.getName();
			case 1:
				return scheme.getDishplayVendor();
			case 2:
				return scheme.getStatus();
			case 3:
				return scheme.getPrice();
			case 4:
				return scheme.getSpread();
			case 5:
				return scheme.getPostPrice();
			case 6:
				return DateUtil.date2Str(scheme.getStartDate());
			case 7:
				return DateUtil.date2Str(scheme.getEndDate());
			case 8:
				return scheme.getFloorAmount();
			case 9:
				return scheme.getCeilAmount();
			default:break;
			}
			return null;
		}

		public List<SchemeVO> getItems() {
			return items;
		}

		public void setItems(List<SchemeVO> items) {
			this.items = items;
		}

		public int getColumnNum() {
			return columnNum;
		}

		public void setColumnNum(int columnNum) {
			this.columnNum = columnNum;
		}
		
	}
}
