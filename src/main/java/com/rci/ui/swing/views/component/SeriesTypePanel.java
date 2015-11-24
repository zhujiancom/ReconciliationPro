package com.rci.ui.swing.views.component;

import java.awt.BorderLayout;
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

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.rci.service.core.IMetadataService;
import com.rci.tools.SpringUtils;
import com.rci.ui.swing.listeners.DishSelectListener;
import com.rci.ui.swing.model.ButtonFactory;
import com.rci.ui.swing.vos.DishSeriesVO;

public class SeriesTypePanel extends JPanel implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5660269571100372333L;
	
	private JPanel containerPane;
	
	private DishSelectListener selectListener;

	private Point currentPoint = new Point(0,0);
	
	private JButton leftBtn;
	
	private JButton rightBtn;
	
	private DisplayDishPanel dishPane;
	
	private IMetadataService metadataService;
	
	private int offset=96;
	
	private JPanel mainPane;
	
	public SeriesTypePanel(JPanel containerPane,DishSelectListener selectListener){
		this.containerPane = containerPane;
		this.selectListener = selectListener;
		metadataService = (IMetadataService) SpringUtils.getBean("MetadataService");
		initComponent();
	}

	private void initComponent() {
		GridBagLayout gblayout = new GridBagLayout();
		this.setLayout(gblayout);
		leftBtn = ButtonFactory.createImageButton("skin/gray/images/64x64/leftBtn.png", null);
		GridBagConstraints gb1 = new GridBagConstraints();
		gb1.insets = new Insets(0,0,5,0);//设置控件的空白 , 上、左、下、右
		gb1.fill = GridBagConstraints.HORIZONTAL;// 设置填充方式
		gb1.weightx=10.0;// 第一列的分布方式为10%
		gb1.gridx=0; //起始点为第1列
		gb1.gridy=0;	//起始点为第1行
		this.add(leftBtn,gb1);
		leftBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent paramActionEvent) {
				prePage();
			}
		});
		
		mainPane = new JPanel();
//		BoxLayout layout = new BoxLayout(mainPane, BoxLayout.X_AXIS);
//		mainPane.setLayout(layout);
		mainPane.setLayout(null);
		mainPane.setPreferredSize(new Dimension(702,100));
		mainPane.setBackground(Color.WHITE);
		List<DishSeriesVO> dishSeries = metadataService.getAllDishSeries();
		int index = 1;
		int x = 0, y=0;
		for(DishSeriesVO dsv:dishSeries){
			JButton btn = ButtonFactory.createImageButton(dsv.getSeriesname(), "skin/gray/images/96x96/btn_"+index+".png",null);
			btn.setName(dsv.getSeriesno());
			btn.setHorizontalTextPosition(SwingConstants.CENTER);
			btn.setBounds(x, y, 96, 96);
			mainPane.add(btn);
			final String seriesno = dsv.getSeriesno();
			if(dishPane == null){
				dishPane = new DisplayDishPanel(seriesno,selectListener);
				containerPane.add(dishPane,BorderLayout.CENTER);
			}
			btn.addActionListener(this);
			index++;
			x += offset;
		}
		GridBagConstraints gb2 = new GridBagConstraints();
		gb2.insets = new Insets(0,0,5,0);//设置控件的空白 , 上、左、下、右
		gb2.fill = GridBagConstraints.HORIZONTAL;// 设置填充方式
		gb2.weightx=80.0;// 第二列的分布方式为80%
		gb2.gridx=1; //起始点为第2列
		gb2.gridy=0;	//起始点为第1行
		this.add(mainPane,gb2);
		
		rightBtn = ButtonFactory.createImageButton("skin/gray/images/64x64/rightBtn.png", null);
		GridBagConstraints gb3 = new GridBagConstraints();
		gb3.insets = new Insets(0,5,5,5);//设置控件的空白 , 上、左、下、右
		gb3.fill = GridBagConstraints.HORIZONTAL;// 设置填充方式
		gb3.weightx=10.0;// 第二列的分布方式为80%
		gb3.gridx=3; //起始点为第3列
		gb3.gridy=0;	//起始点为第1行
		this.add(rightBtn,gb3);
		rightBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent paramActionEvent) {
				nextPage();
			}
		});
	}
	
	public void prePage(){
		Component[] seriesTypeBtns = mainPane.getComponents();
		for(int i=0;i<seriesTypeBtns.length;i++){
			JButton btn = (JButton) seriesTypeBtns[i];
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
		Component[] seriesTypeBtns = mainPane.getComponents();
		int totalLength = offset * seriesTypeBtns.length;
		for(int i=0;i<seriesTypeBtns.length;i++){
			JButton btn = (JButton) seriesTypeBtns[i];
			Point curPoint = seriesTypeBtns[i].getLocation();
			if(currentPoint.x < totalLength-offset){
				btn.setLocation(curPoint.x-offset, curPoint.y);
			}
		}
		if(currentPoint.x < totalLength-offset){
			currentPoint.x = currentPoint.x+offset;
		}
	}

	public JPanel getContainerPane() {
		return containerPane;
	}

	public void setContainerPane(JPanel containerPane) {
		this.containerPane = containerPane;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		JButton btn = (JButton) event.getSource();
		containerPane.remove(dishPane);
		dishPane = new DisplayDishPanel(btn.getName(),selectListener);
		containerPane.add(dishPane,BorderLayout.CENTER);
		containerPane.updateUI();
		containerPane.repaint();
//		if(dishPane == null){
//			dishPane = new DisplayDishPanel(btn.getName(),selectListener);
//			containerPane.add(dishPane,BorderLayout.CENTER);
//		}
//		dishPane.setSeriesno(btn.getName());
//		dishPane.repaint();
//		Component[] components = containerPane.getComponents();
//		for(int i=0;i<components.length;i++){
//			System.out.println(components[i]);
//		}
//		dishPane = (DisplayDishPanel) containerPane.getComponent(1);
//		dishPane.setSeriesno(btn.getName());
//		dishPane.refresh();
	}
}
