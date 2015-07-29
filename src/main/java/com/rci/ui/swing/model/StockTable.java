package com.rci.ui.swing.model;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.rci.contants.BusinessConstant;
import com.rci.service.IStockService;
import com.rci.service.core.IMetadataService;
import com.rci.tools.DigitUtil;
import com.rci.tools.SpringUtils;
import com.rci.ui.swing.renderers.AbstractLineRedMarkRenderer;
import com.rci.ui.swing.vos.StockVO;

public class StockTable extends JTable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6631773169791610714L;
	private String action;
	
	public StockTable(){}
	
	public StockTable(String action){
		this.action = action;
		setModel();
		setHeaderLabel();
		this.setRowHeight(30);
	}
	
	public void setModel(){
		IMetadataService metadataService = (IMetadataService)SpringUtils.getBean("MetadataService");
		List<StockVO> stocks = metadataService.displayStocks();
		StockTableModel stockModel = new StockTableModel(stocks);
		super.setModel(stockModel);
	}
	
	public void setHeaderLabel(){
		StockTableRedMarkRenderer redmarkRenderer = new StockTableRedMarkRenderer();
		TableColumnModel cm = this.getColumnModel();
		cm.getColumn(0).setHeaderValue("库存编号");
		cm.getColumn(0).setPreferredWidth(75);
		cm.getColumn(0).setCellRenderer(redmarkRenderer);
		cm.getColumn(1).setHeaderValue("名称");
		cm.getColumn(1).setPreferredWidth(75);
		cm.getColumn(1).setCellRenderer(redmarkRenderer);
		cm.getColumn(2).setHeaderValue("总量");
		cm.getColumn(2).setPreferredWidth(105);
		cm.getColumn(2).setCellRenderer(redmarkRenderer);
		cm.getColumn(3).setHeaderValue("已消费数量");
		cm.getColumn(3).setPreferredWidth(75);
		cm.getColumn(3).setCellRenderer(redmarkRenderer);
		cm.getColumn(4).setHeaderValue("剩余数量");
		cm.getColumn(4).setPreferredWidth(75);
		cm.getColumn(4).setCellRenderer(redmarkRenderer);
		if(BusinessConstant.RESTOCK_ACTION.equals(this.action)){
			TableColumn operateCol = cm.getColumn(5);
			operateCol.setPreferredWidth(50);
			operateCol.setHeaderValue("操作");
			operateCol.setCellRenderer(new MyJButtonCellRenderer());
			operateCol.setCellEditor(new MyJButtonCellEditor());
		}
	}
	
	private class MyJButtonCellRenderer implements TableCellRenderer{

		private JPanel panel;
		private JButton btn;
		
		public MyJButtonCellRenderer(){
			this.initButton();
			this.initPanel();
			this.panel.add(this.btn);
		}
		
		private void initButton(){
			this.btn = new JButton();
			this.btn.setBounds(20, 0, 60, 25);
		}
		
		private void initPanel(){
			this.panel = new JPanel();
			this.panel.setLayout(null);
		}

		@Override
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			this.btn.setText("进货");
			return this.panel;  
		}
		
	}
	
	private class MyJButtonCellEditor extends DefaultCellEditor{
		/**
		 * 
		 */
		private static final long serialVersionUID = 6668108348824639549L;
		private JPanel panel;
		private JButton btn;
		private String sno;
		private JTable table;
		
		public MyJButtonCellEditor() {
			super(new JTextField());
			// 设置点击几次激活编辑。   
	        this.setClickCountToStart(1);  
	        this.initButton();  
	        
	        this.initPanel();  
	  
	        // 添加按钮。   
	        this.panel.add(this.btn);  
		}
		
		private void initButton(){
			this.btn = new JButton();
			this.btn.setBounds(20, 0, 60, 25);
			btn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					final JFrame frame = new JFrame("进货数量设置");
					JPanel subpanel = new JPanel();
					frame.add(subpanel);
					final JTextField amountInput = new JTextField(10);
					JButton saveBtn = new JButton("确定");
					saveBtn.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							IStockService stockService = (IStockService) SpringUtils.getBean("StockService");
							IMetadataService metadataService = (IMetadataService)SpringUtils.getBean("MetadataService");
							BigDecimal amount = DigitUtil.stringTobigDecimal(amountInput.getText());
							stockService.rwRestock(sno, amount);
							frame.dispose();
							List<StockVO> stocks = metadataService.displayStocks();
							StockTableModel stm = (StockTableModel) table.getModel();
							stm.setStocks(stocks);
							table.repaint();
							table.updateUI();
						}
					});
					
					subpanel.add(amountInput);
					subpanel.add(saveBtn);
					
					frame.setSize(300, 100);
					frame.addWindowListener(new WindowAdapter() {
						@Override
						public void windowClosed(WindowEvent e) {
							super.windowClosed(e);
						}
					});
					frame.setLocationRelativeTo(null); // 相对居中, 在frame设置size之后
					frame.setVisible(true);
				}
			});
		}
		
		private void initPanel(){
			this.panel = new JPanel();
			this.panel.setLayout(null);
		}

		@Override
		public Component getTableCellEditorComponent(JTable table,
				Object value, boolean isSelected, int row, int column) {
			sno = String.valueOf(value);
			this.table = table;
			this.btn.setText("进货");
			return this.panel;  
		}

		@Override
		public Object getCellEditorValue() {
			return sno;
		}
		
	}
	
	/**
	 * 
	 * remark (备注):Stock Model
	 *
	 * @author zj
	 *	
	 * 项目名称：ReconciliationPro
	 *
	 * 类名称：StockTableModel
	 *
	 * 包名称：com.rci.ui.swing.model
	 *
	 * Create Time: 2015年7月27日 下午2:52:51
	 *
	 */
	public class StockTableModel extends AbstractTableModel{
		/**
		 * 
		 */
		private static final long serialVersionUID = 2069979813018653831L;
		private List<StockVO> stocks = Collections.emptyList();
		
		public StockTableModel(List<StockVO> stocks){
			this.stocks = stocks;
		}
		
		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			if(columnIndex == 5){
				return true;
			}
			return false;
		}

		@Override
		public int getRowCount() {
			return stocks.size();
		}
		
		public StockVO getStockAt(int rowIndex){
			return stocks.get(rowIndex);
		}

		@Override
		public int getColumnCount() {
			if(action.equals(BusinessConstant.RESTOCK_ACTION)){
				return 6;
			}
			return 5;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			StockVO stock = stocks.get(rowIndex);
			switch (columnIndex) {
			case 0:
				return stock.getSno();
			case 1:
				return stock.getDishName();
			case 2:
				return "<html><font color='blue'>"+stock.getGross()+"</font></html>";
			case 3:
				return stock.getConsumeAmount();
			case 4:
				return stock.getBalanceAmount();
			case 5:
				return stock.getSno();
			default:
					break;
			}
			return null;
		}

		public void setStocks(List<StockVO> stocks) {
			this.stocks = stocks;
		}
	}
	
	/**
	 * 
	 * remark (备注): if stock's balance amount <= 0, mark red
	 *
	 * @author zj
	 *	
	 * 项目名称：ReconciliationPro
	 *
	 * 类名称：StockTableRedMarkRenderer
	 *
	 * 包名称：com.rci.ui.swing.model
	 *
	 * Create Time: 2015年7月27日 下午2:53:02
	 *
	 */
	private class StockTableRedMarkRenderer extends AbstractLineRedMarkRenderer<StockTableModel>{

		/**
		 * 
		 */
		private static final long serialVersionUID = -7471535707441144408L;

		@Override
		public boolean markRed(StockTableModel tm, int rowIndex) {
			StockVO stock = tm.getStockAt(rowIndex);
			if(stock.getBalanceAmount().compareTo(BigDecimal.TEN) <= 0){
				return true;
			}
			return false;
		}
		
	}
}
