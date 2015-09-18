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
	private JLabel mtwmValue;
	private JLabel mtwmFreeValue;
	private JLabel mtSuperValue;
	private JLabel mtSuperFreeValue;
	private JLabel freeValue;
	private JLabel totalValue;
//	private JLabel expRateValue; //外送率
	private Map<AccountCode,BigDecimal> sumMap;
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
		
		/* 淘点点统计  */
		JLabel tdd = new JLabel("支付宝入账总额：");
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
		/* 总免单金额统计  */
		JLabel freeLabel = new JLabel("总免单金额：");
		freeValue = new JLabel();
		freeValue.setForeground(Color.GREEN);
		JPanel freePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		freePanel.add(freeLabel);
		freePanel.add(freeValue);
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
		sevenGroup.add(tddPanel);
		sevenGroup.add(freePanel);
		eightGroup.add(totalPanel);
		
		this.add(firstGroup);
		this.add(secondGroup);
		this.add(thirdGroup);
		this.add(forthGroup);
		this.add(fifthGroup);
		this.add(sixthGroup);
		this.add(sevenGroup);
		this.add(eightGroup);
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
//		expRateValue.setText("");
	}
	
	public void refreshUI() {
		getCashValue().setText(getTotalAmount(AccountCode.CASH_MACHINE).toString());
		getPosValue().setText(getTotalAmount(AccountCode.POS).toString());
		getMtValue().setText(getTotalAmount(AccountCode.MT).toString());
		getTgValue().setText(getTotalAmount(AccountCode.DPTG).toString());
		getShValue().setText(getTotalAmount(AccountCode.DPSH).toString());
		getEleValue().setText(getTotalAmount(AccountCode.ELE).toString());
		getEleFreeValue().setText(getTotalAmount(AccountCode.FREE_ELE).toString());
		getEleSdRemark().setText(getELESDAllowanceAmount(queryDate));
		getTddValue().setText(getTotalAmount(AccountCode.ALIPAY).toString());
		getMtSuperValue().setText(getTotalAmount(AccountCode.MT_SUPER).toString());
		getMtwmValue().setText(getTotalAmount(AccountCode.MTWM).toString());
		getMtwmFreeValue().setText(getTotalAmount(AccountCode.FREE_MTWM).toString());
		getFreeValue().setText(getTotalAmount(AccountCode.FREE).toString());
		getTotalValue().setText(getTotalDayAmount(queryDate).toString());
		getTgRemark().setText(getTicketStatistic(queryDate,Vendor.DZDP));
		getMtRemark().setText(getTicketStatistic(queryDate,Vendor.MT));
		getEleRemark().setText(getValidCount(queryDate, Vendor.ELE));
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
	public BigDecimal getTotalAmount(AccountCode accountNo){
		return sumMap.get(accountNo) == null? BigDecimal.ZERO:sumMap.get(accountNo);
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
		return oaService.getValidOrderCount(postTime, AccountCode.valueOf(vendor.name()));
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

	public Map<AccountCode, BigDecimal> getSumMap() {
		return sumMap;
	}

	public void setSumMap(Map<AccountCode, BigDecimal> sumMap) {
		this.sumMap = sumMap;
	}

	public Date getQueryDate() {
		return queryDate;
	}

	public void setQueryDate(Date queryDate) {
		this.queryDate = queryDate;
	}

}
