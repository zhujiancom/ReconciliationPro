package com.rci.ui.swing.views.builder;

import java.awt.Container;

import javax.swing.JScrollPane;

import com.rci.contants.BusinessConstant;
import com.rci.ui.swing.model.StockTable;
import com.rci.ui.swing.views.PopWindow;

public class StockWinBuilder implements PopWindowBuilder {
	private String action;
	private JScrollPane mainScrollPane;
	private StockTable stockTable;
	
	public StockWinBuilder(String action){
		this.action = action;
	}
	
	@Override
	public PopWindow retrieveWindow() {
		PopWindow stockWindow = new PopWindow();
		createContentPane();
		if(BusinessConstant.VIEW_ACTION.equals(action)){
			stockWindow.setTitle("库存信息查看");
			
		}else{
			stockWindow.setTitle("库存设置");
		}
		Container containerPanel = stockWindow.getContentPane();
		containerPanel.add(mainScrollPane);
		return stockWindow;
	}

	@Override
	public void createQueryPane() {
		throw new UnsupportedOperationException("库存展示窗口没有query面板");
	}

	@Override
	public void createContentPane() {
		mainScrollPane = new JScrollPane();
		mainScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		stockTable = new StockTable(7,action);
		mainScrollPane.setViewportView(stockTable);
	}

	@Override
	public void createBottomPane() {
		throw new UnsupportedOperationException("库存展示窗口没有Bottom面板");
	}

}
