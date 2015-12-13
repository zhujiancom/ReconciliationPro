package com.rci.ui.swing.views.component.slidebar;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.apache.commons.collections.CollectionUtils;

import com.rci.ui.swing.model.ButtonFactory;

public class SlideBar extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3296023438295974556L;

	private JPanel mainSlidePanel;
	
	private Point currentPoint = new Point(0,0);
	
	private int offset = 0;
	
	private List<SlideElement> elements;
	
	public SlideBar(List<SlideElement> elements){
		this.elements = elements;
		initComponent();
	}
	
	private void initComponent(){
		setBackground(Color.WHITE);
		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);
		//左方向键
		addLeftArrow();
		//滑动面板主面板
		addMainSlide();
		//右方向键
		addRightArrow();
	}
	
	private void addMainSlide() {
		mainSlidePanel = new JPanel();
		mainSlidePanel.setPreferredSize(new Dimension(672,80));
		mainSlidePanel.setBackground(Color.WHITE);
		mainSlidePanel.setLayout(null);
		GridBagConstraints gb = new GridBagConstraints();
		gb.insets = new Insets(0,0,5,0);//设置控件的空白 , 上、左、下、右
		gb.fill = GridBagConstraints.HORIZONTAL;// 设置填充方式
		gb.weightx=80.0;// 第二列的分布方式为80%
		gb.gridx=1; //起始点为第2列
		gb.gridy=0;	//起始点为第1行
		if(!CollectionUtils.isEmpty(elements)){
			offset = elements.get(0).getWidth()+elements.get(0).getHgap();
			int x = 0;
			for(SlideElement element:elements){
				element.setBounds(x, (mainSlidePanel.getPreferredSize().height-element.getHeight())/2, element.getWidth(), element.getHeight());
				mainSlidePanel.add(element);
				x += offset;
			}
			
		}
		add(mainSlidePanel,gb);
	}

	private void addLeftArrow(){
		GridBagConstraints gb = new GridBagConstraints();
		JButton leftArrow = ButtonFactory.createImageButton("skin/gray/images/64x64/arrow-left.png", "skin/gray/images/64x64/arrow-left-pressed.png");
		gb.insets = new Insets(0,0,5,0);//设置控件的空白 , 上、左、下、右
		gb.fill = GridBagConstraints.WEST;// 设置填充方式
		gb.weightx=10.0;// 第一列的分布方式为10%
		gb.gridx=0; //起始点为第1列
		gb.gridy=0;	//起始点为第1行
		
		leftArrow.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent paramActionEvent) {
				prePage();
			}
		});
		add(leftArrow,gb);
	}
	
	private void addRightArrow(){
		GridBagConstraints gb = new GridBagConstraints();
		JButton rightArrow = ButtonFactory.createImageButton("skin/gray/images/64x64/arrow-right.png", "skin/gray/images/64x64/arrow-right-pressed.png");
		gb.insets = new Insets(0,0,5,0);//设置控件的空白 , 上、左、下、右
		gb.fill = GridBagConstraints.EAST;// 设置填充方式
		gb.weightx=10.0;// 第二列的分布方式为80%
		gb.gridx=2; //起始点为第3列
		gb.gridy=0;	//起始点为第1行
		
		rightArrow.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				nextPage();
			}
		});
		add(rightArrow,gb);
	}
	
	public void prePage(){
		Component[] slideElements = mainSlidePanel.getComponents();
		for(int i=0;i<slideElements.length;i++){
			JButton btn = (JButton) slideElements[i];
			Point curPoint = btn.getLocation();
			if(currentPoint.x > 0){
				btn.setLocation(curPoint.x+offset, curPoint.y);
			}
		}
		if(currentPoint.x > 0){
			currentPoint.x = currentPoint.x - offset;
		}
	}
	
	public void nextPage(){
		Component[] slideElements = mainSlidePanel.getComponents();
		int totalLength = offset * slideElements.length;
		for(int i=0;i<slideElements.length;i++){
			JButton btn = (JButton) slideElements[i];
			Point curPoint = slideElements[i].getLocation();
			if(currentPoint.x < totalLength-offset){
				btn.setLocation(curPoint.x-offset, curPoint.y);
			}
		}
		if(currentPoint.x < totalLength-offset){
			currentPoint.x = currentPoint.x+offset;
		}
	}
}
