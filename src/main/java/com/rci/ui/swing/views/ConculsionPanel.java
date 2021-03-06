package com.rci.ui.swing.views;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.springframework.util.CollectionUtils;

import com.rci.contants.BusinessConstant;
import com.rci.enums.BusinessEnums.AccountCode;
import com.rci.enums.BusinessEnums.Vendor;
import com.rci.service.IOrderAccountRefService;
import com.rci.service.IOrderService;
import com.rci.service.core.StatisticCenterFacade;
import com.rci.tools.DateUtil;
import com.rci.tools.SpringUtils;
import com.rci.ui.swing.vos.OrderVO;

public class ConculsionPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2353366277940919271L;
	private JLabel cashValue;
	private JLabel posValue;
	private JLabel mtValue;
	private JLabel tgValue;
	private JLabel lsValue;
	private JLabel tgRemark;
	private JLabel mtRemark;
	private DisplayLabel<String,Long> eleRemark;
	private DisplayLabel<String,BigDecimal> eleSdRemark;
	private JLabel shValue;
	private JLabel eleFreeValue;
	private JLabel eleValue;
	private JLabel tddValue;
	private JLabel alipayValue;
	private JLabel mtwmValue;
	private JLabel mtwmFreeValue;
	private DisplayLabel<String,Long> mtwmRemark;
	private JLabel mtSuperValue;
	private JLabel mtSuperFreeValue;
	private JLabel freeValue;
	private JLabel totalValue;
	private JLabel wmcrValue;
	private JLabel wmcrbtValue;
	private DisplayLabel<String,Long> wmcrRemark;
	private JLabel bdwmValue;
	private JLabel bdwmbtValue;
	private DisplayLabel<String,Long> bdwmRemark;
	private JLabel plqValue;
	private JLabel plqbtValue;
	private DisplayLabel<String,Long> plqRemark;
	private JLabel bdnmValue;
	private JLabel bdnmRemark;
	private JLabel bdnmddfValue;
	
//	private JLabel expRateValue; //外送率
	private Map<AccountCode,BigDecimal> dailyPostAccountMap;  //每日账户入账金额
	private Date queryDate;  //统计日期
	private StatisticCenterFacade facade;
	
	public ConculsionPanel(){
		buildPane();
		facade = (StatisticCenterFacade) SpringUtils.getBean("StatisticCenterFacade");
	}
	
	/**
	 * 
	 * Describle(描述)：底部 数据统计面板
	 *
	 * 方法名称：buildPane
	 *
	 * 所在类名：ConculsionPanel
	 *
	 * Create Time:2015年7月29日 上午9:25:31
	 *
	 */
	public void buildPane(){
		this.setLayout(new GridLayout(0, 2));
		
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
		JPanel nineGroup = new JPanel();
		nineGroup.setLayout(new BoxLayout(nineGroup, BoxLayout.X_AXIS));
		JPanel tenGroup = new JPanel();
		tenGroup.setLayout(new BoxLayout(tenGroup, BoxLayout.X_AXIS));
		JPanel elevenGroup = new JPanel();
		elevenGroup.setLayout(new BoxLayout(elevenGroup, BoxLayout.X_AXIS));
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
		JLabel eleFreeLabel = new JLabel("饿了么补贴金额：");
		eleFreeValue = new JLabel();
		eleFreeValue.setForeground(Color.RED);
		eleSdRemark = new DisplayLabel<String, BigDecimal>("刷单补贴金额");
		eleSdRemark.setForeground(Color.BLUE);
		JPanel eleFreePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		eleFreePanel.add(eleFreeLabel);
		eleFreePanel.add(eleFreeValue);
		eleFreePanel.add(eleSdRemark);
		/* 饿了么在线支付统计  */
		JLabel ele = new JLabel("饿了么入账总额：");
		eleValue = new JLabel();
		eleValue.setForeground(Color.RED);
		JPanel elePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		eleRemark = new DisplayLabel<String,Long>("有效订单","单");
		eleRemark.setForeground(Color.BLUE);
		elePanel.add(ele);
		elePanel.add(eleValue);
		elePanel.add(eleRemark);
		
		/* 淘点点&支付宝统计  */
		JLabel tdd = new JLabel("淘点点入账总额：");
		tddValue = new JLabel();
		tddValue.setForeground(Color.RED);
		JPanel tddPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		tddPanel.add(tdd);
		tddPanel.add(tddValue);
		
		/* 支付宝统计  */
		JLabel alipay = new JLabel("支付宝入账总额：");
		alipayValue = new JLabel();
		alipayValue.setForeground(Color.RED);
		JPanel alipayPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		alipayPanel.add(alipay);
		alipayPanel.add(alipayValue);
		
		JLabel mtwm = new JLabel("美团外卖入账总额：");
		mtwmValue = new JLabel();
		mtwmValue.setForeground(Color.RED);
		JPanel mtwmPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		mtwmRemark = new DisplayLabel<String,Long>("有效订单","单");
		mtwmRemark.setForeground(Color.BLUE);
		mtwmPanel.add(mtwm);
		mtwmPanel.add(mtwmValue);
		mtwmPanel.add(mtwmRemark);
		
		JLabel mtwmFreeLabel = new JLabel("美团外卖补贴金额：");
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
		
		/* 外卖超人 */
//		JLabel wmcrLabel = new JLabel("外卖超人入账总额：");
//		wmcrValue = new JLabel();
//		wmcrValue.setForeground(Color.RED);
//		wmcrRemark = new DisplayLabel<String,Long>("有效订单","单");
//		wmcrRemark.setForeground(Color.BLUE);
//		JPanel wmcrPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
//		wmcrPanel.add(wmcrLabel);
//		wmcrPanel.add(wmcrValue);
//		wmcrPanel.add(wmcrRemark);
		
		/* 外卖超人补贴 */
//		JLabel wmcrbtLabel = new JLabel("外卖超人补贴总额：");
//		wmcrbtValue = new JLabel();
//		wmcrbtValue.setForeground(Color.RED);
//		JPanel wmcrbtPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
//		wmcrbtPanel.add(wmcrbtLabel);
//		wmcrbtPanel.add(wmcrbtValue);
		
		/* 百度外卖 */
		JLabel bdwmLabel = new JLabel("百度外卖入账总额：");
		bdwmValue = new JLabel();
		bdwmValue.setForeground(Color.RED);
		bdwmRemark = new DisplayLabel<String,Long>("有效订单","单");
		bdwmRemark.setForeground(Color.BLUE);
		JPanel bdwmPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		bdwmPanel.add(bdwmLabel);
		bdwmPanel.add(bdwmValue);
		bdwmPanel.add(bdwmRemark);
		
		/* 百度外卖补贴 */
		JLabel bdwmbtLabel = new JLabel("百度外卖补贴总额：");
		bdwmbtValue = new JLabel();
		bdwmbtValue.setForeground(Color.RED);
		JPanel bdwmbtPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		bdwmbtPanel.add(bdwmbtLabel);
		bdwmbtPanel.add(bdwmbtValue);
		
		/* 总免单金额统计  */
		JLabel freeLabel = new JLabel("线下免单金额：");
		freeValue = new JLabel();
		freeValue.setForeground(Color.GREEN);
		JPanel freePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		freePanel.add(freeLabel);
		freePanel.add(freeValue);
		
		/* 派乐趣 */
		JLabel plqLabel = new JLabel("派乐趣入账总额:");
		plqValue = new JLabel();
		plqValue.setForeground(Color.RED);
		plqRemark = new DisplayLabel<String,Long>("有效订单","单");
		plqRemark.setForeground(Color.BLUE);
		JPanel plqPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		plqPanel.add(plqLabel);
		plqPanel.add(plqValue);
		plqPanel.add(plqRemark);
		
		JLabel plqbtLabel = new JLabel("派乐趣补贴总额：");
		plqbtValue = new JLabel();
		plqbtValue.setForeground(Color.RED);
		JPanel plqbtPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		plqbtPanel.add(plqbtLabel);
		plqbtPanel.add(plqbtValue);
		
		/* 百度糯米代金券使用统计 */
		JLabel bdnmLabel = new JLabel("百度糯米入账总额：");
		bdnmValue = new JLabel();
		bdnmRemark = new JLabel();
		bdnmValue.setForeground(Color.RED);
		bdnmRemark.setForeground(Color.BLUE);
		JPanel bdnmPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		bdnmPanel.add(bdnmLabel);
		bdnmPanel.add(bdnmValue);
		bdnmPanel.add(bdnmRemark);
		
		JLabel bdnmddfLabel = new JLabel("百度糯米到店付总额：");
		bdnmddfValue = new JLabel();
		bdnmddfValue.setForeground(Color.RED);
		JPanel bdnmddfPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		bdnmddfPanel.add(bdnmddfLabel);
		bdnmddfPanel.add(bdnmddfValue);
		/* 外送率统计  */
//		JLabel expRateLabel = new JLabel("外送率：");
//		expRateValue = new JLabel();
//		expRateValue.setForeground(Color.RED);
//		JPanel expRatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
//		expRatePanel.add(expRateLabel);
//		expRatePanel.add(expRateValue);
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
		sixthGroup.add(mtwmPanel);
		sixthGroup.add(mtwmFreePanel);
		sevenGroup.add(bdwmPanel);
		sevenGroup.add(Box.createHorizontalStrut(10));
//		sevenGroup.add(bdwmbtPanel);
		sevenGroup.add(alipayPanel);
		eightGroup.add(tddPanel);
		eightGroup.add(freePanel);
		tenGroup.add(plqPanel);
		tenGroup.add(Box.createHorizontalStrut(10));
		tenGroup.add(plqbtPanel);
		elevenGroup.add(bdnmPanel);
		nineGroup.add(bdnmddfPanel);
		nineGroup.add(Box.createHorizontalStrut(10));
		nineGroup.add(totalPanel);
		
		this.add(firstGroup);
		this.add(secondGroup);
		this.add(thirdGroup);
		this.add(forthGroup);
		this.add(fifthGroup);
		this.add(sixthGroup);
		this.add(sevenGroup);
		this.add(eightGroup);
		this.add(tenGroup);
		this.add(elevenGroup);
		this.add(nineGroup);
	}
	
	public void clearData(){
		cashValue.setText("");
		posValue.setText("");
		mtValue.setText("");
		tgValue.setText("");
		shValue.setText("");
		eleFreeValue.setText("");
		eleValue.setText("");
		tddValue.setText("");
		alipayValue.setText("");
		mtwmValue.setText("");
		mtwmFreeValue.setText("");
		mtSuperValue.setText("");
//		mtSuperFreeValue.setText("");
		freeValue.setText("");
		totalValue.setText("");
//		lsValue.setText("");
		tgRemark.setText("");
		mtRemark.setText("");
		eleRemark.setText("");
		eleSdRemark.setText("");
		mtwmRemark.setText("");
//		expRateValue.setText("");
//		wmcrValue.setText("");
//		wmcrbtValue.setText("");
//		wmcrRemark.setText("");
		bdwmValue.setText("");
		bdwmbtValue.setText("");
		bdwmRemark.setText("");
		plqValue.setText("");
		plqbtValue.setText("");
		plqRemark.setText("");
		bdnmValue.setText("");
		bdnmRemark.setText("");
		bdnmddfValue.setText("");
	}
	
	public void refreshUI() {
//		String cashmachine = getTotalAmount(BusinessConstant.AccountCode_CASH_MACHINE).toString();
//		String pos = getTotalAmount(BusinessConstant.AccountCode_POS).toString();
//		String mt = getTotalAmount(BusinessConstant.AccountCode_MT).toString();
//		String dptg = getTotalAmount(BusinessConstant.AccountCode_DPTG).toString();
//		String sh = getTotalAmount(BusinessConstant.AccountCode_DPSH).toString();
//		String ele = getTotalAmount(BusinessConstant.AccountCode_ELE).toString();
//		String ele_allowance = getTotalAmount(BusinessConstant.AccountCode_FREE_ELE).toString();
		BigDecimal elesdremark = getELESDAllowanceAmount(queryDate);								//饿了么刷单金额
//		String tdd = getTotalAmount(BusinessConstant.AccountCode_ALIPAY).toString();
//		String mtsuper = getTotalAmount(BusinessConstant.AccountCode_MT_SUPER).toString();
//		String mtwm = getTotalAmount(BusinessConstant.AccountCode_MTWM).toString();
//		String mtwm_allowance = getTotalAmount(BusinessConstant.AccountCode_FREE_MTWM).toString();
//		String offlineFree = getTotalAmount(BusinessConstant.AccountCode_FREE_OFFLINE).toString();
//		String totalAmount = getTotalDayAmount(queryDate).toString();
//		String dptgRemark = getTicketStatistic(queryDate,Vendor.DZDP);
//		String mttgRemark = getTicketStatistic(queryDate,Vendor.MT);
//		Long eleOrderNum = getValidCount(queryDate, Vendor.ELE);
//		Long mtwmOrderNum = getValidCount(queryDate,Vendor.MTWM);
//		String wmcr = getTotalAmount(AccountCode.WMCR).toString();
//		String freewmcr = getTotalAmount(AccountCode.FREE_WMCR).toString();
//		Long wmcrOrderNum = getValidCount(queryDate,Vendor.WMCR);
//		String plq = getTotalAmount(BusinessConstant.AccountCode_PLQ).toString();
//		String plqbt = getTotalAmount(BusinessConstant.AccountCode_FREE_PLQ).toString();
//		Long plqOrderNum = getValidCount(queryDate,Vendor.PLQ);
//		String bdnm = getTotalAmount(BusinessConstant.AccountCode_BDNM).toString();
//		String bdnmRemark = getTicketStatistic(queryDate,Vendor.BDNM);
		
		String cashmachine = getDailyPostAccountAmount(AccountCode.CASH_MACHINE).toString(); 		//收银机每日入账金额
		String pos = getDailyPostAccountAmount(AccountCode.ONLINE_POS).toString();					//pos机每日入账金额
		String ele = getDailyPostAccountAmount(AccountCode.ONLINE_ELE).toString();					//饿了么
		String ele_allowance = getDailyPostAccountAmount(AccountCode.ALLOWANCE_ELE).toString();		//饿了么补贴
		String dptg = getDailyPostAccountAmount(AccountCode.ONLINE_DPTG).toString();				//点评团购
		String dptgRemark = getTicketStatistic(queryDate,Vendor.DZDP);								//点评团购券使用信息
		String mt = getDailyPostAccountAmount(AccountCode.ONLINE_MT).toString();					//美团团购
		String mttgRemark = getTicketStatistic(queryDate,Vendor.MT);								//美团团购券使用信息
		String sh = getDailyPostAccountAmount(AccountCode.DPSH).toString();							//大众点评闪惠
		String mtsuper = getDailyPostAccountAmount(AccountCode.MT_SUPER).toString();				//美团超券
		String mtwm = getDailyPostAccountAmount(AccountCode.ONLINE_MTWM).toString();				//美团外卖
		String mtwm_allowance = getDailyPostAccountAmount(AccountCode.ALLOWANCE_MTWM).toString();	//美团外卖补贴
		String bdwm = getDailyPostAccountAmount(AccountCode.ONLINE_BDWM).toString();				//百度外卖
		String bdwm_allowance = "";																	//百度外卖补贴
		String tdd = getDailyPostAccountAmount(AccountCode.ONLINE_TDD).toString();					//淘点点
		String alipay = getDailyPostAccountAmount(AccountCode.ONLINE_ALIPAY).toString();			//支付宝
		String offlineFree = getDailyPostAccountAmount(AccountCode.FREE_OFFLINE).toString();		//线下免单金额
		String plq = getDailyPostAccountAmount(AccountCode.ONLINE_PLQ).toString();					//派乐趣
		String plqbt = getDailyPostAccountAmount(AccountCode.ALLOWANCE_PLQ).toString();				//派乐趣补贴
		String bdnm = getDailyPostAccountAmount(AccountCode.ONLINE_BDNM).toString();				//百度糯米团购
		String bdnmRemark = getTicketStatistic(queryDate,Vendor.BDNM);								//百度糯米代金券使用信息
		String bdnmddf = getDailyPostAccountAmount(AccountCode.DDF_BDNM).toString();				//百度糯米到店付
		
		String totalAmount = getTotalDayAmount(queryDate).toString();								//当日营业额总收入
		
		
		
		Long eleOrderNum = getValidCount(queryDate, Vendor.ELE);									//饿了么有效订单数量
		Long mtwmOrderNum = getValidCount(queryDate,Vendor.MTWM);									//美团外卖有效订单数量
		Long bdwmOrderNum = getValidCount(queryDate,Vendor.BDWM);									//百度外卖有效订单数量
		Long plqOrderNum = getValidCount(queryDate,Vendor.PLQ);										//派乐趣有效订单数量
		
		getCashValue().setText(cashmachine);
		getPosValue().setText(pos);
		getMtValue().setText(mt);
		getTgValue().setText(dptg);
		getShValue().setText(sh);
		getEleValue().setText(ele);
		getEleRemark().setText(eleOrderNum);
		getEleRemark().displayToolTips(true);
		getEleFreeValue().setText(ele_allowance);
		getEleSdRemark().setText(elesdremark);
		getTddValue().setText(tdd);
		getAlipayValue().setText(alipay);
		getMtSuperValue().setText(mtsuper);
		getMtwmValue().setText(mtwm);
		getMtwmFreeValue().setText(mtwm_allowance);
		getMtwmRemark().setText(mtwmOrderNum);
		getMtwmRemark().displayToolTips(true);
		getFreeValue().setText(offlineFree);
		getTotalValue().setText(totalAmount);
		getTgRemark().setText(dptgRemark);
		getTgRemark().setToolTipText(dptgRemark);
		getMtRemark().setText(mttgRemark);
		getMtRemark().setToolTipText(mttgRemark);
//		getWmcrValue().setText(wmcr);
//		getWmcrbtValue().setText(freewmcr);
//		getWmcrRemark().setText(wmcrOrderNum);
//		getWmcrRemark().displayToolTips(true);
		getBdwmValue().setText(bdwm);
		getBdwmbtValue().setText(bdwm_allowance);
		getBdwmRemark().setText(bdwmOrderNum);
		getBdwmRemark().displayToolTips(true);
		getPlqValue().setText(plq);
		getPlqbtValue().setText(plqbt);
		getPlqRemark().setText(plqOrderNum);
		getPlqRemark().displayToolTips(true);
		getBdnmValue().setText(bdnm);
		getBdnmRemark().setText(bdnmRemark);
		getBdnmRemark().setToolTipText(bdnmRemark);
		getBdnmddfValue().setText(bdnmddf);
	}
	
	/**
	 * 
	 * Describle(描述)： 统计不同账户今日的入账总额
	 *
	 * 方法名称：getTotalAmount
	 *
	 * 所在类名：OrderDataLoader
	 *
	 * Create Time:2015年8月14日 下午3:14:55
	 *  
	 * @param accountNo
	 * @return
	 */
	public BigDecimal getTotalAmount(String accountNo){
//		return sumMap.get(accountNo) == null? BigDecimal.ZERO:sumMap.get(accountNo);
		return null;
	}
	
	public BigDecimal getDailyPostAccountAmount(AccountCode code){
		return dailyPostAccountMap.get(code) == null?BigDecimal.ZERO:dailyPostAccountMap.get(code);
	}
	
	
	/**
	 * 
	 * Describle(描述)：获取饿了么平台有效单数
	 *
	 * 方法名称：getValidCount
	 *
	 * 所在类名：OrderDataLoader
	 *
	 * Create Time:2015年8月14日 下午3:12:52
	 *  
	 * @param postTime
	 * @param vendor
	 * @return
	 */
	public Long getValidCount(Date postTime,Vendor vendor){
		IOrderAccountRefService oaService = (IOrderAccountRefService) SpringUtils.getBean("OrderAccountRefService");
		String accoutNo = "";
		if(Vendor.MTWM.equals(vendor)){
			accoutNo = AccountCode.ONLINE_MTWM.name();
		}
		if(Vendor.ELE.equals(vendor)){
			accoutNo = AccountCode.ONLINE_ELE.name();
		}
		if(Vendor.BDWM.equals(vendor)){
			accoutNo = AccountCode.ONLINE_BDWM.name();
		}
		if(Vendor.PLQ.equals(vendor)){
			accoutNo = AccountCode.ONLINE_PLQ.name();
		}
		return oaService.getValidOrderCount(postTime, accoutNo);
	}
	
	/**
	 * 
	 * Describle(描述)：统计一天的收入总额
	 *
	 * 方法名称：getTotalDayAmount
	 *
	 * 所在类名：ConculsionPanel
	 *
	 * Create Time:2015年8月14日 下午3:28:54
	 *  
	 * @param time
	 * @return
	 */
	public BigDecimal getTotalDayAmount(Date date){
		BigDecimal totalAmount = new BigDecimal(BigInteger.ZERO,2);
		IOrderService orderService = (IOrderService) SpringUtils.getBean("OrderService");
		String time = DateUtil.date2Str(date, "yyyyMMdd");
		List<OrderVO> orders = orderService.accquireOrderVOsByDay(time);
		if(CollectionUtils.isEmpty(orders)){
			return totalAmount;
		}
		for(OrderVO order:orders){
			totalAmount = totalAmount.add(order.getTotalAmount());
		}
		BigDecimal allowanceAmount = facade.getSDAllowanceAmount(date);
		if(allowanceAmount != null){
			totalAmount = totalAmount.add(allowanceAmount);
		}
		return totalAmount;
	}
	
	/**
	 * 
	 * Describle(描述)： 统计代金券数量展示
	 *
	 * 方法名称：getTicketStatistic
	 *
	 * 所在类名：QueryListener
	 *
	 * Create Time:2015年6月19日 下午4:38:40
	 *  
	 * @param vendor
	 * @return
	 */
	public String getTicketStatistic(Date time,Vendor vendor) {
		return facade.getTicketStatistic(time, vendor);
	}
	
	/**
	 * 
	 * Describle(描述)： 获取饿了么刷单补贴金额
	 *
	 * 方法名称：getELESDAllowanceAmount
	 *
	 * 所在类名：QueryListener
	 *
	 * Create Time:2015年7月30日 下午1:58:46
	 *  
	 * @param date
	 * @return
	 */
	public BigDecimal getELESDAllowanceAmount(Date date){
		return facade.getSDAllowanceAmount(date);
	}
	
	public JLabel getCashValue() {
		return cashValue;
	}
	public void setCashValue(JLabel cashValue) {
		this.cashValue = cashValue;
	}
	public JLabel getPosValue() {
		return posValue;
	}
	public void setPosValue(JLabel posValue) {
		this.posValue = posValue;
	}
	public JLabel getMtValue() {
		return mtValue;
	}
	public void setMtValue(JLabel mtValue) {
		this.mtValue = mtValue;
	}
	public JLabel getTgValue() {
		return tgValue;
	}
	public void setTgValue(JLabel tgValue) {
		this.tgValue = tgValue;
	}
	public JLabel getLsValue() {
		return lsValue;
	}
	public void setLsValue(JLabel lsValue) {
		this.lsValue = lsValue;
	}
	public JLabel getTgRemark() {
		return tgRemark;
	}
	public void setTgRemark(JLabel tgRemark) {
		this.tgRemark = tgRemark;
	}
	public JLabel getMtRemark() {
		return mtRemark;
	}
	public void setMtRemark(JLabel mtRemark) {
		this.mtRemark = mtRemark;
	}
	public JLabel getShValue() {
		return shValue;
	}
	public void setShValue(JLabel shValue) {
		this.shValue = shValue;
	}
	public JLabel getEleFreeValue() {
		return eleFreeValue;
	}
	public void setEleFreeValue(JLabel eleFreeValue) {
		this.eleFreeValue = eleFreeValue;
	}
	public JLabel getEleValue() {
		return eleValue;
	}
	public void setEleValue(JLabel eleValue) {
		this.eleValue = eleValue;
	}
	public JLabel getTddValue() {
		return tddValue;
	}
	public void setTddValue(JLabel tddValue) {
		this.tddValue = tddValue;
	}
	public JLabel getMtwmValue() {
		return mtwmValue;
	}
	public void setMtwmValue(JLabel mtwmValue) {
		this.mtwmValue = mtwmValue;
	}
	public JLabel getMtwmFreeValue() {
		return mtwmFreeValue;
	}
	public void setMtwmFreeValue(JLabel mtwmFreeValue) {
		this.mtwmFreeValue = mtwmFreeValue;
	}
	public JLabel getMtSuperValue() {
		return mtSuperValue;
	}
	public void setMtSuperValue(JLabel mtSuperValue) {
		this.mtSuperValue = mtSuperValue;
	}
	public JLabel getMtSuperFreeValue() {
		return mtSuperFreeValue;
	}
	public void setMtSuperFreeValue(JLabel mtSuperFreeValue) {
		this.mtSuperFreeValue = mtSuperFreeValue;
	}
	public JLabel getFreeValue() {
		return freeValue;
	}
	public void setFreeValue(JLabel freeValue) {
		this.freeValue = freeValue;
	}
	public JLabel getTotalValue() {
		return totalValue;
	}
	public void setTotalValue(JLabel totalValue) {
		this.totalValue = totalValue;
	}

	public DisplayLabel<String, Long> getEleRemark() {
		return eleRemark;
	}

	public void setEleRemark(DisplayLabel<String, Long> eleRemark) {
		this.eleRemark = eleRemark;
	}

	public DisplayLabel<String, BigDecimal> getEleSdRemark() {
		return eleSdRemark;
	}

	public void setEleSdRemark(DisplayLabel<String, BigDecimal> eleSdRemark) {
		this.eleSdRemark = eleSdRemark;
	}


	public Map<AccountCode, BigDecimal> getDailyPostAccountMap() {
		return dailyPostAccountMap;
	}

	public void setDailyPostAccountMap(
			Map<AccountCode, BigDecimal> dailyPostAccountMap) {
		this.dailyPostAccountMap = dailyPostAccountMap;
	}

	public Date getQueryDate() {
		return queryDate;
	}

	public void setQueryDate(Date queryDate) {
		this.queryDate = queryDate;
	}

	public JLabel getWmcrValue() {
		return wmcrValue;
	}

	public void setWmcrValue(JLabel wmcrValue) {
		this.wmcrValue = wmcrValue;
	}

	public JLabel getWmcrbtValue() {
		return wmcrbtValue;
	}

	public void setWmcrbtValue(JLabel wmcrbtValue) {
		this.wmcrbtValue = wmcrbtValue;
	}

	public DisplayLabel<String, Long> getMtwmRemark() {
		return mtwmRemark;
	}

	public void setMtwmRemark(DisplayLabel<String, Long> mtwmRemark) {
		this.mtwmRemark = mtwmRemark;
	}

	public DisplayLabel<String, Long> getWmcrRemark() {
		return wmcrRemark;
	}

	public DisplayLabel<String, Long> getPlqRemark() {
		return plqRemark;
	}

	public void setWmcrRemark(DisplayLabel<String, Long> wmcrRemark) {
		this.wmcrRemark = wmcrRemark;
	}

	public void setPlqRemark(DisplayLabel<String, Long> plqRemark) {
		this.plqRemark = plqRemark;
	}

	public JLabel getPlqValue() {
		return plqValue;
	}

	public JLabel getPlqbtValue() {
		return plqbtValue;
	}

	public void setPlqValue(JLabel plqValue) {
		this.plqValue = plqValue;
	}

	public void setPlqbtValue(JLabel plqbtValue) {
		this.plqbtValue = plqbtValue;
	}

	public JLabel getBdnmValue() {
		return bdnmValue;
	}

	public JLabel getBdnmRemark() {
		return bdnmRemark;
	}

	public void setBdnmValue(JLabel bdnmValue) {
		this.bdnmValue = bdnmValue;
	}

	public void setBdnmRemark(JLabel bdnmRemark) {
		this.bdnmRemark = bdnmRemark;
	}

	public JLabel getBdwmValue() {
		return bdwmValue;
	}

	public JLabel getBdwmbtValue() {
		return bdwmbtValue;
	}

	public DisplayLabel<String, Long> getBdwmRemark() {
		return bdwmRemark;
	}

	public void setBdwmValue(JLabel bdwmValue) {
		this.bdwmValue = bdwmValue;
	}

	public void setBdwmbtValue(JLabel bdwmbtValue) {
		this.bdwmbtValue = bdwmbtValue;
	}

	public void setBdwmRemark(DisplayLabel<String, Long> bdwmRemark) {
		this.bdwmRemark = bdwmRemark;
	}

	public JLabel getAlipayValue() {
		return alipayValue;
	}

	public void setAlipayValue(JLabel alipayValue) {
		this.alipayValue = alipayValue;
	}

	public JLabel getBdnmddfValue() {
		return bdnmddfValue;
	}

	public void setBdnmddfValue(JLabel bdnmddfValue) {
		this.bdnmddfValue = bdnmddfValue;
	}

}
