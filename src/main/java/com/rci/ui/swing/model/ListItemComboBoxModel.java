package com.rci.ui.swing.model;

import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

public class ListItemComboBoxModel<T> extends AbstractListModel<T>
		implements ComboBoxModel<T> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4883065378238865479L;

//	private Object selectedItem;
	
	private T selectedItem;
	
	private List<T> itemList;
	
	public ListItemComboBoxModel() {
		super();
	}

	public ListItemComboBoxModel(List<T> itemList) {
		super();
		this.itemList = itemList;
	}

	@Override
	public int getSize() {
		return itemList.size();
	}

	@Override
	public T getElementAt(int index) {
		return (T) itemList.get(index);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setSelectedItem(Object anItem) {
		selectedItem =  (T) anItem;
	}

	@Override
	public Object getSelectedItem() {
		return selectedItem;
	}

}
