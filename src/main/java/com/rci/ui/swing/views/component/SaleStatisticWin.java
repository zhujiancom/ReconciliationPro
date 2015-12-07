package com.rci.ui.swing.views.component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.rci.ui.swing.views.PopWindow;

public class SaleStatisticWin extends PopWindow {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5270961465345282597L;

	public SaleStatisticWin(String title){
		super(title);
		initComponent();
	}

	private void initComponent() {
		JPanel queryBar = createQueryBar();
		JPanel containerPanel = this.getContainerPanel();
		containerPanel.add(queryBar,BorderLayout.NORTH);
	}

	/**
	 * 
	 * Describle(描述)：创建查询工具栏
	 *
	 * 方法名称：createQueryBar
	 *
	 * 所在类名：SaleStatisticWin
	 *
	 * Create Time:2015年12月7日 下午4:38:07
	 *  
	 * @return
	 */
	private JPanel createQueryBar() {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		JLabel time = new JLabel("统计时间：");
		JPanel timeSelectPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,0,0));
		timeSelectPanel.setBackground(Color.WHITE);
		timeSelectPanel.add(new TimeSelectTag("今天", 0,true,TimeSelectTag.Day));
		timeSelectPanel.add(new TimeSelectTag("近7天", 7,TimeSelectTag.Day));
		timeSelectPanel.add(new TimeSelectTag("近1月", 1,TimeSelectTag.MONTH));
		panel.add(time);
		panel.add(timeSelectPanel);
		return panel;
	}
	
	/**
	 * 
	 * remark (备注): 时间选择标签
	 *
	 * @author zj
	 *	
	 * 项目名称：ReconciliationPro
	 *
	 * 类名称：TimeSelectTag
	 *
	 * 包名称：com.rci.ui.swing.views.component
	 *
	 * Create Time: 2015年12月7日 下午4:42:31
	 *
	 */
	private class TimeSelectTag extends JLabel{
		/**
		 * 
		 */
		private static final long serialVersionUID = -3700402653890016213L;
		public static final int YEAR = 1;
		public static final int MONTH = 2;
		public static final int Day = 3;
		private String name;
		
		private int timeRange;
		
		private boolean isSelected;
		
		private int mask; // 时间基数是年，月,或日
		
		private int width;
		
		private int height;
		
		public TimeSelectTag(String name,int timeRange,int mask){
			this(name,timeRange,false,mask);
		}
		
		public TimeSelectTag(String name,int timeRange,boolean isSelected,int mask){
			this(name,timeRange,isSelected,50,20,mask);
		}
		
		public TimeSelectTag(String name,int timeRange,boolean isSelected,int width,int height,int mask){
			super(name);
			this.timeRange = timeRange;
			this.isSelected = isSelected;
			this.width = width;
			this.height = height;
			this.mask = mask;
			initComponent();
		}
		
		private void initComponent(){
			this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			this.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
			this.setPreferredSize(new Dimension(width,height));
			this.setOpaque(true);
			if(isSelected){
				this.setBackground(Color.BLUE);
				this.setForeground(Color.WHITE);
			}else{
				this.setBackground(Color.WHITE);
				this.setForeground(Color.BLACK);
			}
			this.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseClicked(MouseEvent event) {
					if(event.getClickCount() == 1){
						doAction();
					}
				}
			});
		}
		
		public void doAction(){
			isSelected = true;
			this.updateUI();
			this.repaint();
		}

		@Override
		protected void paintComponent(Graphics paramGraphics) {
			super.paintComponent(paramGraphics);
			if(isSelected){
				this.setBackground(Color.BLUE);
				this.setForeground(Color.WHITE);
			}else{
				this.setBackground(Color.WHITE);
				this.setForeground(Color.BLACK);
			}
		}
		
		
	}
}
