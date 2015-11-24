package com.rci.ui.swing.views.component;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.rci.ui.swing.listeners.DishSelectListener;
import com.rci.ui.swing.model.ButtonFactory;
import com.rci.ui.swing.views.PopWindow;

public class DishSelectWin extends PopWindow {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2248904392389671255L;
	
	private DishSelectListener selectListener;
	
	public DishSelectWin(DishSelectListener selectListener,int width,int height,String title){
		super(width,height,title);
		this.selectListener = selectListener;
		createContentPane();
	}

	private void createContentPane() {
		JPanel containerPanel = this.getContainerPanel();
		containerPanel.add(new SeriesTypePanel(containerPanel,selectListener),BorderLayout.NORTH);
//		containerPanel.add(buildDishPane(),BorderLayout.CENTER);
//		containerPanel.add(new DisplayDishPanel(selectListener),BorderLayout.CENTER);
		containerPanel.add(buildOperatePane(),BorderLayout.SOUTH);
	}
	
	
	private JPanel buildOperatePane(){
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER,20,0));
		JButton confirmBtn = ButtonFactory.createImageButton("确定","skin/gray/images/btn_green.png",null);
		JButton cancelBtn = ButtonFactory.createImageButton("取消","skin/gray/images/btn_purple.png",null);
		panel.add(confirmBtn);
		panel.add(cancelBtn);
		confirmBtn.addActionListener(selectListener);
		return panel;
	}

	public DishSelectListener getSelectListener() {
		return selectListener;
	}

	public void setSelectListener(DishSelectListener selectListener) {
		this.selectListener = selectListener;
	}

}
