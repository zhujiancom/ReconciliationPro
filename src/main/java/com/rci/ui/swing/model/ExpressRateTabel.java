package com.rci.ui.swing.model;

import java.util.Collections;
import java.util.List;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumnModel;

import com.rci.service.core.StatisticCenterFacade;
import com.rci.tools.DateUtil;
import com.rci.tools.SpringUtils;
import com.rci.ui.swing.vos.ExpressRateVO;

public class ExpressRateTabel extends BaseTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3852832227931696863L;
	
	public ExpressRateTabel(int columnNum){
		super(columnNum);
	}
	
	@Override
	protected void setModel(int columnNum) {
		StatisticCenterFacade scf = (StatisticCenterFacade) SpringUtils.getBean("StatisticCenterFacade");
		List<ExpressRateVO> rates = scf.getExpressRateList();
		ExpressRateTabelModel dm = new ExpressRateTabelModel(columnNum);
		dm.setItems(rates);
		super.setModel(dm);
	}


	@Override
	protected void setHeaderLabel() {
		TableColumnModel cm = this.getColumnModel();
		cm.getColumn(0).setHeaderValue("日期");
		cm.getColumn(0).setPreferredWidth(105);
		cm.getColumn(1).setHeaderValue("外送单/总单");
		cm.getColumn(1).setPreferredWidth(275);
		cm.getColumn(2).setHeaderValue("外送率");
		cm.getColumn(2).setPreferredWidth(105);
	}

	public static class ExpressRateTabelModel extends AbstractTableModel {
		/**
		 * 
		 */
		private static final long serialVersionUID = -3907144203145032413L;
		private List<ExpressRateVO> items = Collections.emptyList();;
		private int columnNum;
		
		public ExpressRateTabelModel(int columnNum){
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
			ExpressRateVO expressRate = items.get(rowIndex);
			switch (columnIndex) {
			case 0:
				return DateUtil.date2Str(expressRate.getDate());
			case 1:
				return "<html><font color='red'>" + expressRate.getExpressNum()
						+ "</font>/" + expressRate.getOrderNum()+"</html>";
			case 2:
				return expressRate.getExpressRate()+"%";
			default:break;
			}
			return null;
		}

		public List<ExpressRateVO> getItems() {
			return items;
		}

		public void setItems(List<ExpressRateVO> items) {
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
