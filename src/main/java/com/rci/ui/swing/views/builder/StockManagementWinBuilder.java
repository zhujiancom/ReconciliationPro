/**
 * 
 */
package com.rci.ui.swing.views.builder;

import java.awt.BorderLayout;
import java.awt.Container;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;

import com.rci.service.IDishService;
import com.rci.tools.SpringUtils;
import com.rci.ui.swing.model.DishListModel;
import com.rci.ui.swing.views.PopWindow;
import com.rci.ui.swing.vos.DishVO;

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
		IDishService dishService = (IDishService) SpringUtils.getBean("DishService");
		List<DishVO> dishes = dishService.queryDishes(false);
		ListModel lmodel = new DishListModel(dishes);
		JList ldishList = new JList(lmodel);
		ldishList.setVisibleRowCount(20);
		ldishList.setBorder(BorderFactory.createTitledBorder("未加入库存控制菜品"));
		lscrollPane.setViewportView(ldishList);
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
		IDishService dishService = (IDishService) SpringUtils.getBean("DishService");
		List<DishVO> dishes = dishService.queryDishes(true);
		ListModel rmodel = new DishListModel(dishes);
		JList rdishList = new JList(rmodel);
		rdishList.setVisibleRowCount(20);
		rdishList.setBorder(BorderFactory.createTitledBorder("已加入库存控制菜品"));
		rscrollPane.setViewportView(rdishList);
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
