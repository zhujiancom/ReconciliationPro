package com.rci.ui.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

import com.rci.config.PropertyConstants;
import com.rci.tools.properties.PropertyUtils;
import com.rci.ui.swing.listeners.ActionHandler;
import com.rci.ui.swing.listeners.CleanListener;
import com.rci.ui.swing.listeners.QueryListener;
import com.rci.ui.swing.listeners.StockListener;
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
		
		/* 绑定菜单条*/
		this.setJMenuBar(buildMenuBar());
		/* 绑定查询form */
		containerPanel.add(buildFormPanel(), BorderLayout.NORTH);
		
		JSplitPane dataPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true);
		containerPanel.add(dataPanel, BorderLayout.CENTER);
		dataPanel.setDividerLocation(800);
		dataPanel.setDividerSize(5);
		mainScrollPane = new JScrollPane(); //将表格加入到滚动条组件中
		mainScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		mainTable = new OrderTable();
		mainScrollPane.setViewportView(mainTable);
		subScrollPane = new JScrollPane(); //将表格加入到滚动条组件中
		subScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		itemTable = new JTable();
		subScrollPane.setViewportView(itemTable);
		dataPanel.add(mainScrollPane);
		dataPanel.add(subScrollPane);
		
		/* 绑定 总结页脚 */
		containerPanel.add(buildConclusionPanel(), BorderLayout.SOUTH);
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
		
		menubar.add(system);
		menubar.add(view);
		menubar.add(setting);
		JMenuItem sysInit = new JMenuItem("系统初始化");
		JMenuItem baseReset = new JMenuItem("基础数据重置");
		JMenuItem viewStock = new JMenuItem("库存查看");
		JMenuItem setStock = new JMenuItem("库存进货");
		system.add(sysInit);
		system.add(baseReset);
		view.add(viewStock);
		setting.add(setStock);
		ActionHandler handler = new ActionHandler();
		sysInit.addActionListener(handler.dataInit());
		baseReset.addActionListener(handler.baseReset());
		viewStock.addActionListener(StockListener.VIEW);
		setStock.addActionListener(StockListener.RESTOCK);
		return menubar;
	}
	
	/**
	 * 
	 * Describle(描述)：底部 数据统计面板
	 *
	 * 方法名称：buildConclusionPanel
	 *
	 * 所在类名：MainFrame
	 *
	 * Create Time:2015年7月28日 下午4:45:36
	 *  
	 * @return
	 */
	private JPanel buildConclusionPanel(){
		JPanel conclusionPanel = new JPanel();
		conclusionPanel.setLayout(new GridLayout(0, 2));
		
		JPanel firstGroup = new JPanel();
		firstGroup.setBounds(new Rectangle(100,20));
		firstGroup.setLayout(new BoxLayout(firstGroup, BoxLayout.X_AXIS));
		JPanel secondGroup = new JPanel();
		secondGroup.setLayout(new BoxLayout(secondGroup, BoxLayout.X_AXIS));
		JPanel thirdGroup = new JPanel();
		thirdGroup.setLayout(new BoxLayout(thirdGroup, BoxLayout.X_AXIS));
		JPanel forthGroup = new JPanel();
		forthGroup.setLayout(new BoxLayout(forthGroup, BoxLayout.X_AXIS));
		JPanel fifthGroup = new JPanel();
		fifthGroup.setLayout(new BoxLayout(fifthGroup, BoxLayout.X_AXIS));
		JPanel sixthGroup = new JPanel();
		sixthGroup.setLayout(new BoxLayout(sixthGroup, BoxLayout.X_AXIS));
		JPanel sevenGroup = new JPanel();
		sevenGroup.setLayout(new BoxLayout(sevenGroup, BoxLayout.X_AXIS));
		JPanel eightGroup = new JPanel();
		eightGroup.setLayout(new BoxLayout(eightGroup, BoxLayout.X_AXIS));
		/* 现金统计  */
		JLabel cash = new JLabel("收银机现金入账总额：");
		cashValue = new JLabel();
		cashValue.setForeground(Color.RED);
		JPanel cashPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		cashPanel.add(cash);
		cashPanel.add(cashValue);
		/* 银联刷卡统计  */
		JLabel pos = new JLabel("pos机入账总额：");
		posValue = new JLabel();
		posValue.setForeground(Color.RED);
		JPanel posPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		posPanel.add(pos);
		posPanel.add(posValue);
		/* 美团在线支付统计  */
		JLabel mt = new JLabel("美团入账总额：");
		mtValue = new JLabel();
		mtRemark = new JLabel();
		mtValue.setForeground(Color.RED);
		mtRemark.setForeground(Color.BLUE);
		JPanel mtPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		mtPanel.add(mt);
		mtPanel.add(mtValue);
		mtPanel.add(mtRemark);
		/* 大众点评团购在线支付统计  */
		JLabel tg = new JLabel("大众点评团购入账总额：");
//		JLabel tgRemark = new JLabel("50代金券 1 张，100代金券 0 张，套餐A 0 张，套餐B 2 张，套餐C 2 张");
		tgValue = new JLabel();
		tgRemark = new JLabel();
		tgValue.setForeground(Color.RED);
		tgRemark.setForeground(Color.BLUE);
		JPanel tgPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		tgPanel.add(tg);
		tgPanel.add(tgValue);
		tgPanel.add(tgRemark);
		/* 闪惠支付统计  */
		JLabel sh = new JLabel("大众点评闪惠入账总额：");
		shValue = new JLabel();
		shValue.setForeground(Color.RED);
		JPanel shPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		shPanel.add(sh);
		shPanel.add(shValue);
		/* 饿了么补贴金额统计  */
		JLabel eleFreeLabel = new JLabel("饿了么补贴总额：");
		eleFreeValue = new JLabel();
		eleFreeValue.setForeground(Color.RED);
		JPanel eleFreePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		eleFreePanel.add(eleFreeLabel);
		eleFreePanel.add(eleFreeValue);
		/* 饿了么在线支付统计  */
		JLabel ele = new JLabel("饿了么入账总额：");
		eleValue = new JLabel();
		eleValue.setForeground(Color.RED);
		JPanel elePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		elePanel.add(ele);
		elePanel.add(eleValue);
		/* 淘点点统计  */
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
		/* 美团超级代金券支付统计  */
		JLabel mtSuperLabel = new JLabel("美团超级代金券入账总额：");
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
		/* 总免单金额统计  */
		JLabel freeLabel = new JLabel("总免单金额：");
		freeValue = new JLabel();
		freeValue.setForeground(Color.RED);
		JPanel freePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		freePanel.add(freeLabel);
		freePanel.add(freeValue);
		/* 外送率统计  */
		JLabel expRateLabel = new JLabel("外送率：");
		expRateValue = new JLabel();
		expRateValue.setForeground(Color.RED);
		JPanel expRatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		expRatePanel.add(expRateLabel);
		expRatePanel.add(expRateValue);
		/* 当日营业额统计  */
		JLabel totalLabel = new JLabel("当日营业收入总额：");
		totalValue = new JLabel();
		totalValue.setForeground(Color.RED);
		JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		totalPanel.add(totalLabel);
		totalPanel.add(totalValue);
		
		firstGroup.add(cashPanel);
		firstGroup.add(posPanel);
		secondGroup.add(elePanel);
		secondGroup.add(eleFreePanel);
		thirdGroup.add(tgPanel);
		forthGroup.add(mtPanel);
		fifthGroup.add(shPanel);
		fifthGroup.add(Box.createHorizontalStrut(25));
		fifthGroup.add(mtSuperPanel);
		sixthGroup.add(tddPanel);
		sixthGroup.add(freePanel);
		sevenGroup.add(expRatePanel);
		sevenGroup.add(Box.createHorizontalStrut(80));
		sevenGroup.add(totalPanel);
		
		conclusionPanel.add(firstGroup);
		conclusionPanel.add(secondGroup);
		conclusionPanel.add(thirdGroup);
		conclusionPanel.add(forthGroup);
		conclusionPanel.add(fifthGroup);
		conclusionPanel.add(sixthGroup);
		conclusionPanel.add(sevenGroup);
		
		return conclusionPanel;
	}
	
	/**
	 * 
	 * Describle(描述)：构建 查询表单
	 *
	 * 方法名称：buildFormPanel
	 *
	 * 所在类名：MainFrame
	 *
	 * Create Time:2015年7月28日 下午4:49:10
	 *  
	 * @return
	 */
	private JPanel buildFormPanel(){
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
		return formPanel;
	}
}
