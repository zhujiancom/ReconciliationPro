package com.rci.ui.swing.views.builder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicButtonUI;

import com.rci.enums.BusinessEnums.Vendor;
import com.rci.ui.swing.model.SchemeTable;
import com.rci.ui.swing.views.PopWindow;

public class SchemeManagementWinBuilder implements PopWindowBuilder {
	private JPanel contentPane;
	private JScrollPane scrollPane;
	private JTable table;
	
	@Override
	public PopWindow retrieveWindow() {
		PopWindow schemeManagementWindow = new PopWindow("在线平台活动设置");
		createContentPane();
		Container containerPanel = schemeManagementWindow.getContentPane();
		containerPanel.add(contentPane,BorderLayout.CENTER);
		return schemeManagementWindow;
	}

	@Override
	public void createQueryPane() {
		
	}

	@Override
	public void createContentPane() {
		// outside box
		contentPane = new JPanel(null);
		//操作栏
		JPanel operaPane = new JPanel();
		operaPane.setLayout(null);
		operaPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		operaPane.setBackground(Color.WHITE);
		operaPane.setBounds(50, 30, 700, 35);
		contentPane.add(operaPane);
		JButton addBtn = new JButton(new ImageIcon("./src/main/resources/skin/gray/images/24x24/addBtn_1.png"));
		addBtn.setUI(new BasicButtonUI());
		addBtn.setContentAreaFilled(false);
		addBtn.setMargin(new Insets(0,0,0,0));
		addBtn.setBounds(4, 4, 24, 24);
		
		JButton delBtn = new JButton(new ImageIcon("./src/main/resources/skin/gray/images/24x24/delBtn_1.png"));
		delBtn.setUI(new BasicButtonUI());
		delBtn.setContentAreaFilled(false);
		delBtn.setMargin(new Insets(0,0,0,0));
		delBtn.setBounds(40, 4, 24, 24);
		
		JButton editBtn = new JButton(new ImageIcon("./src/main/resources/skin/gray/images/24x24/editBtn_1.png"));
		editBtn.setUI(new BasicButtonUI());
		editBtn.setContentAreaFilled(false);
		editBtn.setMargin(new Insets(0,0,0,0));
		editBtn.setBounds(80, 4, 24, 24);
		
		operaPane.add(addBtn);
		operaPane.add(delBtn);
		operaPane.add(editBtn);
		
		scrollPane = new JScrollPane();
		table = new SchemeTable(10);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setViewportView(table);
		scrollPane.setBounds(50, 65, 700, 400);
		
		contentPane.add(scrollPane);
		
		addBtn.addActionListener(new ActionListener() {
			private JTextField nameInput = new JTextField(30);
			private JComboBox<Vendor> vendorInput = new JComboBox<Vendor>(new Vendor[]{Vendor.ELE,Vendor.DZDP});
			private JTextField priceInput = new JTextField(30);
			private JTextField postPriceInput = new JTextField(30);
			private JTextField spreadInput = new JTextField(30);
			private JTextField startInput = new JTextField(30);
			private JTextField endInput = new JTextField(30);
			private JTextField floorInput = new JTextField(30);
			private JTextField ceilInput = new JTextField(30);
			private JLabel name = new JLabel("活动名称");
			private JLabel vendor = new JLabel("活动平台");
			private JLabel price = new JLabel("优惠金额");
			private JLabel postPrice = new JLabel("平台补贴");
			private JLabel spread = new JLabel("餐厅补贴");
			private JLabel start = new JLabel("开始时间");
			private JLabel end = new JLabel("结束时间");
			private JLabel floor = new JLabel("最低消费金额");
			private JLabel ceil = new JLabel("最高消费金额");
			private JButton confirmBtn = new JButton(new ImageIcon("./src/main/resources/skin/submitBtn.png"));
			
			
			@Override
			public void actionPerformed(ActionEvent e) {
				PopWindow addForm = new PopWindow(350,500);
				JPanel containerPanel = addForm.getContainerPanel();
				JPanel mainPane = new JPanel();
				mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.Y_AXIS));
				JScrollPane sPane = new JScrollPane(mainPane);
				buildMainPanel(mainPane);
				containerPanel.add(sPane,BorderLayout.CENTER);
			}
			
			public void buildMainPanel(JPanel mainPane){
				JPanel firstPane = new JPanel(new FlowLayout(FlowLayout.LEFT,20,2));
				firstPane.add(name);
				firstPane.add(nameInput);
				mainPane.add(firstPane);
				JPanel secondPane = new JPanel(new FlowLayout(FlowLayout.LEFT,20,0));
				secondPane.add(vendor);
				secondPane.add(vendorInput);
				mainPane.add(secondPane);
				JPanel thirdPane = new JPanel(new FlowLayout(FlowLayout.LEFT,20,2));
				thirdPane.add(price);
				thirdPane.add(priceInput);
				mainPane.add(thirdPane);
				JPanel forthPane = new JPanel(new FlowLayout(FlowLayout.LEFT,20,2));
				forthPane.add(postPrice);
				forthPane.add(postPriceInput);
				mainPane.add(forthPane);
				JPanel fifthPane = new JPanel(new FlowLayout(FlowLayout.LEFT,20,2));
				fifthPane.add(spread);
				fifthPane.add(spreadInput);
				mainPane.add(fifthPane);
				JPanel sixthPane = new JPanel(new FlowLayout(FlowLayout.LEFT,20,2));
				sixthPane.add(start);
				sixthPane.add(startInput);
				mainPane.add(sixthPane);
				JPanel seventhPane = new JPanel(new FlowLayout(FlowLayout.LEFT,20,2));
				seventhPane.add(end);
				seventhPane.add(endInput);
				mainPane.add(seventhPane);
				JPanel eightthPane = new JPanel(new FlowLayout(FlowLayout.LEFT));
				eightthPane.add(floor);
				eightthPane.add(floorInput);
				mainPane.add(eightthPane);
				JPanel ninthPane = new JPanel(new FlowLayout(FlowLayout.LEFT));
				ninthPane.add(ceil);
				ninthPane.add(ceilInput);
				mainPane.add(ninthPane);
				confirmBtn.setUI(new BasicButtonUI());
				confirmBtn.setContentAreaFilled(false);
				confirmBtn.setMargin(new Insets(0,0,0,0));
				confirmBtn.setPreferredSize(new Dimension(64,64));
				mainPane.add(confirmBtn);
			}
		});
	}

	@Override
	public void createBottomPane() {

	}

}
