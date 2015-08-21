package com.rci.ui.swing.model;

import java.util.List;

import javax.swing.AbstractListModel;

import com.rci.ui.swing.vos.StockVO;

public class StockListModel extends AbstractListModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2934534205698976512L;
	private List<StockVO> stocks;
	
	public StockListModel(){}
	
	public StockListModel(List<StockVO> stocks){
		this.stocks = stocks;
	}
	
	@Override
	public int getSize() {
		return stocks.size();
	}

	@Override
	public Object getElementAt(int index) {
		StockVO stock = getStockAt(index);
		return stock.getDishName();
	}
	
	public StockVO getStockAt(int index){
		return stocks.get(index);
	}

	public List<StockVO> getStocks() {
		return stocks;
	}

	public void setStocks(List<StockVO> stocks) {
		this.stocks = stocks;
	}
	
	public void addElement(StockVO stock){
		int index = stocks.size();
		stocks.add(stock);
		fireIntervalAdded(this,index,index);
	}
	
	public void removeElementAt(int index){
		stocks.remove(index);
		fireIntervalRemoved(this, index, index);
	}

}
