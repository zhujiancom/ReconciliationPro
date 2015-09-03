/**
 * 
 */
package com.rci.ui.swing.views.builder;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.springframework.util.CollectionUtils;

import com.rci.service.IDishService;
import com.rci.service.IStockService;
import com.rci.service.core.IMetadataService;
import com.rci.tools.SpringUtils;
import com.rci.tools.StringUtils;
import com.rci.ui.swing.model.DishListModel;
import com.rci.ui.swing.model.StockListModel;
import com.rci.ui.swing.views.PopWindow;
import com.rci.ui.swing.vos.DishVO;
import com.rci.ui.swing.vos.StockVO;

/**
 * remark (备注):
 *
 * @author zj
 *	
 * 项目名称：ReconciliationPro
 *
 * 类名称：StockManagementWinBuilder
 *
 * 包名称：com.rci.ui.swing.views.builder
 *
 * Create Time: 2015年8月18日 下午10:49:04
 *
 */
public class StockManagementWinBuilder implements PopWindowBuilder {
	private JPanel contentPane;
	
	private JList<DishVO> lList;
	
	private JList<DishVO> rList;
	
	private Map<Integer,DishVO> lmap;
	
	private Map<Integer,StockVO> rmap;
	
	private IDishService dishService;
	
	private IStockService stockService;

	/* 
	 * @see com.rci.ui.swing.views.builder.PopWindowBuilder#retrieveWindow()
	 */
	@Override
	public PopWindow retrieveWindow() {
		PopWindow stockManagementWindow = new PopWindow("库存管理设置");
		createContentPane();
		Container containerPanel = stockManagementWindow.getContentPane();
		containerPanel.add(contentPane,BorderLayout.CENTER);
		return stockManagementWindow;
	}

	/* 
	 * @see com.rci.ui.swing.views.builder.PopWindowBuilder#createQueryPane()
	 */
	@Override
	public void createQueryPane() {
		throw new UnsupportedOperationException("库存管理窗口没有query面板");
	}

	/* 
	 * @see com.rci.ui.swing.views.builder.PopWindowBuilder#createContentPane()
	 */
	@Override
	public void createContentPane() {
		contentPane = new JPanel();
		JPanel leftPane = createLeftPane();
		JPanel rightPane = createRightPane();
		JPanel operationPane = new JPanel();
		BoxLayout layout = new BoxLayout(operationPane, BoxLayout.Y_AXIS);
		operationPane.setLayout(layout);
		JButton inBtn = new JButton(">>");
		JButton outBtn = new JButton("<<");
		operationPane.add(inBtn);
		operationPane.add(Box.createVerticalStrut(20));
		operationPane.add(outBtn);
		contentPane.add(leftPane);
		contentPane.add(operationPane);
		contentPane.add(rightPane);
		
		inBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(CollectionUtils.isEmpty(lmap)){
					JOptionPane.showMessageDialog(null, "请选择要加入库存管理的菜品");
				}else{
					for(Iterator<Entry<Integer,DishVO>> it = lmap.entrySet().iterator();it.hasNext();){
						Entry<Integer,DishVO> entry = it.next();
						Integer index = entry.getKey();
						DishVO dish = entry.getValue();
						DishListModel lmodel = (DishListModel) lList.getModel();
						lmodel.removeElementAt(index);
						StockListModel rmodel = (StockListModel) rList.getModel();
						if(stockService == null){
							stockService = (IStockService) SpringUtils.getBean("StockService");
						}
						stockService.rwAddStock(dish.getDishNo());
						StockVO stock = stockService.getStock(dish.getDishNo());
						rmodel.addElement(stock);
					}
				}
			}
		});
		
		outBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(CollectionUtils.isEmpty(rmap)){
					JOptionPane.showMessageDialog(null, "请选择要移除库存管理的菜品");
				}else{
					for(Iterator<Entry<Integer,StockVO>> it = rmap.entrySet().iterator();it.hasNext();){
						Entry<Integer,StockVO> entry = it.next();
						Integer index = entry.getKey();
						StockVO stock = entry.getValue();
						String dishNo = stock.getDishNo();
						if(!StringUtils.hasText(dishNo)){
							JOptionPane.showMessageDialog(null, "不能移动");
							return ;
						}
						StockListModel rmodel = (StockListModel) rList.getModel();
						rmodel.removeElementAt(index);
						if(stockService == null){
							stockService = (IStockService) SpringUtils.getBean("StockService");
						}
						stockService.rwRemoveStock(stock.getSid());
						DishListModel lmodel = (DishListModel) lList.getModel();
						DishVO dish = dishService.queryDish(dishNo);
						lmodel.addElement(dish);
					}
				}
			}
		});
	}
	
	/**
	 * 
	 * Describle(描述)：左侧菜品列表
	 *
	 * 方法名称：createLeftPane
	 *
	 * 所在类名：StockManagementWinBuilder
	 *
	 * Create Time:2015年8月20日 下午5:01:05
	 *  
	 * @return
	 */
	private JPanel createLeftPane(){
		JPanel leftPane = new JPanel();
		BoxLayout llayout = new BoxLayout(leftPane, BoxLayout.Y_AXIS);
		leftPane.setLayout(llayout);
		JLabel llable = new JLabel("未加入库存控制菜品");
		JScrollPane lscrollPane = new JScrollPane();
		lscrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		leftPane.add(llable);
		leftPane.add(Box.createVerticalStrut(5));
		leftPane.add(lscrollPane);
		dishService = (IDishService) SpringUtils.getBean("DishService");
		List<DishVO> dishes = dishService.queryDishes(false);
		ListModel lmodel = new DishListModel(dishes);
		lList = new JList(lmodel);
		lList.setVisibleRowCount(20);
//		ldishList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		lList.setBorder(BorderFactory.createTitledBorder("未加入库存控制菜品"));
		lList.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(!e.getValueIsAdjusting()){
					JList list = (JList) e.getSource();
					int index = list.getSelectedIndex();
					if(index == -1){
						index = 0;
					}
					DishListModel dishModel = (DishListModel) list.getModel();
					DishVO dish = (DishVO) dishModel.getDishAt(index);
					lmap = new HashMap<Integer,DishVO>();
					lmap.put(index, dish);
				}
			}
		});
		lscrollPane.setViewportView(lList);
		return leftPane;
	}
	
	/**
	 * 
	 * Describle(描述)： 右侧菜品列表
	 *
	 * 方法名称：createRightPane
	 *
	 * 所在类名：StockManagementWinBuilder
	 *
	 * Create Time:2015年8月20日 下午5:01:52
	 *  
	 * @return
	 */
	private JPanel createRightPane(){
		JPanel rightPane = new JPanel();
		BoxLayout rlayout = new BoxLayout(rightPane, BoxLayout.Y_AXIS);
		rightPane.setLayout(rlayout);
		JLabel rlable = new JLabel("已加入库存控制菜品");
		JScrollPane rscrollPane = new JScrollPane();
		rscrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		rightPane.add(rlable);
		rightPane.add(Box.createVerticalStrut(5));
		rightPane.add(rscrollPane);
		IMetadataService metadataService = (IMetadataService)SpringUtils.getBean("MetadataService");
		List<StockVO> stocks = metadataService.displayStocks();
		ListModel rmodel = new StockListModel(stocks);
		rList = new JList(rmodel);
		rList.setVisibleRowCount(20);
		rList.setBorder(BorderFactory.createTitledBorder("已加入库存控制菜品"));
		rList.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(!e.getValueIsAdjusting()){
					JList list = (JList) e.getSource();
					int index = list.getSelectedIndex();
					if(index == -1){
						index = 0;
					}
					StockListModel stockModel = (StockListModel) list.getModel();
					StockVO stock = (StockVO) stockModel.getStockAt(index);
					rmap = new HashMap<Integer,StockVO>();
					rmap.put(index, stock);
				}
			}
		});
		rscrollPane.setViewportView(rList);
		return rightPane;
	}

	/* 
	 * @see com.rci.ui.swing.views.builder.PopWindowBuilder#createBottomPane()
	 */
	@Override
	public void createBottomPane() {
		// TODO Auto-generated method stub

	}

	public JPanel getContentPane() {
		return contentPane;
	}

	public void setContentPane(JPanel contentPane) {
		this.contentPane = contentPane;
	}

}
