package com.rci.ui.swing.views;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class QueryFormPanel extends JPanel {

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
		// 饿了么刷单金额设置
		JLabel eleOnlinePayLabel = new JLabel("饿了么刷单在线支付总金额");
		eleOnlinePayAmount = new JTextField(5);
		JLabel eleOrderCountLabel = new JLabel("饿了么刷单数量");
		eleOrderCount = new JTextField(5);
		JLabel elePerAllowanceLabel = new JLabel("每单优惠金额");
		elePerAllowanceAmount = new JTextField(5);

		JLabel rciTime = new JLabel("日期");
		timeInput = new JTextField(10);
		queryBtn = new JButton("查询");
		cleanBtn = new JButton("清空");
		this.add(eleOnlinePayLabel);
		this.add(eleOnlinePayAmount);
		this.add(eleOrderCountLabel);
		this.add(eleOrderCount);
		this.add(elePerAllowanceLabel);
		this.add(elePerAllowanceAmount);

		this.add(rciTime);
		this.add(timeInput);
		this.add(queryBtn);
		this.add(cleanBtn);
		this.setVisible(true);
		this.setSize(500, 300);
		this.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
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
}
