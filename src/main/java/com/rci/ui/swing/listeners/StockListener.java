package com.rci.ui.swing.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import com.rci.contants.BusinessConstant;
import com.rci.ui.swing.model.StockTable;

public class StockListener implements ActionListener {
	public static final StockListener VIEW = new StockListener(BusinessConstant.VIEW_ACTION);
	public static final StockListener RESTOCK = new StockListener(BusinessConstant.RESTOCK_ACTION);
	
	private String action;
	
	public StockListener(String action){
		this.action = action;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JFrame frame = new JFrame();
		JScrollPane mainScrollPane = new JScrollPane();
		mainScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		StockTable stockTable = new StockTable(BusinessConstant.VIEW_ACTION);
		if(BusinessConstant.VIEW_ACTION.equals(action)){
			frame.setTitle("查看库存");
		}
		if(BusinessConstant.RESTOCK_ACTION.equals(action)){
			stockTable = new StockTable(BusinessConstant.RESTOCK_ACTION);
			frame.setTitle("设置库存");
		}
		mainScrollPane.setViewportView(stockTable);
		frame.add(mainScrollPane);
		
		frame.setSize(800, 600);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				super.windowClosed(e);
			}
		});
		frame.setLocationRelativeTo(null); // 相对居中, 在frame设置size之后
		frame.setVisible(true);
	}

}
