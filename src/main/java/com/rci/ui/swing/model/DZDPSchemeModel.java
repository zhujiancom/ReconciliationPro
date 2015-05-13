package com.rci.ui.swing.model;

import java.util.Collections;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.rci.tools.DateUtil;
import com.rci.ui.swing.vos.SchemeVO;

public class DZDPSchemeModel extends AbstractTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8918192031866331042L;
	private List<SchemeVO> schemes = Collections.emptyList();
	
	public DZDPSchemeModel(List<SchemeVO> schemes){
		this.schemes = schemes;
	}
	
	@Override
	public int getRowCount() {
		return schemes.size();
	}

	@Override
	public int getColumnCount() {
		return 9;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		SchemeVO scheme = schemes.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return scheme.getName();
		case 1:
			return scheme.getPaymode();
		case 2:
			return scheme.getPrice();
		case 3:
			return scheme.getPostPrice();
		case 4:
			return scheme.getSpread();
		case 5:
			return scheme.getType();
		case 6:
			return DateUtil.date2Str(scheme.getStartDate());
		case 7:
			return DateUtil.date2Str(scheme.getEndDate());
		case 8:
			return scheme.getStatus();
		default:
				break;
		}
		return null;
	}

}
