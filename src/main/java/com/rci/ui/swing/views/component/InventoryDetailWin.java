package com.rci.ui.swing.views.component;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.rci.tools.StringUtils;
import com.rci.ui.swing.views.PopWindow;
import com.rci.ui.swing.vos.InventoryVO;

public class InventoryDetailWin extends PopWindow {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9156143832303454329L;
	private InventoryVO inventory;
	private JLabel pname = new JLabel("品种名称");
	private JLabel ino = new JLabel("库存编号");
	private JLabel relatedDish = new JLabel("已关联菜品");
	private JLabel totalAmount = new JLabel("库存总量");
	private JLabel balanceAmount = new JLabel("库存余量");
	private JLabel consumeAmount = new JLabel("消费数量");
	private JLabel cardinal = new JLabel("基数");
	
	public InventoryDetailWin(InventoryVO inventory,int width,int height){
		super(width,height,"库存详细信息");
		this.inventory = inventory;
		initComponent();
	}

	private void initComponent() {
		JPanel containerPanel = this.getContainerPanel();
		JPanel mainPane = new JPanel();
		containerPanel.add(mainPane,BorderLayout.CENTER);
		
		mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.Y_AXIS));
		JPanel firstPane = new JPanel(new FlowLayout(FlowLayout.LEFT,20,2));
		firstPane.add(pname);
		firstPane.add(new JLabel(inventory.getIname()));
		mainPane.add(firstPane);
		
		JPanel secondPane = new JPanel(new FlowLayout(FlowLayout.LEFT,20,2));
		secondPane.add(ino);
		secondPane.add(new JLabel(inventory.getIno()));
		mainPane.add(secondPane);
		
		JPanel forthPane = new JPanel(new FlowLayout(FlowLayout.LEFT,20,2));
		forthPane.add(totalAmount);
		forthPane.add(new JLabel(StringUtils.trimToEmpty(inventory.getTotalAmount())));
		mainPane.add(forthPane);
		
		JPanel sixthPane = new JPanel(new FlowLayout(FlowLayout.LEFT,20,2));
		sixthPane.add(balanceAmount);
		sixthPane.add(new JLabel(StringUtils.trimToEmpty(inventory.getBalanceAmount())));
		mainPane.add(sixthPane);
		
		JPanel seventhPane = new JPanel(new FlowLayout(FlowLayout.LEFT,20,2));
		seventhPane.add(consumeAmount);
		seventhPane.add(new JLabel(StringUtils.trimToEmpty(inventory.getConsumeAmount())));
		mainPane.add(seventhPane);
		
		JPanel fifthPane = new JPanel(new FlowLayout(FlowLayout.LEFT,30,2));
		fifthPane.add(cardinal);
		fifthPane.add(new JLabel(StringUtils.trimToEmpty(inventory.getCardinal())));
		mainPane.add(fifthPane);
		
		JPanel thirdPane = new JPanel(new FlowLayout(FlowLayout.LEFT,20,2));
		thirdPane.add(relatedDish);
		JLabel displayRelatedDishes = new JLabel(inventory.getRelatedDishNames());
		displayRelatedDishes.setToolTipText(inventory.getRelatedDishNames());
		thirdPane.add(displayRelatedDishes);
		mainPane.add(thirdPane);
	}
}
