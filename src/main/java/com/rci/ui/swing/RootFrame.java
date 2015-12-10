package com.rci.ui.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.UIManager;

import com.rci.config.PropertyConstants;
import com.rci.tools.properties.PropertyUtils;
import com.rci.ui.swing.handler.MainPaneSwitcherHandler;
import com.rci.ui.swing.handler.MenuItemActionHandler;
import com.rci.ui.swing.listeners.DataExportImportListener;
import com.rci.ui.swing.listeners.FrameListener;
import com.rci.ui.swing.listeners.OrderDataExportImportListener;
import com.rci.ui.swing.model.ButtonFactory;
import com.rci.ui.swing.views.component.switcher.SwitcherBar;
import com.rci.ui.swing.views.component.switcher.SwitcherElement;

public class RootFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2808931761923869238L;
	private FrameListener frameListener;
	
	public RootFrame() throws Exception{
		this.setUndecorated(true);
		this.setSize(PropertyUtils.getIntegerValue("mainframe.width"), PropertyUtils.getIntegerValue("mainframe.height"));
		frameListener = new FrameListener(this); 
		this.setTitle((String) PropertyUtils
				.getProperties(PropertyConstants.SYSNAME));
		Container containerPanel = this.getContentPane();
		BorderLayout layout = new BorderLayout(0, 0);
		containerPanel.setLayout(layout);
		containerPanel.add(new CheckedoutOrderPanel(this.getWidth(),this.getHeight()),BorderLayout.CENTER);
		this.setJMenuBar(buildMenuBar());
		this.addMouseListener(frameListener);
		this.addMouseMotionListener(frameListener);
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private JMenuBar buildMenuBar() {
		JMenuBar menubar = new JMenuBar();
		menubar.setBackground(Color.BLACK);
		JButton logoBtn = ButtonFactory.createImageButton("skin/gray/images/24x24/logo.png", null);
		JMenu system = new JMenu("系统");
		system.setForeground(Color.WHITE);
		JMenu operation = new JMenu("操作");
		operation.setForeground(Color.WHITE);
		JMenu statistic = new JMenu("统计");
		statistic.setForeground(Color.WHITE);
		JMenu management = new JMenu("管理");
		management.setForeground(Color.WHITE);
		final JFrame frame = this;
		menubar.add(logoBtn);
		menubar.add(Box.createHorizontalStrut(10));
		menubar.add(system);
		menubar.add(operation);
		menubar.add(statistic);
		menubar.add(management);
		JPanel rightP = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		rightP.setBackground(Color.BLACK);
		
		MainPaneSwitcherHandler handler = new MainPaneSwitcherHandler();
		SwitcherBar mainPaneSwitcherBar = new SwitcherBar(handler);
		SwitcherElement checkedout = new SwitcherElement("已结订单", true);
		checkedout.setActionCommand("checked");
		SwitcherElement hangup = new SwitcherElement("未结订单");
		hangup.setActionCommand("unchecked");
		mainPaneSwitcherBar.addElement(checkedout);
		mainPaneSwitcherBar.addElement(hangup);
		
		JButton minimizeBtn = ButtonFactory.createImageButton("skin/gray/images/16x16/minimize_2.png",null);
		final JButton maximizeBtn = ButtonFactory.createImageButton("skin/gray/images/16x16/maximize_2.png",null);
		maximizeBtn.setToolTipText("最大化");
		menubar.add(maximizeBtn);
		JButton closeBtn = ButtonFactory.createImageButton("skin/gray/images/16x16/close.png",null);
		closeBtn.setToolTipText("关闭");
		rightP.add(mainPaneSwitcherBar);
		rightP.add(minimizeBtn);
		rightP.add(maximizeBtn);
		rightP.add(closeBtn);
		menubar.add(rightP);
		frameListener.setMaximizeBtn(maximizeBtn);
		JMenuItem sysInit = ButtonFactory.createMenuItem("系统初始化","skin/gray/images/24x24/initBtn.png");
		JMenuItem baseReset = ButtonFactory.createMenuItem("基础数据重置", "skin/gray/images/24x24/basereset.png");
		JMenuItem dataExport = ButtonFactory.createMenuItem("订单数据导出", "skin/gray/images/24x24/export_0.png");
		JMenuItem dataImport = ButtonFactory.createMenuItem("订单数据导入", "skin/gray/images/24x24/import_0.png");
		system.add(sysInit);
		system.add(baseReset);
		operation.add(dataExport);
		operation.add(dataImport);

		JMenuItem expressRate = ButtonFactory.createMenuItem("外送率统计", "skin/gray/images/24x24/takeout.png");
		JMenuItem earning = ButtonFactory.createMenuItem("营业额统计", "skin/gray/images/24x24/statistic.png");
		JMenuItem costControl = ButtonFactory.createMenuItem("成本统计", "skin/gray/images/24x24/cost.png");
		JMenuItem saleStatistic = ButtonFactory.createMenuItem("菜品销量统计", "skin/gray/images/24x24/dish_1.png");
		statistic.add(expressRate);
		statistic.add(earning);
		statistic.add(costControl);
		statistic.add(saleStatistic);
		
		JMenuItem dishManage = ButtonFactory.createMenuItem("菜品管理", "skin/gray/images/24x24/dish.png");
		JMenuItem inventoryManage = ButtonFactory.createMenuItem("库存管理", "skin/gray/images/24x24/stockmanage.png");
		JMenuItem activityManage = ButtonFactory.createMenuItem("活动管理", "skin/gray/images/24x24/activity.png");
		management.add(dishManage);
		management.add(inventoryManage);
		management.add(activityManage);

		MenuItemActionHandler mhandler = new MenuItemActionHandler();
//		mhandler.setQueryPanel(queryPanel);
		baseReset.addActionListener(mhandler.doBaseInfoResetAction());
		// 外送率统计事件
		expressRate.addActionListener(mhandler.doExpressRateStatisticAction());
		// 营业额统计事件
		earning.addActionListener(mhandler.doEarningStatisticAction());
		//菜品销量统计
		saleStatistic.addActionListener(mhandler.doSaleStatisticAction());

		// 数据导出
		DataExportImportListener dataExportListener = new OrderDataExportImportListener(DataExportImportListener.EXPORT);
		dataExport.addActionListener(dataExportListener);
		
		//数据导入
		OrderDataExportImportListener dataImportListener = new OrderDataExportImportListener(DataExportImportListener.IMPORT);
//		dataImportListener.setConclusionPane(conclusionPane);
//		dataImportListener.setContentPane(contentPane);
//		dataImportListener.setQueryPanel(queryPanel);
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
		inventoryManage.addActionListener(mhandler.doInventoryManagmentAction());
		
		//菜品管理
		dishManage.addActionListener(mhandler.doDishManagmentAction());
		
		// 活动设置
		activityManage.addActionListener(mhandler.doActivitySettingAction());
		return menubar;
	}
}
