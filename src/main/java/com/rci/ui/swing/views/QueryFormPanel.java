package com.rci.ui.swing.views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jdesktop.swingx.JXDatePicker;

import com.rci.enums.BusinessEnums.PaymodeCode;
import com.rci.ui.swing.model.ButtonFactory;

public class QueryFormPanel extends JPanel implements ItemListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8989608740654927169L;

	/**
	 * 饿了么刷单参数设置
	 */
	private JTextField eleOnlinePayAmount; // 饿了么刷单在线支付总金额
	private JTextField eleOrderCount; // 饿了么刷单数量
	private JTextField elePerAllowanceAmount; // 每单补贴金额

	private JButton queryBtn;
	private JButton cleanBtn;
	private JButton filterBtn;
//	private DateTextField timeInput;
	private JXDatePicker datepicker;
	
	private JLabel actionLabel;
	private JLabel warningLabel;
	private boolean warningShowing;
	
	private JCheckBox allCheck;
	private JCheckBox eleCheck;
	private JCheckBox mtwmCheck;
//	private JCheckBox dptgCheck;
	private JCheckBox dpshCheck;
	private JCheckBox mttgCheck;
	private JCheckBox mtsuperCheck;
	private JCheckBox cashCheck;
	private JCheckBox posCheck;
	private JCheckBox tddCheck;
	private JCheckBox alipayCheck;
	private JCheckBox bdnmCheck;
	private JCheckBox bdwmCheck;
	private Set<PaymodeCode> paymodes = new HashSet<PaymodeCode>();
	private Icon loadingIcon;
	private Icon doneIcon;
	private Icon warningIcon;
	private Icon errorIcon;
	
	public QueryFormPanel() {
		buildPane();
		URL loadingIconUrl = this.getClass().getClassLoader().getResource("skin/gray/images/24x24/loading.gif");
		loadingIcon = new ImageIcon(loadingIconUrl);
		URL doneIconUrl = this.getClass().getClassLoader().getResource("skin/gray/images/24x24/done.png");
		doneIcon = new ImageIcon(doneIconUrl);
		URL errorIconUrl = this.getClass().getClassLoader().getResource("skin/gray/images/24x24/warning.png");
		errorIcon = new ImageIcon(errorIconUrl);
		URL warningIconUrl = this.getClass().getClassLoader().getResource("skin/gray/images/16x16/warning.png");
		warningIcon = new ImageIcon(warningIconUrl);
	}

	/**
	 * 
	 * Describle(描述)：创建查询面板
	 *
	 * 方法名称：buildPane
	 *
	 * 所在类名：QueryFormPanel
	 *
	 * Create Time:2015年7月29日 上午9:47:01
	 *
	 */
	public void buildPane() {
		setBackground(Color.WHITE);
		this.setLayout(new GridLayout(0, 1));
		JPanel queryPane = new JPanel();
		queryPane.setBackground(Color.WHITE);
		// 饿了么刷单金额设置
//		JLabel eleOnlinePayLabel = new JLabel("饿了么刷单在线支付总金额");
//		eleOnlinePayAmount = new JTextField(5);
//		JLabel eleOrderCountLabel = new JLabel("饿了么刷单数量");
//		eleOrderCount = new JTextField(5);
//		JLabel elePerAllowanceLabel = new JLabel("每单优惠金额");
//		elePerAllowanceAmount = new JTextField(5);
		
		
//		timeInput = new DateTextField("查询日期");
		datepicker = new JXDatePicker(new Date());
		Calendar calendar = datepicker.getMonthView().getCalendar();
		calendar.add(Calendar.YEAR,1);
		datepicker.getMonthView().setUpperBound(calendar.getTime());
		datepicker.setPreferredSize(new Dimension(150,25));
		datepicker.setFormats("yyyyMMdd");
		
		queryBtn = ButtonFactory.createImageButton("查询","skin/gray/images/24x24/search.png", null);
		cleanBtn = ButtonFactory.createImageButton("清空","skin/gray/images/24x24/empty_trash.png", null);
		filterBtn = ButtonFactory.createImageButton("过滤","skin/gray/images/24x24/filter.png", null);
//		queryPane.add(eleOnlinePayLabel);
//		queryPane.add(eleOnlinePayAmount);
//		queryPane.add(eleOrderCountLabel);
//		queryPane.add(eleOrderCount);
//		queryPane.add(elePerAllowanceLabel);
//		queryPane.add(elePerAllowanceAmount);
		JPanel actionPane = new JPanel(new FlowLayout(FlowLayout.LEFT));
		actionPane.setBackground(Color.WHITE);
		actionPane.setPreferredSize(new Dimension(300,30));
		actionLabel = new JLabel();
//		font-family: "Arial Rounded MT Bold","Helvetica Rounded",Arial,sans-serif
//		font-family: "Courier New",Courier,"Lucida Sans Typewriter","Lucida Typewriter",monospace
//		Font[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
//		for(int i=0;i<fonts.length;i++){
//			System.out.println(fonts[i]);
//		}
		actionLabel.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		actionLabel.setForeground(Color.RED);
		actionPane.add(actionLabel);
		
		JPanel inventoryWarningPane = new JPanel();
		inventoryWarningPane.setBackground(Color.WHITE);
		warningLabel = new JLabel();
		warningLabel.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		warningLabel.setForeground(Color.RED);
		inventoryWarningPane.add(warningLabel);
		
//		queryPane.add(timeInput);
		queryPane.add(datepicker);
		queryPane.add(queryBtn);
		queryPane.add(cleanBtn);
		queryPane.add(filterBtn);
		queryPane.add(actionPane);
		queryPane.add(inventoryWarningPane);
		
		this.add(queryPane);
		this.add(createCheckPane()); //添加复选框
		this.setVisible(true);
		this.setSize(500, 300);
	}
	
	public JPanel createCheckPane(){
		JPanel checkPane = new JPanel();
		checkPane.setBackground(Color.WHITE);
		allCheck = new JCheckBox("全部");
		allCheck.setBackground(Color.WHITE);
		allCheck.setSelected(true);
		allCheck.addItemListener(this);
		
		cashCheck = new JCheckBox("现金支付");
		cashCheck.addItemListener(this);
		cashCheck.setBackground(Color.WHITE);
		
		eleCheck = new JCheckBox("饿了么");
		eleCheck.addItemListener(this);
		eleCheck.setBackground(Color.WHITE);
		
//		dptgCheck = new JCheckBox("点评团购");
//		dptgCheck.addItemListener(this);
//		dptgCheck.setBackground(Color.WHITE);
//		
		dpshCheck = new JCheckBox("点评闪惠");
		dpshCheck.addItemListener(this);
		dpshCheck.setBackground(Color.WHITE);
		
		mttgCheck = new JCheckBox("美团团购");
		mttgCheck.addItemListener(this);
		mttgCheck.setBackground(Color.WHITE);
		
		mtsuperCheck = new JCheckBox("美团超券");
		mtsuperCheck.addItemListener(this);
		mtsuperCheck.setBackground(Color.WHITE);
		
		mtwmCheck = new JCheckBox("美团外卖");
		mtwmCheck.addItemListener(this);
		mtwmCheck.setBackground(Color.WHITE);
		
		tddCheck = new JCheckBox("淘点点");
		tddCheck.addItemListener(this);
		tddCheck.setBackground(Color.WHITE);
		
		alipayCheck = new JCheckBox("支付宝");
		alipayCheck.addItemListener(this);
		alipayCheck.setBackground(Color.WHITE);
		
		posCheck = new JCheckBox("银联支付");
		posCheck.addItemListener(this);
		posCheck.setBackground(Color.WHITE);
		
		bdnmCheck = new JCheckBox("百度糯米");
		bdnmCheck.addItemListener(this);
		bdnmCheck.setBackground(Color.WHITE);
		
		bdwmCheck = new JCheckBox("百度外卖");
		bdwmCheck.addItemListener(this);
		bdwmCheck.setBackground(Color.WHITE);
		
		checkPane.add(allCheck);
		checkPane.add(cashCheck);
		checkPane.add(eleCheck);
//		checkPane.add(dptgCheck);
		checkPane.add(dpshCheck);
		checkPane.add(mttgCheck);
		checkPane.add(mtsuperCheck);
		checkPane.add(mtwmCheck);
		checkPane.add(tddCheck);
		checkPane.add(alipayCheck);
		checkPane.add(posCheck);
		checkPane.add(bdnmCheck);
		checkPane.add(bdwmCheck);
		return checkPane;
	}

	public JTextField getEleOnlinePayAmount() {
		return eleOnlinePayAmount;
	}

	public void setEleOnlinePayAmount(JTextField eleOnlinePayAmount) {
		this.eleOnlinePayAmount = eleOnlinePayAmount;
	}

	public JTextField getEleOrderCount() {
		return eleOrderCount;
	}

	public void setEleOrderCount(JTextField eleOrderCount) {
		this.eleOrderCount = eleOrderCount;
	}

	public JButton getQueryBtn() {
		return queryBtn;
	}

	public void setQueryBtn(JButton queryBtn) {
		this.queryBtn = queryBtn;
	}

	public JButton getCleanBtn() {
		return cleanBtn;
	}

	public void setCleanBtn(JButton cleanBtn) {
		this.cleanBtn = cleanBtn;
	}

//	public DateTextField getTimeInput() {
//		return timeInput;
//	}
//
//	public void setTimeInput(DateTextField timeInput) {
//		this.timeInput = timeInput;
//	}

	public JTextField getElePerAllowanceAmount() {
		return elePerAllowanceAmount;
	}

	public void setElePerAllowanceAmount(JTextField elePerAllowanceAmount) {
		this.elePerAllowanceAmount = elePerAllowanceAmount;
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		Object source = e.getItemSelectable();
		if(paymodes.contains(PaymodeCode.UNKNOW)){
			paymodes.remove(PaymodeCode.UNKNOW);
		}
		if(source == allCheck && e.getStateChange() == ItemEvent.SELECTED){
			allCheck.setSelected(true);
			cashCheck.setSelected(false);
			eleCheck.setSelected(false);
			dpshCheck.setSelected(false);
//			dptgCheck.setSelected(false);
			mttgCheck.setSelected(false);
			mtsuperCheck.setSelected(false);
			mtwmCheck.setSelected(false);
			posCheck.setSelected(false);
			tddCheck.setSelected(false);
			alipayCheck.setSelected(false);
			bdnmCheck.setSelected(false);
			paymodes.clear();
			paymodes.add(PaymodeCode.UNKNOW);
		}
		if(source == cashCheck){
			if(e.getStateChange() == ItemEvent.SELECTED){
				allCheck.setSelected(false);
				paymodes.add(PaymodeCode.CASH_MACHINE);	
			}else{
				paymodes.remove(PaymodeCode.CASH_MACHINE);
			}
		}
		if(source == eleCheck){
			if(e.getStateChange() == ItemEvent.SELECTED){
				allCheck.setSelected(false);
				paymodes.add(PaymodeCode.ELE);	
			}else{
				paymodes.remove(PaymodeCode.ELE);
			}
		}
//		if(source == dptgCheck){
//			if(e.getStateChange() == ItemEvent.SELECTED){
//				allCheck.setSelected(false);
//				paymodes.add(PaymodeCode.DPTG);	
//			}else{
//				paymodes.remove(PaymodeCode.DPTG);
//			}
//		}
		if(source == dpshCheck){
			if(e.getStateChange() == ItemEvent.SELECTED){
				allCheck.setSelected(false);
				paymodes.add(PaymodeCode.DPSH);	
			}else{
				paymodes.remove(PaymodeCode.DPSH);
			}
		}
		if(source == mttgCheck){
			if(e.getStateChange() == ItemEvent.SELECTED){
				allCheck.setSelected(false);
				paymodes.add(PaymodeCode.MT);	
			}else{
				paymodes.remove(PaymodeCode.MT);
			}
		}
		if(source == mtsuperCheck){
			if(e.getStateChange() == ItemEvent.SELECTED){
				allCheck.setSelected(false);
				paymodes.add(PaymodeCode.MTSUPER);	
			}else{
				paymodes.remove(PaymodeCode.MTSUPER);
			}
		}
		if(source == mtwmCheck){
			if(e.getStateChange() == ItemEvent.SELECTED){
				allCheck.setSelected(false);
				paymodes.add(PaymodeCode.MTWM);	
			}else{
				paymodes.remove(PaymodeCode.MTWM);
			}
		}
		if(source == posCheck){
			if(e.getStateChange() == ItemEvent.SELECTED){
				allCheck.setSelected(false);
				paymodes.add(PaymodeCode.POS);	
			}else{
				paymodes.remove(PaymodeCode.POS);
			}
		}
		if(source == tddCheck){
			if(e.getStateChange() == ItemEvent.SELECTED){
				allCheck.setSelected(false);
				paymodes.add(PaymodeCode.TDD);	
			}else{
				paymodes.remove(PaymodeCode.TDD);
			}
		}
		if(source == alipayCheck){
			if(e.getStateChange() == ItemEvent.SELECTED){
				allCheck.setSelected(false);
				paymodes.add(PaymodeCode.ALIPAY);	
			}else{
				paymodes.remove(PaymodeCode.ALIPAY);
			}
		}
		if(source == bdnmCheck){
			if(e.getStateChange() == ItemEvent.SELECTED){
				allCheck.setSelected(false);
				paymodes.add(PaymodeCode.BDNM);	
			}else{
				paymodes.remove(PaymodeCode.BDNM);
			}
		}
		if(source == bdwmCheck){
			if(e.getStateChange() == ItemEvent.SELECTED){
				allCheck.setSelected(false);
				paymodes.add(PaymodeCode.BDWM);	
			}else{
				paymodes.remove(PaymodeCode.BDWM);
			}
		}
	}

	public Set<PaymodeCode> getPaymodes() {
		return paymodes;
	}

	public void setPaymodes(Set<PaymodeCode> paymodes) {
		this.paymodes = paymodes;
	}

	public JLabel getActionLabel() {
		return actionLabel;
	}

	public void setActionLabel(JLabel actionLabel) {
		this.actionLabel = actionLabel;
	}
	
	public void displayInfoLoading(String diplayInfo){
		actionLabel.setText(diplayInfo);
		actionLabel.setIcon(loadingIcon);
	}
	
	public void displayInfoDone(String diplayInfo){
		actionLabel.setText(diplayInfo);
		actionLabel.setIcon(doneIcon);
	}
	
	public void displayErrorInfo(String errorInfo){
		actionLabel.setText(errorInfo);
		actionLabel.setIcon(errorIcon);
	}
	
	public void displayWarningInfo(String warningInfo){
		warningLabel.setText(warningInfo);
		warningLabel.setIcon(warningIcon);
		warningShowing = true;
	}
	
	public void hideInfo(){
		actionLabel.setText(null);
		actionLabel.setIcon(null);
	}
	
	public void removeWarningInfo(){
		warningLabel.setText(null);
		warningLabel.setIcon(null);
		warningShowing = false;
	}

	public boolean isWarningShowing() {
		return warningShowing;
	}

	public void setWarningShowing(boolean warningShowing) {
		this.warningShowing = warningShowing;
	}

	public JButton getFilterBtn() {
		return filterBtn;
	}

	public void setFilterBtn(JButton filterBtn) {
		this.filterBtn = filterBtn;
	}

	public JXDatePicker getDatepicker() {
		return datepicker;
	}

	public void setDatepicker(JXDatePicker datepicker) {
		this.datepicker = datepicker;
	}
}
