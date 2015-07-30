package com.rci.ui.swing;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.KeyEvent;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSplitPane;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

import com.rci.config.PropertyConstants;
import com.rci.tools.properties.PropertyUtils;
import com.rci.ui.swing.listeners.ActionHandler;
import com.rci.ui.swing.listeners.CleanListener;
import com.rci.ui.swing.listeners.ExpressRateListener;
import com.rci.ui.swing.listeners.QueryListener;
import com.rci.ui.swing.listeners.StockListener;
import com.rci.ui.swing.views.ConculsionPanel;
import com.rci.ui.swing.views.ContentPanel;
import com.rci.ui.swing.views.QueryFormPanel;

public class MainFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8216786708977859424L;
	
	private ConculsionPanel conclusionPane = new ConculsionPanel(); //统计信息面板
	private QueryFormPanel queryPanel = new QueryFormPanel(); // 查询面板
	ContentPanel contentPane; //订单数据内容展示面板
	
	public MainFrame(){
		initComponent();
		QueryListener listener = new QueryListener(contentPane);
		listener.setConclusionPane(conclusionPane);
		listener.setQueryPanel(queryPanel);
		queryPanel.getQueryBtn().registerKeyboardAction(listener, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0), JComponent.WHEN_IN_FOCUSED_WINDOW);
		queryPanel.getQueryBtn().addActionListener(listener);
		
		CleanListener clistener = new CleanListener(contentPane);
		clistener.setConclusionPane(conclusionPane);
		clistener.setQueryPanel(queryPanel);
		queryPanel.getCleanBtn().addActionListener(clistener);
		
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	private void initComponent() {
		this.setTitle((String) PropertyUtils.getProperties(PropertyConstants.SYSNAME));
		Container containerPanel = this.getContentPane();
		BorderLayout layout = new BorderLayout(0, 10);
		containerPanel.setLayout(layout);
		/* 绑定菜单条*/
		this.setJMenuBar(buildMenuBar());
		/* 绑定查询form */
		containerPanel.add(queryPanel, BorderLayout.NORTH);
		/* 绑定订单内容和警告日志展示列表*/
		contentPane = new ContentPanel(JSplitPane.HORIZONTAL_SPLIT);
		containerPanel.add(contentPane, BorderLayout.CENTER);
		/* 绑定 总结页脚 */
		containerPanel.add(conclusionPane, BorderLayout.SOUTH);
	}
	
	/**
	 * 
	 * Describle(描述)：构建menu bar 内容
	 *
	 * 方法名称：buildMenuBar
	 *
	 * 所在类名：MainFrame
	 *
	 * Create Time:2015年7月27日 上午9:37:04
	 *  
	 * @param menubar
	 */
	private JMenuBar buildMenuBar(){
		JMenuBar menubar = new JMenuBar();
		JMenu system = new JMenu("系统");
		JMenu view = new JMenu("查看");
		JMenu setting = new JMenu("设置");
		JMenu statistic = new JMenu("统计");
		
		menubar.add(system);
		menubar.add(view);
		menubar.add(setting);
		menubar.add(statistic);
		JMenuItem sysInit = new JMenuItem("系统初始化");
		JMenuItem baseReset = new JMenuItem("基础数据重置");
		JMenuItem viewStock = new JMenuItem("库存查看");
		JMenuItem setStock = new JMenuItem("库存进货");
		system.add(sysInit);
		system.add(baseReset);
		view.add(viewStock);
		setting.add(setStock);
		
		JMenuItem expressRate = new JMenuItem("外送率统计"); //展示当月外送率统计信息表
		JMenuItem earning = new JMenuItem("营业额统计");
		statistic.add(expressRate);
		statistic.add(earning);
		
		
		ActionHandler handler = new ActionHandler();
		sysInit.addActionListener(handler.dataInit());
		baseReset.addActionListener(handler.baseReset());
		viewStock.addActionListener(StockListener.VIEW);
		setStock.addActionListener(StockListener.RESTOCK);
		expressRate.addActionListener(new ExpressRateListener());
		return menubar;
	}
}
