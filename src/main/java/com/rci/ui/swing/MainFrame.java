package com.rci.ui.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.net.URL;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

import com.rci.config.PropertyConstants;
import com.rci.tools.properties.PropertyUtils;
import com.rci.ui.swing.listeners.ActionHandler;
import com.rci.ui.swing.listeners.CleanListener;
import com.rci.ui.swing.listeners.DataExportImportListener;
import com.rci.ui.swing.listeners.FrameListener;
import com.rci.ui.swing.listeners.OrderDataExportImportListener;
import com.rci.ui.swing.listeners.QueryListener;
import com.rci.ui.swing.model.ButtonFactory;
import com.rci.ui.swing.views.ConculsionPanel;
import com.rci.ui.swing.views.ContentPanel;
import com.rci.ui.swing.views.QueryFormPanel;
import com.rci.ui.swing.views.builder.WindowBuilderFactory;

public class MainFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8216786708977859424L;

	private ConculsionPanel conclusionPane = new ConculsionPanel(); // 统计信息面板
	private QueryFormPanel queryPanel = new QueryFormPanel(); // 查询面板
	ContentPanel contentPane; // 订单数据内容展示面板
	private FrameListener frameListener;

	public MainFrame() throws Exception{
		this.setUndecorated(true);
		this.setSize(PropertyUtils.getIntegerValue("mainframe.width"), PropertyUtils.getIntegerValue("mainframe.height"));
		frameListener = new FrameListener(this); 
		initComponent();
		QueryListener listener = new QueryListener(contentPane);
		listener.setConclusionPane(conclusionPane);
		listener.setQueryPanel(queryPanel);
		queryPanel.getQueryBtn().registerKeyboardAction(listener,
				KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),
				JComponent.WHEN_IN_FOCUSED_WINDOW);
		queryPanel.getQueryBtn().addActionListener(listener);

		CleanListener clistener = new CleanListener(contentPane);
		clistener.setConclusionPane(conclusionPane);
		clistener.setQueryPanel(queryPanel);
		queryPanel.getCleanBtn().addActionListener(clistener);
		
		this.addMouseListener(frameListener);
		this.addMouseMotionListener(frameListener);
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initComponent() {
		this.setTitle((String) PropertyUtils
				.getProperties(PropertyConstants.SYSNAME));
		Container containerPanel = this.getContentPane();
		BorderLayout layout = new BorderLayout(0, 10);
		containerPanel.setLayout(layout);
		
		/* 绑定查询form */
		containerPanel.add(queryPanel, BorderLayout.NORTH);
		/* 绑定订单内容和警告日志展示列表 */
		contentPane = new ContentPanel(JSplitPane.HORIZONTAL_SPLIT,this.getWidth(),this.getHeight());
		containerPanel.add(contentPane, BorderLayout.CENTER);
		/* 绑定 总结页脚 */
		containerPanel.add(conclusionPane, BorderLayout.SOUTH);
		/* 绑定菜单条 */
		this.setJMenuBar(buildMenuBar());
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
	private JMenuBar buildMenuBar() {
		JMenuBar menubar = new JMenuBar();
		menubar.setBackground(Color.BLACK);
		JButton logoBtn = ButtonFactory.createImageButton("skin/gray/images/24x24/logo.png", null);
		JMenu system = new JMenu("系统");
		system.setForeground(Color.WHITE);
		JMenu operation = new JMenu("操作");
		operation.setForeground(Color.WHITE);
//		JMenu view = new JMenu("查看");
//		view.setForeground(Color.WHITE);
//		JMenu setting = new JMenu("设置");
//		setting.setForeground(Color.WHITE);
		JMenu statistic = new JMenu("统计");
		statistic.setForeground(Color.WHITE);
		JMenu management = new JMenu("管理");
		management.setForeground(Color.WHITE);
		final JFrame frame = this;
		menubar.add(logoBtn);
		menubar.add(Box.createHorizontalStrut(10));
		menubar.add(system);
		menubar.add(operation);
//		menubar.add(view);
//		menubar.add(setting);
		menubar.add(statistic);
		menubar.add(management);
		
		JPanel rightP = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		rightP.setBackground(Color.BLACK);
		JButton minimizeBtn = ButtonFactory.createImageButton("skin/gray/images/16x16/minimize_2.png",null);
		final JButton maximizeBtn = ButtonFactory.createImageButton("skin/gray/images/16x16/maximize_2.png",null);
		maximizeBtn.setToolTipText("最大化");
		menubar.add(maximizeBtn);
		JButton closeBtn = ButtonFactory.createImageButton("skin/gray/images/16x16/close.png",null);
		closeBtn.setToolTipText("关闭");
		rightP.add(minimizeBtn);
		rightP.add(maximizeBtn);
		rightP.add(closeBtn);
		menubar.add(rightP);
		frameListener.setMaximizeBtn(maximizeBtn);
		
		JMenuItem sysInit = ButtonFactory.createMenuItem("系统初始化","skin/gray/images/16x16/initBtn.png");
		JMenuItem baseReset = ButtonFactory.createMenuItem("基础数据重置", "skin/gray/images/16x16/basereset.png");
		JMenuItem dataExport = ButtonFactory.createMenuItem("订单数据导出", "skin/gray/images/16x16/export_0.png");
		JMenuItem dataImport = ButtonFactory.createMenuItem("订单数据导入", "skin/gray/images/16x16/import_0.png");
//		JMenuItem viewStock = ButtonFactory.createMenuItem("库存查看", "skin/gray/images/16x16/stock.png");
//		JMenuItem setStock = ButtonFactory.createMenuItem("库存进货", "skin/gray/images/16x16/restock.png");
//		JMenuItem stockManagement = ButtonFactory.createMenuItem("库存管理", "skin/gray/images/16x16/stockmanage.png");
//		JMenuItem schemeManagement = ButtonFactory.createMenuItem("在线活动管理", "skin/gray/images/16x16/activity.png");
		system.add(sysInit);
		system.add(baseReset);
		operation.add(dataExport);
		operation.add(dataImport);
//		view.add(viewStock);
//		setting.add(setStock);
//		setting.add(stockManagement);
//		setting.add(schemeManagement);

		JMenuItem expressRate = ButtonFactory.createMenuItem("外送率统计", "skin/gray/images/16x16/takeout.png");
		JMenuItem earning = ButtonFactory.createMenuItem("营业额统计", "skin/gray/images/16x16/statistic.png");
		JMenuItem costControl = ButtonFactory.createMenuItem("成本控制", "skin/gray/images/16x16/cost.png");
		statistic.add(expressRate);
		statistic.add(earning);
		statistic.add(costControl);
		
		JMenuItem dishManage = ButtonFactory.createMenuItem("菜品管理", "skin/gray/images/16x16/dish.png");
		JMenuItem inventoryManage = ButtonFactory.createMenuItem("库存管理", "skin/gray/images/16x16/stockmanage.png");
		JMenuItem activityManage = ButtonFactory.createMenuItem("活动管理", "skin/gray/images/16x16/activity.png");
		management.add(dishManage);
		management.add(inventoryManage);
		management.add(activityManage);

		ActionHandler handler = new ActionHandler();
		sysInit.addActionListener(handler.dataInit());
		baseReset.addActionListener(handler.baseReset());
//		// 库存查看事件
//		viewStock.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				WindowBuilderFactory.createViewStockWindow();
//			}
//		});
//		// 库存进货时间
//		setStock.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				WindowBuilderFactory.createReStockWindow();
//			}
//		});
		// 外送率统计事件
		expressRate.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				WindowBuilderFactory.createExpressReateWindow();				
			}
		});
		// 营业额统计事件
		earning.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				WindowBuilderFactory.createTurnoverWindow();
			}
		});
		// 库存管理事件
//		stockManagement.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				WindowBuilderFactory.createStockManagementWindow();
//			}
//		});
		
		// 活动设置
		activityManage.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				WindowBuilderFactory.createSchemeManagementWindow();
			}
		});

		// 数据导出
//		DataIOListener dataExportListener = new DataIOListener(DataIOListener.EXPORT);
		DataExportImportListener dataExportListener = new OrderDataExportImportListener(DataExportImportListener.EXPORT);
		dataExport.addActionListener(dataExportListener);
		
		//数据导入
//		DataIOListener dataImportListener = new DataIOListener(DataIOListener.IMPORT);
		OrderDataExportImportListener dataImportListener = new OrderDataExportImportListener(DataExportImportListener.IMPORT);
		dataImportListener.setConclusionPane(conclusionPane);
		dataImportListener.setContentPane(contentPane);
		dataImportListener.setQueryPanel(queryPanel);
		dataImport.addActionListener(dataImportListener);
		
		//系统退出
		closeBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		//窗口最大化
		maximizeBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
				if(device.getFullScreenWindow() == null){
					device.setFullScreenWindow(frame);
					frame.removeMouseListener(frameListener);
					frame.removeMouseMotionListener(frameListener);
					URL btnUrl = this.getClass().getClassLoader().getResource("skin/gray/images/16x16/normalize_2.png");
					maximizeBtn.setIcon(new ImageIcon(btnUrl));
					maximizeBtn.setToolTipText("向下还原");
				}else{
					device.setFullScreenWindow(null);
					frame.addMouseListener(frameListener);
					frame.addMouseMotionListener(frameListener);
					URL btnUrl = this.getClass().getClassLoader().getResource("skin/gray/images/16x16/maximize_2.png");
					maximizeBtn.setIcon(new ImageIcon(btnUrl));
					maximizeBtn.setToolTipText("最大化");
				}
			}
		});
		//窗口最小化
		minimizeBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setExtendedState(JFrame.ICONIFIED);
			}
		});
		
		//库存管理
		inventoryManage.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				WindowBuilderFactory.createInventoryManagementWindow();
			}
		});
		return menubar;
	}
}
