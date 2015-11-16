package com.rci.ui.swing.views.component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.rci.ui.swing.model.ButtonFactory;
import com.rci.ui.swing.views.PopWindow;

public class DishSelectWin extends PopWindow {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2248904392389671255L;
	
	private SelectedDishPanel parentPanel;
	
	public DishSelectWin(int width,int height,String title){
		super(width,height,title);
		createContentPane();
	}

	private void createContentPane() {
		JPanel containerPanel = this.getContainerPanel();
		containerPanel.add(buildSeriesPane(),BorderLayout.NORTH);
		containerPanel.add(buildDishPane(),BorderLayout.CENTER);
	}
	
	private JPanel buildSeriesPane(){
		JPanel seriesPane = new JPanel();
		GridBagLayout gblayout = new GridBagLayout();
		seriesPane.setLayout(gblayout);
		
		JButton leftBtn = ButtonFactory.createImageButton("skin/gray/images/64x64/leftBtn.png", null);
		GridBagConstraints gb1 = new GridBagConstraints();
		gb1.insets = new Insets(0,0,5,0);//设置控件的空白 , 上、左、下、右
		gb1.fill = GridBagConstraints.HORIZONTAL;// 设置填充方式
		gb1.weightx=10.0;// 第一列的分布方式为10%
		gb1.gridx=0; //起始点为第1列
		gb1.gridy=0;	//起始点为第1行
		seriesPane.add(leftBtn,gb1);
		
		JPanel seriesTypePane = new JPanel();
		seriesTypePane.setBackground(Color.WHITE);
		JButton btn1 = ButtonFactory.createImageButton("无骨系列","skin/gray/images/96x96/btn_1.png", null);
		btn1.setHorizontalTextPosition(SwingConstants.CENTER);
		JButton btn2 = ButtonFactory.createImageButton("带骨系列","skin/gray/images/96x96/btn_2.png", null);
		btn2.setHorizontalTextPosition(SwingConstants.CENTER);
		JButton btn3 = ButtonFactory.createImageButton("碳烤系列","skin/gray/images/96x96/btn_3.png", null);
		btn3.setHorizontalTextPosition(SwingConstants.CENTER);
		JButton btn4 = ButtonFactory.createImageButton("全鸡系列","skin/gray/images/96x96/btn_4.png", null);
		btn4.setHorizontalTextPosition(SwingConstants.CENTER);
		JButton btn5 = ButtonFactory.createImageButton("韩式火锅","skin/gray/images/96x96/btn_5.png", null);
		btn5.setHorizontalTextPosition(SwingConstants.CENTER);
		JButton btn6 = ButtonFactory.createImageButton("配料","skin/gray/images/96x96/btn_6.png", null);
		btn6.setHorizontalTextPosition(SwingConstants.CENTER);
		seriesTypePane.add(btn1);
		seriesTypePane.add(btn2);
		seriesTypePane.add(btn3);
		seriesTypePane.add(btn4);
		seriesTypePane.add(btn5);
		seriesTypePane.add(btn6);
		GridBagConstraints gb2 = new GridBagConstraints();
		gb2.insets = new Insets(0,0,5,0);//设置控件的空白 , 上、左、下、右
		gb2.fill = GridBagConstraints.HORIZONTAL;// 设置填充方式
		gb2.weightx=80.0;// 第二列的分布方式为80%
		gb2.gridx=1; //起始点为第2列
		gb2.gridy=0;	//起始点为第1行
		seriesPane.add(seriesTypePane,gb2);
		
		
		JButton rightBtn = ButtonFactory.createImageButton("skin/gray/images/64x64/rightBtn.png", null);
		GridBagConstraints gb3 = new GridBagConstraints();
		gb3.insets = new Insets(0,5,5,5);//设置控件的空白 , 上、左、下、右
		gb3.fill = GridBagConstraints.HORIZONTAL;// 设置填充方式
		gb3.weightx=10.0;// 第二列的分布方式为80%
		gb3.gridx=3; //起始点为第3列
		gb3.gridy=0;	//起始点为第1行
		seriesPane.add(rightBtn,gb3);
		
		return seriesPane;
	}
	
	private JPanel buildDishPane(){
		JPanel dishPane = new JPanel();
		GridBagLayout gblayout = new GridBagLayout();
		dishPane.setLayout(gblayout);
		
		JPanel leftPane = new JPanel(new FlowLayout(FlowLayout.LEFT));
		leftPane.setBackground(Color.WHITE);
		leftPane.setPreferredSize(dishPane.getMaximumSize());
		JCheckBox c1 = new JCheckBox("原味无骨大");
		JCheckBox c2 = new JCheckBox("原味无骨小");
		leftPane.add(c1);
		leftPane.add(c2);
		GridBagConstraints gb1 = new GridBagConstraints();
		gb1.insets = new Insets(0,0,5,5);//设置控件的空白 , 上、左、下、右
		gb1.fill = GridBagConstraints.HORIZONTAL;// 设置填充方式
		gb1.weightx=90.0;// 第二列的分布方式为80%
		gb1.gridx=0; //起始点为第3列
		gb1.gridy=0;	//起始点为第1行
		dishPane.add(leftPane,gb1);
		
		JPanel rightPane = new JPanel();
		BoxLayout rightLayout = new BoxLayout(rightPane, BoxLayout.Y_AXIS);
		rightPane.setLayout(rightLayout);
		JButton btn1 = ButtonFactory.createImageButton("原味系列","skin/gray/images/64x64/btn_1.png", null);
		btn1.setHorizontalTextPosition(SwingConstants.CENTER);
		JButton btn2 = ButtonFactory.createImageButton("香辣系列","skin/gray/images/64x64/btn_1.png", null);
		btn2.setHorizontalTextPosition(SwingConstants.CENTER);
		JButton btn3 = ButtonFactory.createImageButton("甜辣系列","skin/gray/images/64x64/btn_1.png", null);
		btn3.setHorizontalTextPosition(SwingConstants.CENTER);
		JButton btn4 = ButtonFactory.createImageButton("照烧系列","skin/gray/images/64x64/btn_1.png", null);
		btn4.setHorizontalTextPosition(SwingConstants.CENTER);
		JButton btn5 = ButtonFactory.createImageButton("蒜香系列","skin/gray/images/64x64/btn_1.png", null);
		btn5.setHorizontalTextPosition(SwingConstants.CENTER);
		JButton btn6 = ButtonFactory.createImageButton("BT辣系列","skin/gray/images/64x64/btn_1.png", null);
		btn6.setHorizontalTextPosition(SwingConstants.CENTER);
		JButton btn7 = ButtonFactory.createImageButton("黑蒜酱系列","skin/gray/images/64x64/btn_1.png", null);
		btn7.setHorizontalTextPosition(SwingConstants.CENTER);
		rightPane.add(btn1);
		rightPane.add(btn2);
		rightPane.add(btn3);
		rightPane.add(btn4);
		rightPane.add(btn5);
		rightPane.add(btn6);
		rightPane.add(btn7);
		
		GridBagConstraints gb2 = new GridBagConstraints();
		gb2.insets = new Insets(0,0,5,5);//设置控件的空白 , 上、左、下、右
		gb2.fill = GridBagConstraints.HORIZONTAL;// 设置填充方式
		gb2.weightx=10.0;// 第二列的分布方式为20%
		gb2.gridx=2; //起始点为第2列
		gb2.gridy=0;	//起始点为第1行
		dishPane.add(rightPane,gb2);
		return dishPane;
	}

	public SelectedDishPanel getParentPanel() {
		return parentPanel;
	}

	public void setParentPanel(SelectedDishPanel parentPanel) {
		this.parentPanel = parentPanel;
	}

}
