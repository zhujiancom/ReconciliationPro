package com.rci.ui.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.rci.metadata.dto.HangupTableDTO;
import com.rci.ui.swing.model.ButtonFactory;

public class HangupOrderPanel extends JPanel {
	private JPanel showTableListPanel;
	
	public HangupOrderPanel(){
		initComponent();
	}
	
	private void initComponent(){
		setLayout(new BorderLayout(0, 0));
		add(createTableInfoBar());
	}

	private JPanel createTableInfoBar() {
		JPanel bar = new JPanel();
		bar.setBackground(Color.WHITE);
		GridBagLayout layout = new GridBagLayout();
		bar.setLayout(layout);
		JButton leftBtn = ButtonFactory.createImageButton("skin/gray/images/64x64/arrow-left.png", "skin/gray/images/64x64/arrow-left-pressed.png");
		GridBagConstraints gb1 = new GridBagConstraints();
		gb1.insets = new Insets(0,0,5,0);//设置控件的空白 , 上、左、下、右
		gb1.fill = GridBagConstraints.HORIZONTAL;// 设置填充方式
		gb1.weightx=10.0;// 第一列的分布方式为10%
		gb1.gridx=0; //起始点为第1列
		gb1.gridy=0;	//起始点为第1行
		this.add(leftBtn,gb1);
		
		JPanel showTableListPanel = new JPanel();
		showTableListPanel.setLayout(null);
		showTableListPanel.setPreferredSize(new Dimension(702,100));
		showTableListPanel.setBackground(Color.WHITE);
		List<HangupTableDTO> tables = new ArrayList<HangupTableDTO>();
		
		leftBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent paramActionEvent) {
				prePage();
			}

			private void prePage() {
				
			}
		});
		
		
		return null;
	}
	
	
}
