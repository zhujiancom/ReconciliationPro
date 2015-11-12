package com.rci.ui.swing.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;

import com.rci.ui.swing.model.ActivityTypeJCheckBox;
import com.rci.ui.swing.model.SchemeTypeTable;

public class ActivityTypeCheckListener implements ItemListener, ActionListener {
	private JTable table;
	private List<ActivityTypeJCheckBox> items = new ArrayList<ActivityTypeJCheckBox>();
	
	public ActivityTypeCheckListener(JTable table){
		this.table = table;
	}
	
	@Override
	public void actionPerformed(ActionEvent paramActionEvent) {
		// TODO Auto-generated method stub

	}

	@Override
	public void itemStateChanged(ItemEvent paramItemEvent) {
		// TODO Auto-generated method stub

	}
	
	public void addCheckBox(ActivityTypeJCheckBox checkBox){
		items.add(checkBox);
	}

	/**
	 * 
	 * Describle(描述)： 刷新表格数据
	 *
	 * 方法名称：refreshTableData
	 *
	 * 所在类名：ActivityTypeCheckListener
	 *
	 * Create Time:2015年11月12日 下午5:21:48
	 *
	 */
	public void refreshTableData(){
		((SchemeTypeTable)table).refresh();
	}
}
