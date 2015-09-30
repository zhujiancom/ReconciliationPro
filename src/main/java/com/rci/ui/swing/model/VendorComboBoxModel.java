package com.rci.ui.swing.model;

import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

import com.rci.bean.LabelValueBean;

public class VendorComboBoxModel extends AbstractListModel<LabelValueBean<String>>
		implements ComboBoxModel<LabelValueBean<String>> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4883065378238865479L;

//	private Object selectedItem;
	
	private LabelValueBean<String> selectedItem;
	
	private List<LabelValueBean<String>> itemList;
	
	public VendorComboBoxModel() {
		super();
	}

	public VendorComboBoxModel(List<LabelValueBean<String>> itemList) {
		super();
		this.itemList = itemList;
	}

	@Override
	public int getSize() {
		return itemList.size();
	}

	@Override
	public LabelValueBean<String> getElementAt(int index) {
		return itemList.get(index);
	}

	@Override
	public void setSelectedItem(Object anItem) {
		selectedItem = (LabelValueBean<String>) anItem;
	}

	@Override
	public Object getSelectedItem() {
		return selectedItem;
	}

}
