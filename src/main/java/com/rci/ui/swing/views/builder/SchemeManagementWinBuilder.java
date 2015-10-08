package com.rci.ui.swing.views.builder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicButtonUI;

import com.rci.bean.LabelValueBean;
import com.rci.enums.BusinessEnums.Vendor;
import com.rci.service.core.IMetadataService;
import com.rci.tools.DateUtil;
import com.rci.tools.EnumUtils;
import com.rci.tools.SpringUtils;
import com.rci.ui.swing.listeners.VendorCheckListener;
import com.rci.ui.swing.model.SchemeTable;
import com.rci.ui.swing.model.VendorComboBoxModel;
import com.rci.ui.swing.model.VendorJCheckBox;
import com.rci.ui.swing.views.PopWindow;
import com.rci.ui.swing.vos.SchemeVO;

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
		JButton addBtn = new JButton(new ImageIcon("./src/main/resources/skin/gray/images/24x24/addBtn_0.png"));
		addBtn.setUI(new BasicButtonUI());
		addBtn.setContentAreaFilled(false);
		addBtn.setMargin(new Insets(0,0,0,0));
		addBtn.setBounds(4, 4, 24, 24);
		addBtn.setPressedIcon(new ImageIcon("./src/main/resources/skin/gray/images/24x24/addBtn_1.png"));
		
		JButton delBtn = new JButton(new ImageIcon("./src/main/resources/skin/gray/images/24x24/delBtn_0.png"));
		delBtn.setUI(new BasicButtonUI());
		delBtn.setContentAreaFilled(false);
		delBtn.setMargin(new Insets(0,0,0,0));
		delBtn.setBounds(40, 4, 24, 24);
		delBtn.setPressedIcon(new ImageIcon("./src/main/resources/skin/gray/images/24x24/delBtn_1.png"));
		
		JButton editBtn = new JButton(new ImageIcon("./src/main/resources/skin/gray/images/24x24/editBtn_0.png"));
		editBtn.setUI(new BasicButtonUI());
		editBtn.setContentAreaFilled(false);
		editBtn.setMargin(new Insets(0,0,0,0));
		editBtn.setBounds(80, 4, 24, 24);
		
		JButton refreshBtn = new JButton(new ImageIcon("./src/main/resources/skin/gray/images/24x24/refreshBtn_0.png"));
		refreshBtn.setUI(new BasicButtonUI());
		refreshBtn.setContentAreaFilled(false);
		refreshBtn.setMargin(new Insets(0,0,0,0));
		refreshBtn.setBounds(120, 4, 24, 24);
		refreshBtn.setPressedIcon(new ImageIcon("./src/main/resources/skin/gray/images/24x24/refreshBtn_1.png"));
		
		operaPane.add(addBtn);
		operaPane.add(delBtn);
		operaPane.add(editBtn);
		operaPane.add(refreshBtn);
		
		VendorCheckListener checkListener = new VendorCheckListener(table);
		JCheckBox allCheck = new VendorJCheckBox(null,"全部");
		allCheck.setSelected(true);
		allCheck.addItemListener(checkListener);
		allCheck.setBounds(160, 4, 24, 24);
		JCheckBox eleCheck = new VendorJCheckBox(Vendor.ELE,"饿了么");
		eleCheck.addItemListener(checkListener);
		eleCheck.setBounds(200, 4, 24, 24);
		JCheckBox mtwmCheck = new VendorJCheckBox(Vendor.MTWM,"美团外卖");
		mtwmCheck.addItemListener(checkListener);
		mtwmCheck.setBounds(240, 4, 24, 24);
		
		operaPane.add(allCheck);
		operaPane.add(eleCheck);
		operaPane.add(mtwmCheck);
		
		scrollPane = new JScrollPane();
		table = new SchemeTable(10);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setViewportView(table);
		scrollPane.setBounds(50, 65, 700, 400);
		
		contentPane.add(scrollPane);
		
		addBtn.addActionListener(new ActionListener() {
			private JTextField nameInput = new JTextField(30);
			private JComboBox<LabelValueBean<String>> vendorInput;
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
				final PopWindow addForm = new PopWindow(350,500);
				JPanel containerPanel = addForm.getContainerPanel();
				JPanel mainPane = new JPanel();
				mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.Y_AXIS));
				JScrollPane sPane = new JScrollPane(mainPane);
				buildMainPanel(mainPane);
				containerPanel.add(sPane,BorderLayout.CENTER);
				confirmBtn.addMouseListener(new MouseListener() {
					
					@Override
					public void mouseReleased(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mousePressed(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseExited(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseEntered(MouseEvent e) {
						confirmBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
						
					}
					
					@Override
					public void mouseClicked(MouseEvent e) {
						try{
							@SuppressWarnings("unchecked")
							LabelValueBean<String> item = (LabelValueBean<String>) vendorInput.getSelectedItem();
							SchemeVO newScheme = new SchemeVO();
							String vendor = item.getValue();
							newScheme.setVendor(Vendor.valueOf(vendor));
							newScheme.setName(nameInput.getText());
							newScheme.setPrice(new BigDecimal(priceInput.getText().trim()));
							newScheme.setPostPrice(new BigDecimal(postPriceInput.getText().trim()));
							newScheme.setSpread(new BigDecimal(spreadInput.getText().trim()));
							newScheme.setStartDate(DateUtil.parseDate(startInput.getText().trim(), "yyyyMMdd"));
							newScheme.setEndDate(DateUtil.parseDate(endInput.getText().trim(),"yyyyMMdd"));
							newScheme.setFloorAmount(new BigDecimal(floorInput.getText().trim()));
							newScheme.setCeilAmount(new BigDecimal(ceilInput.getText().trim()));
							IMetadataService metaService = (IMetadataService) SpringUtils.getBean("MetadataService");
							metaService.createScheme(newScheme);
							JOptionPane.showMessageDialog(null, "活动创建成功！");
							addForm.close();
						}catch (Exception ex){
							ex.printStackTrace();
						}
					}
				});
			}
			
			public void buildMainPanel(JPanel mainPane){
				JPanel firstPane = new JPanel(new FlowLayout(FlowLayout.LEFT,20,2));
				firstPane.add(name);
				firstPane.add(nameInput);
				mainPane.add(firstPane);
				JPanel secondPane = new JPanel(new FlowLayout(FlowLayout.LEFT,20,0));
				secondPane.add(vendor);
				List<LabelValueBean<String>> itemList = EnumUtils.getEnumLabelValueBeanList(Vendor.class, false);
				VendorComboBoxModel vcm = new VendorComboBoxModel(itemList);
				vendorInput = new JComboBox<LabelValueBean<String>>(vcm);
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
