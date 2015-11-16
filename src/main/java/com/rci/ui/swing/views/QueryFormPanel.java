package com.rci.ui.swing.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.rci.enums.BusinessEnums.PaymodeCode;
import com.rci.ui.swing.model.ButtonFactory;
import com.rci.ui.swing.views.component.DishSelectWin;

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
	private JTextField timeInput;
	
	private JLabel actionLabel;
	
	private JCheckBox allCheck;
	private JCheckBox eleCheck;
	private JCheckBox mtwmCheck;
	private JCheckBox dptgCheck;
	private JCheckBox dpshCheck;
	private JCheckBox mttgCheck;
	private JCheckBox mtsuperCheck;
	private JCheckBox cashCheck;
	private JCheckBox posCheck;
	private JCheckBox tddCheck;
	private JCheckBox alipayCheck;
	private JCheckBox bdnmCheck;
	private Set<PaymodeCode> paymodes = new HashSet<PaymodeCode>();

	public QueryFormPanel() {
		buildPane();
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
		this.setLayout(new GridLayout(0, 1));
		JPanel queryPane = new JPanel();
		// 饿了么刷单金额设置
//		JLabel eleOnlinePayLabel = new JLabel("饿了么刷单在线支付总金额");
//		eleOnlinePayAmount = new JTextField(5);
//		JLabel eleOrderCountLabel = new JLabel("饿了么刷单数量");
//		eleOrderCount = new JTextField(5);
//		JLabel elePerAllowanceLabel = new JLabel("每单优惠金额");
//		elePerAllowanceAmount = new JTextField(5);
		JButton btn = new JButton("选择菜品");
		btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				DishSelectWin win = new DishSelectWin(900,600,"菜品选择");
			}
		});
		queryPane.add(btn, new FlowLayout(FlowLayout.LEFT));
		
		JLabel rciTime = new JLabel("日期");
		timeInput = new JTextField(10);
		queryBtn = ButtonFactory.createImageButton("查询","skin/gray/images/24x24/search.png", null);
		cleanBtn = ButtonFactory.createImageButton("清空","skin/gray/images/24x24/empty_trash.png", null);
//		queryPane.add(eleOnlinePayLabel);
//		queryPane.add(eleOnlinePayAmount);
//		queryPane.add(eleOrderCountLabel);
//		queryPane.add(eleOrderCount);
//		queryPane.add(elePerAllowanceLabel);
//		queryPane.add(elePerAllowanceAmount);
		JPanel actionPane = new JPanel(new FlowLayout(FlowLayout.LEFT));
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
		
		queryPane.add(rciTime);
		queryPane.add(timeInput);
		queryPane.add(queryBtn);
		queryPane.add(cleanBtn);
		queryPane.add(actionPane);
		
		this.add(queryPane);
		this.add(createCheckPane()); //添加复选框
		this.setVisible(true);
		this.setSize(500, 300);
		this.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
	}
	
	public JPanel createCheckPane(){
		JPanel checkPane = new JPanel();
		allCheck = new JCheckBox("全部");
		allCheck.setSelected(true);
		allCheck.addItemListener(this);
		
		cashCheck = new JCheckBox("现金支付");
		cashCheck.addItemListener(this);
		
		eleCheck = new JCheckBox("饿了么");
		eleCheck.addItemListener(this);
		
		dptgCheck = new JCheckBox("点评团购");
		dptgCheck.addItemListener(this);
		
		dpshCheck = new JCheckBox("点评闪惠");
		dpshCheck.addItemListener(this);
		
		mttgCheck = new JCheckBox("美团团购");
		mttgCheck.addItemListener(this);
		
		mtsuperCheck = new JCheckBox("美团超券");
		mtsuperCheck.addItemListener(this);
		
		mtwmCheck = new JCheckBox("美团外卖");
		mtwmCheck.addItemListener(this);
		
		tddCheck = new JCheckBox("淘点点");
		tddCheck.addItemListener(this);
		
		alipayCheck = new JCheckBox("支付宝");
		alipayCheck.addItemListener(this);
		
		posCheck = new JCheckBox("银联支付");
		posCheck.addItemListener(this);
		
		bdnmCheck = new JCheckBox("百度糯米");
		bdnmCheck.addItemListener(this);
		
		checkPane.add(allCheck);
		checkPane.add(cashCheck);
		checkPane.add(eleCheck);
		checkPane.add(dptgCheck);
		checkPane.add(dpshCheck);
		checkPane.add(mttgCheck);
		checkPane.add(mtsuperCheck);
		checkPane.add(mtwmCheck);
		checkPane.add(tddCheck);
		checkPane.add(alipayCheck);
		checkPane.add(posCheck);
		checkPane.add(bdnmCheck);
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

	public JTextField getTimeInput() {
		return timeInput;
	}

	public void setTimeInput(JTextField timeInput) {
		this.timeInput = timeInput;
	}

	public JTextField getElePerAllowanceAmount() {
		return elePerAllowanceAmount;
	}

	public void setElePerAllowanceAmount(JTextField elePerAllowanceAmount) {
		this.elePerAllowanceAmount = elePerAllowanceAmount;
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		Object source = e.getItemSelectable();
		if(source == allCheck && e.getStateChange() == ItemEvent.SELECTED){
			allCheck.setSelected(true);
			cashCheck.setSelected(false);
			eleCheck.setSelected(false);
			dpshCheck.setSelected(false);
			dptgCheck.setSelected(false);
			mttgCheck.setSelected(false);
			mtsuperCheck.setSelected(false);
			mtwmCheck.setSelected(false);
			posCheck.setSelected(false);
			tddCheck.setSelected(false);
			alipayCheck.setSelected(false);
			bdnmCheck.setSelected(false);
			paymodes.clear();
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
		if(source == dptgCheck){
			if(e.getStateChange() == ItemEvent.SELECTED){
				allCheck.setSelected(false);
				paymodes.add(PaymodeCode.DPTG);	
			}else{
				paymodes.remove(PaymodeCode.DPTG);
			}
		}
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
				paymodes.add(PaymodeCode.ZFB);	
			}else{
				paymodes.remove(PaymodeCode.ZFB);
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
}
