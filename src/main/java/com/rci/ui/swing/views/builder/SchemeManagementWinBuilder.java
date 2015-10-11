package com.rci.ui.swing.views.builder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.plaf.basic.BasicButtonUI;

import com.rci.enums.BusinessEnums.Vendor;
import com.rci.service.ISchemeService;
import com.rci.tools.SpringUtils;
import com.rci.ui.swing.listeners.VendorCheckListener;
import com.rci.ui.swing.model.SchemeTable;
import com.rci.ui.swing.model.SchemeTable.SchemeTabelModel;
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
		URL addBtnUrl1 = this.getClass().getClassLoader().getResource("skin/gray/images/24x24/addBtn_0.png");
		JButton addBtn = new JButton(new ImageIcon(addBtnUrl1));
		addBtn.setUI(new BasicButtonUI());
		addBtn.setContentAreaFilled(false);
		addBtn.setMargin(new Insets(0,0,0,0));
		addBtn.setBounds(4, 4, 24, 24);
		URL addBtnUrl2 = this.getClass().getClassLoader().getResource("skin/gray/images/24x24/addBtn_1.png");
		addBtn.setPressedIcon(new ImageIcon(addBtnUrl2));
		
		URL delBtnUrl1 = this.getClass().getClassLoader().getResource("skin/gray/images/24x24/delBtn_0.png");
		JButton delBtn = new JButton(new ImageIcon(delBtnUrl1));
		delBtn.setUI(new BasicButtonUI());
		delBtn.setContentAreaFilled(false);
		delBtn.setMargin(new Insets(0,0,0,0));
		delBtn.setBounds(40, 4, 24, 24);
		URL delBtnUrl2 = this.getClass().getClassLoader().getResource("skin/gray/images/24x24/delBtn_1.png");
		delBtn.setPressedIcon(new ImageIcon(delBtnUrl2));
		
		URL editBtnUrl = this.getClass().getClassLoader().getResource("skin/gray/images/24x24/editBtn_0.png");
		JButton editBtn = new JButton(new ImageIcon(editBtnUrl));
		editBtn.setUI(new BasicButtonUI());
		editBtn.setContentAreaFilled(false);
		editBtn.setMargin(new Insets(0,0,0,0));
		editBtn.setBounds(80, 4, 24, 24);
		
		URL refreshUrl1 = this.getClass().getClassLoader().getResource("skin/gray/images/24x24/refreshBtn_0.png");
		JButton refreshBtn = new JButton(new ImageIcon(refreshUrl1));
		refreshBtn.setUI(new BasicButtonUI());
		refreshBtn.setContentAreaFilled(false);
		refreshBtn.setMargin(new Insets(0,0,0,0));
		refreshBtn.setBounds(120, 4, 24, 24);
		URL refreshUrl2 = this.getClass().getClassLoader().getResource("skin/gray/images/24x24/refreshBtn_1.png");
		refreshBtn.setPressedIcon(new ImageIcon(refreshUrl2));
		
		operaPane.add(addBtn);
		operaPane.add(delBtn);
		operaPane.add(editBtn);
		operaPane.add(refreshBtn);
		
		table = new SchemeTable(10);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		VendorCheckListener checkListener = new VendorCheckListener(table);
		JCheckBox allCheck = new VendorJCheckBox(0,null,"全部");
		allCheck.setSelected(true);
		allCheck.addItemListener(checkListener);
		allCheck.setBounds(160, 4, 80, 24);
		JCheckBox eleCheck = new VendorJCheckBox(1,Vendor.ELE,"饿了么");
		eleCheck.addItemListener(checkListener);
		eleCheck.setBounds(240, 4, 80, 24);
		JCheckBox mtwmCheck = new VendorJCheckBox(2,Vendor.MTWM,"美团外卖");
		mtwmCheck.addItemListener(checkListener);
		mtwmCheck.setBounds(320, 4, 80, 24);
		
		operaPane.add(allCheck);
		operaPane.add(eleCheck);
		operaPane.add(mtwmCheck);
		
		scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setViewportView(table);
		scrollPane.setBounds(50, 65, 700, 400);
		
		contentPane.add(scrollPane);
		
		//添加
		addBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				WindowBuilderFactory.createSchemeAddFormWindow();
			}
		});
		
		//刷新
		refreshBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				((SchemeTable)table).refresh();
			}
		});
		
		//删除
		delBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectIndex = table.getSelectedRow();
				SchemeTabelModel model = (SchemeTabelModel) table.getModel();
				SchemeVO scheme = model.getScheme(selectIndex);
				ISchemeService schemeService = (ISchemeService) SpringUtils.getBean("SchemeService");
				schemeService.rwDelete(scheme.getSid());
				model.removeRow(selectIndex);
			}
		});
		
		//编辑
		editBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectIndex = table.getSelectedRow();
				SchemeTabelModel model = (SchemeTabelModel) table.getModel();
				SchemeVO scheme = model.getScheme(selectIndex);
				PopWindowBuilder winBuilder = new SchemeModifyWinBuilder(scheme);
				winBuilder.retrieveWindow();
			}
		});
	}

	@Override
	public void createBottomPane() {

	}

}
