package com.rci.ui.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

import com.rci.config.PropertyConstants;
import com.rci.tools.properties.PropertyUtils;
import com.rci.ui.swing.listeners.CleanListener;
import com.rci.ui.swing.listeners.QueryListener;
import com.rci.ui.swing.listeners.SystemInitHandler;
import com.rci.ui.swing.model.OrderTable;

public class MainFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8216786708977859424L;
	
	/**
	 * 饿了么刷单参数设置
	 */
	private JTextField eleOnlinePayAmount; //饿了么刷单在线支付总金额
	private JTextField eleOrderCount;    //饿了么刷单数量
	
	private JButton queryBtn;
	private JButton cleanBtn;
	private JTextField timeInput;
	private JScrollPane mainScrollPane;
	private JScrollPane subScrollPane;
	private JTable mainTable; //展示order 列表
	private JTable itemTable; //展示 orderItem 列表
	
	private JLabel cashValue;
	private JLabel posValue;
	private JLabel mtValue;
	private JLabel tgValue;
	private JLabel lsValue;
	private JLabel tgRemark;
	private JLabel mtRemark;
	private JLabel shValue;
	private JLabel eleFreeValue;
	private JLabel eleValue;
	private JLabel tddValue;
	private JLabel mtwmValue;
	private JLabel mtwmFreeValue;
	private JLabel mtSuperValue;
	private JLabel mtSuperFreeValue;
	private JLabel freeValue;
	private JLabel totalValue;
	private JLabel expRateValue; //外送率
	
	public MainFrame(){
		initComponent();
		QueryListener listener = new QueryListener(mainTable, itemTable);
		listener.setTimeInput(timeInput);
		listener.setEleValue(eleValue);
		listener.setMtValue(mtValue);
		listener.setCashValue(cashValue);
		listener.setPosValue(posValue);
		listener.setEleFreeValue(eleFreeValue);
		listener.setTddValue(tddValue);
		listener.setShValue(shValue);
		listener.setTgValue(tgValue);
		listener.setMtwmValue(mtwmValue);
		listener.setMtwmFreeValue(mtwmFreeValue);
		listener.setMtSuperValue(mtSuperValue);
		listener.setMtSuperFreeValue(mtSuperFreeValue);
		listener.setFreeValue(freeValue);
		listener.setTotalValue(totalValue);
		listener.setTgRemark(tgRemark);
		listener.setMtRemark(mtRemark);
		listener.setExpRateValue(expRateValue);
		listener.setLsValue(lsValue);
		listener.setEleOnlinePayAmount(eleOnlinePayAmount);
		listener.setEleOrderCount(eleOrderCount);
		queryBtn.registerKeyboardAction(listener, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0), JComponent.WHEN_IN_FOCUSED_WINDOW);
		queryBtn.addActionListener(listener);
		
		CleanListener clistener = new CleanListener(mainTable, itemTable);
		clistener.setTimeInput(timeInput);
		clistener.setEleValue(eleValue);
		clistener.setMtValue(mtValue);
		clistener.setCashValue(cashValue);
		clistener.setPosValue(posValue);
		clistener.setEleFreeValue(eleFreeValue);
		clistener.setTddValue(tddValue);
		clistener.setShValue(shValue);
		clistener.setTgValue(tgValue);
		clistener.setMtwmValue(mtwmValue);
		clistener.setMtwmFreeValue(mtwmFreeValue);
		clistener.setMtSuperValue(mtSuperValue);
		clistener.setMtSuperFreeValue(mtSuperFreeValue);
		clistener.setFreeValue(freeValue);
		clistener.setTotalValue(totalValue);
		clistener.setTgRemark(tgRemark);
		clistener.setMtRemark(mtRemark);
		clistener.setExpRateValue(expRateValue);
		clistener.setLsValue(lsValue);
		cleanBtn.addActionListener(clistener);
		
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
		
		JMenuBar menubar = new JMenuBar();
		this.setJMenuBar(menubar);
		JMenu sysMenu = new JMenu("系统");
		JMenu helpMenu = new JMenu("帮助");
		menubar.add(sysMenu);
		menubar.add(helpMenu);
		JMenuItem dataInit = new JMenuItem("系统初始化");
		JMenuItem baseReset = new JMenuItem("基础数据重置");
		JMenuItem settings = new JMenuItem("方案设置");
		JMenuItem helpInfo = new JMenuItem("帮助信息");
		sysMenu.add(dataInit);
		sysMenu.add(baseReset);
		sysMenu.add(settings);
		helpMenu.add(helpInfo);
		SystemInitHandler handler = new SystemInitHandler();
		dataInit.addActionListener(handler.dataInit());
		baseReset.addActionListener(handler.baseReset());
		settings.addActionListener(handler.settings());
		helpMenu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "帮助信息。。。。。");
			}
		});
		
		//饿了么刷单金额设置
		JLabel eleOnlinePayLabel = new JLabel("饿了么刷单在线支付总金额");
		eleOnlinePayAmount = new JTextField(5);
		JLabel eleOrderCountLabel = new JLabel("饿了么刷单数量");
		eleOrderCount = new JTextField(5);
		
		JPanel formPanel = new JPanel();
		JLabel rciTime = new JLabel("日期");
		timeInput = new JTextField(10);
		queryBtn = new JButton("查询");
		cleanBtn = new JButton("清空");
		formPanel.add(eleOnlinePayLabel);
		formPanel.add(eleOnlinePayAmount);
		formPanel.add(eleOrderCountLabel);
		formPanel.add(eleOrderCount);
		
		formPanel.add(rciTime);
		formPanel.add(timeInput);
		formPanel.add(queryBtn);
		formPanel.add(cleanBtn);
		formPanel.setVisible(true);
		formPanel.setSize(500, 300);
		formPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		containerPanel.add(formPanel, BorderLayout.NORTH);
		
//		JPanel dataPanel = new JPanel();
		JSplitPane dataPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true);
		containerPanel.add(dataPanel, BorderLayout.CENTER);
		dataPanel.setDividerLocation(800);
		dataPanel.setDividerSize(5);
		mainScrollPane = new JScrollPane(); //将表格加入到滚动条组件中
//		mainScrollPane.setMaximumSize(new Dimension(400,600));
		mainScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		mainTable = new OrderTable();
		mainScrollPane.setViewportView(mainTable);
		subScrollPane = new JScrollPane(); //将表格加入到滚动条组件中
//		subScrollPane.setMaximumSize(new Dimension(300,600));
//		subScrollPane.setMinimumSize(new Dimension(300,600));
		subScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		itemTable = new JTable();
		subScrollPane.setViewportView(itemTable);
		dataPanel.add(mainScrollPane);
		dataPanel.add(subScrollPane);
		
		JPanel conclusionPanel = new JPanel();
		containerPanel.add(conclusionPanel, BorderLayout.SOUTH);
		conclusionPanel.setLayout(new GridLayout(15, 1));
		
		JLabel cash = new JLabel("收银机现金入账总额：");
		cashValue = new JLabel();
		cashValue.setForeground(Color.RED);
		JPanel cashPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		cashPanel.add(cash);
		cashPanel.add(cashValue);
		
		JLabel pos = new JLabel("pos机入账总额：");
		posValue = new JLabel();
		posValue.setForeground(Color.RED);
		JPanel posPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		posPanel.add(pos);
		posPanel.add(posValue);
		
		JLabel mt = new JLabel("美团入账总额：");
		mtValue = new JLabel();
		mtRemark = new JLabel();
		mtValue.setForeground(Color.RED);
		JPanel mtPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		mtPanel.add(mt);
		mtPanel.add(mtValue);
		mtPanel.add(mtRemark);
		
		JLabel tg = new JLabel("大众点评团购入账总额：");
//		JLabel tgRemark = new JLabel("50代金券 1 张，100代金券 0 张，套餐A 0 张，套餐B 2 张，套餐C 2 张");
		tgValue = new JLabel();
		tgRemark = new JLabel();
		tgValue.setForeground(Color.RED);
		JPanel tgPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		tgPanel.add(tg);
		tgPanel.add(tgValue);
		tgPanel.add(tgRemark);
		
		JLabel sh = new JLabel("大众点评闪惠入账总额：");
		shValue = new JLabel();
		shValue.setForeground(Color.RED);
		JPanel shPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		shPanel.add(sh);
		shPanel.add(shValue);
		
		JLabel eleFreeLabel = new JLabel("饿了么补贴总额：");
		eleFreeValue = new JLabel();
		eleFreeValue.setForeground(Color.RED);
		JPanel eleFreePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		eleFreePanel.add(eleFreeLabel);
		eleFreePanel.add(eleFreeValue);
		
		JLabel ele = new JLabel("饿了么入账总额：");
		eleValue = new JLabel();
		eleValue.setForeground(Color.RED);
		JPanel elePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		elePanel.add(ele);
		elePanel.add(eleValue);
		
		JLabel tdd = new JLabel("淘点点入账总额：");
		tddValue = new JLabel();
		tddValue.setForeground(Color.RED);
		JPanel tddPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		tddPanel.add(tdd);
		tddPanel.add(tddValue);
		
		JLabel mtwm = new JLabel("美团外卖入账总额：");
		mtwmValue = new JLabel();
		mtwmValue.setForeground(Color.RED);
		JPanel mtwmPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		mtwmPanel.add(mtwm);
		mtwmPanel.add(mtwmValue);
		
		JLabel mtwmFreeLabel = new JLabel("美团外卖补贴总额：");
		mtwmFreeValue = new JLabel();
		mtwmFreeValue.setForeground(Color.RED);
		JPanel mtwmFreePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		mtwmFreePanel.add(mtwmFreeLabel);
		mtwmFreePanel.add(mtwmFreeValue);
		
		JLabel mtSuperLabel = new JLabel("美团超级代金券支付金额：");
		mtSuperValue = new JLabel();
		mtSuperValue.setForeground(Color.RED);
		JPanel mtSuperPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		mtSuperPanel.add(mtSuperLabel);
		mtSuperPanel.add(mtSuperValue);
		
		JLabel mtSuperFreeLabel = new JLabel("美团超级代金券补贴金额：");
		mtSuperFreeValue = new JLabel();
		mtSuperFreeValue.setForeground(Color.RED);
		JPanel mtSuperFreePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		mtSuperFreePanel.add(mtSuperFreeLabel);
		mtSuperFreePanel.add(mtSuperFreeValue);
		
		JLabel lsLabel = new JLabel("拉手网：");
		lsValue = new JLabel();
		lsValue.setForeground(Color.RED);
		JPanel lsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		lsPanel.add(lsLabel);
		lsPanel.add(lsValue);
		
		JLabel freeLabel = new JLabel("总免单金额：");
		freeValue = new JLabel();
		freeValue.setForeground(Color.RED);
		JPanel freePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		freePanel.add(freeLabel);
		freePanel.add(freeValue);
		
		JLabel expRateLabel = new JLabel("外送率：");
		expRateValue = new JLabel();
		expRateValue.setForeground(Color.RED);
		JPanel expRatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		expRatePanel.add(expRateLabel);
		expRatePanel.add(expRateValue);
		
		JLabel totalLabel = new JLabel("当日营业收入总额：");
		totalValue = new JLabel();
		totalValue.setForeground(Color.RED);
		JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		totalPanel.add(totalLabel);
		totalPanel.add(totalValue);
		
		conclusionPanel.add(cashPanel);
		conclusionPanel.add(posPanel);
		conclusionPanel.add(tgPanel);
		conclusionPanel.add(shPanel);
		conclusionPanel.add(mtPanel);
		conclusionPanel.add(elePanel);
		conclusionPanel.add(eleFreePanel);
		conclusionPanel.add(mtwmPanel);
//		conclusionPanel.add(mtwmFreePanel);
		conclusionPanel.add(lsPanel);
		conclusionPanel.add(mtSuperPanel);
		conclusionPanel.add(mtSuperFreePanel);
		conclusionPanel.add(tddPanel);
		conclusionPanel.add(freePanel);
		conclusionPanel.add(expRatePanel);
		conclusionPanel.add(totalPanel);
	}
}
