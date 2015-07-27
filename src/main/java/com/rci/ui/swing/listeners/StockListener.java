package com.rci.ui.swing.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.rci.contants.BusinessConstant;
import com.rci.ui.swing.model.StockTable;

public class StockListener extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5527733323323061321L;
	public static final StockListener VIEW = new StockListener(BusinessConstant.VIEW_ACTION);
	public static final StockListener RESTOCK = new StockListener(BusinessConstant.RESTOCK_ACTION);
	
	private String action;
	
	private StockListener(String action){
		this.action = action;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JPanel mainPanel = new JPanel();
		JScrollPane mainScrollPane = new JScrollPane();
		mainScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		JTable stockTable = new StockTable(BusinessConstant.VIEW_ACTION);
		if(BusinessConstant.VIEW_ACTION.equals(action)){
			this.setTitle("查看库存");
		}
		if(BusinessConstant.RESTOCK_ACTION.equals(action)){
			stockTable = new StockTable(BusinessConstant.RESTOCK_ACTION);
			this.setTitle("设置库存");
		}
		mainScrollPane.setViewportView(stockTable);
		mainPanel.add(mainScrollPane);
		this.add(mainScrollPane);
		
		this.setSize(800, 600);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				super.windowClosed(e);
			}
		});
		this.setLocationRelativeTo(null); // 相对居中, 在frame设置size之后
		this.setVisible(true);
	}

}
