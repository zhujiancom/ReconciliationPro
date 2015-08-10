package com.rci.ui.swing.views;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.math.BigDecimal;
import java.text.ParseException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.rci.enums.BusinessEnums.AccountCode;
import com.rci.enums.BusinessEnums.Vendor;
import com.rci.tools.DateUtil;
import com.rci.ui.swing.listeners.QueryListener;

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
	
	public ConculsionPanel(){
		buildPane();
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
		sixthGroup.add(tddPanel);
		sixthGroup.add(freePanel);
//		sevenGroup.add(expRatePanel);
//		sevenGroup.add(Box.createHorizontalStrut(90));
		sevenGroup.add(totalPanel);
		
		this.add(firstGroup);
		this.add(secondGroup);
		this.add(thirdGroup);
		this.add(forthGroup);
		this.add(fifthGroup);
		this.add(sixthGroup);
		this.add(sevenGroup);
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
//		mtwmValue.setText("");
//		mtwmFreeValue.setText("");
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
	
	public void updateUI(QueryListener listener) throws ParseException {
		String time = listener.getTime();
		getCashValue().setText(listener.getTotalAmount(AccountCode.CASH_MACHINE).toString());
		getPosValue().setText(listener.getTotalAmount(AccountCode.POS).toString());
		getMtValue().setText(listener.getTotalAmount(AccountCode.MT).toString());
		getTgValue().setText(listener.getTotalAmount(AccountCode.DPTG).toString());
		getShValue().setText(listener.getTotalAmount(AccountCode.DPSH).toString());
		getEleValue().setText(listener.getTotalAmount(AccountCode.ELE).toString());
		getEleFreeValue().setText(listener.getTotalAmount(AccountCode.FREE_ELE).toString());
		getEleSdRemark().setText(listener.getELESDAllowanceAmount(DateUtil.parseDate(time, "yyyyMMdd")));
		getTddValue().setText(listener.getTotalAmount(AccountCode.TDD).toString());
		getMtSuperValue().setText(listener.getTotalAmount(AccountCode.MT_SUPER).toString());
		getFreeValue().setText(listener.getTotalAmount(AccountCode.FREE).toString());
		getTotalValue().setText(listener.getTotalDayAmount(time).toString());
		getTgRemark().setText(listener.getTicketStatistic(DateUtil.parseDate(time, "yyyyMMdd"),Vendor.DZDP));
		getMtRemark().setText(listener.getTicketStatistic(DateUtil.parseDate(time, "yyyyMMdd"),Vendor.MT));
		getEleRemark().setText(listener.getValidCount(time, Vendor.ELE));
//		getExpRateValue().setText(listener.getExpressRateStatistic(time));
//		mtwmValue.setText(getTotalAmount(BusinessConstant.MTWM_ACC).toString());
//		mtwmFreeValue.setText(getTotalAmount(BusinessConstant.FREE_MTWM_ACC).toString());
//		lsValue.setText(getTotalAmount(BusinessConstant.LS_ACC).toString());		
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

}
