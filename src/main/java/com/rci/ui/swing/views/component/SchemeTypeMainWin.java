package com.rci.ui.swing.views.component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import com.rci.enums.BusinessEnums.ActivityStatus;
import com.rci.enums.BusinessEnums.ActivityType;
import com.rci.service.ISchemeTypeService;
import com.rci.tools.EnumUtils;
import com.rci.tools.SpringUtils;
import com.rci.ui.swing.listeners.ActivityTypeCheckListener;
import com.rci.ui.swing.model.ActivityTypeJCheckBox;
import com.rci.ui.swing.model.ButtonFactory;
import com.rci.ui.swing.model.SchemeStatusRadioButton;
import com.rci.ui.swing.model.SchemeTypeTable;
import com.rci.ui.swing.model.SchemeTypeTable.SchemeTypeTabelModel;
import com.rci.ui.swing.views.PopWindow;
import com.rci.ui.swing.views.builder.PopWindowBuilder;
import com.rci.ui.swing.views.builder.SchemeTypeCreateWinBuilder;
import com.rci.ui.swing.vos.SchemeTypeVO;

public class SchemeTypeMainWin extends PopWindow {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3368829215325628253L;
	private JPanel operaPane;
	private JScrollPane scrollPane;
	private JTable table;
	private JButton addBtn;
	private JButton delBtn;
	private JButton refreshBtn;
	
	public SchemeTypeMainWin(int width,int height,String title){
		super(width,height,title);
		createContentPane();
	}
	
	public void createContentPane() {
		JPanel containerPanel = this.getContainerPanel();
		operaPane = new JPanel();
		operaPane.setLayout(new FlowLayout(FlowLayout.LEFT));
		operaPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		operaPane.setBackground(Color.WHITE);
		
		table = new SchemeTypeTable(7);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		ActivityTypeCheckListener checkListener = new ActivityTypeCheckListener(table);
		createOperationBtn(checkListener);
		createCheckBoxBtn(checkListener);
		createRadioBtn(checkListener);
		
		scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setViewportView(table);
		
		BorderLayout layout = (BorderLayout) containerPanel.getLayout();
		layout.setHgap(0);
		layout.setVgap(0);
		containerPanel.add(operaPane,BorderLayout.NORTH);
		containerPanel.add(scrollPane,BorderLayout.CENTER);
	}
	
	private void createOperationBtn(final ActivityTypeCheckListener checkListener){
		addBtn = ButtonFactory.createImageButton("skin/gray/images/24x24/addBtn_0.png", "skin/gray/images/24x24/addBtn_1.png");
		addBtn.setToolTipText("新增");
		addBtn.setBounds(4, 4, 24, 24);
		delBtn = ButtonFactory.createImageButton("skin/gray/images/24x24/delBtn_0.png", "skin/gray/images/24x24/delBtn_1.png");
		delBtn.setToolTipText("删除");
		delBtn.setBounds(40, 4, 24, 24);
		refreshBtn = ButtonFactory.createImageButton("skin/gray/images/24x24/refreshBtn_0.png", "skin/gray/images/24x24/refreshBtn_1.png");
		refreshBtn.setToolTipText("刷新");
		refreshBtn.setBounds(120, 4, 24, 24);
		operaPane.add(addBtn);
		operaPane.add(delBtn);
		operaPane.add(refreshBtn);
		
		//添加
		addBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				PopWindowBuilder winBuilder = new SchemeTypeCreateWinBuilder(checkListener);
				winBuilder.retrieveWindow();
			}
		});
		
		//刷新
		refreshBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
//				checkListener.refreshTableData();
			}
		});
		
		//删除
		delBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectIndex = table.getSelectedRow();
				if(selectIndex < 0){
					JOptionPane.showMessageDialog(null, "请选择一条要删除的数据！");
					return;
				}
				int operateCode = JOptionPane.showConfirmDialog(null, "<html><font color='red'>确定要删除此活动类型吗？</font></html>");
				if(operateCode == JOptionPane.CANCEL_OPTION || operateCode == JOptionPane.NO_OPTION){
					return;
				}
				SchemeTypeTabelModel model = (SchemeTypeTabelModel) table.getModel();
				SchemeTypeVO scheme = model.getScheme(selectIndex);
				ISchemeTypeService schemeTypeService = (ISchemeTypeService) SpringUtils.getBean("SchemeTypeService");
				schemeTypeService.rwDelete(scheme.getStid());
				model.removeRow(selectIndex);
			}
		});
	}
	
	private void createCheckBoxBtn(final ActivityTypeCheckListener checkListener){
		JCheckBox allCheck = new ActivityTypeJCheckBox(0,null,"全部");
		allCheck.setSelected(true);
		allCheck.addItemListener(checkListener);
		allCheck.setBounds(160, 4, 80, 24);
		JCheckBox voucherCheck = new ActivityTypeJCheckBox(1,ActivityType.VOUCHER,EnumUtils.getEnumMessage(ActivityType.VOUCHER));
		voucherCheck.addItemListener(checkListener);
		voucherCheck.setBounds(240, 4, 80, 24);
		JCheckBox takeoutCheck = new ActivityTypeJCheckBox(2,ActivityType.TAKEOUT,EnumUtils.getEnumMessage(ActivityType.TAKEOUT));
		takeoutCheck.addItemListener(checkListener);
		takeoutCheck.setBounds(320, 4, 80, 24);
		
		operaPane.add(allCheck);
		operaPane.add(voucherCheck);
		operaPane.add(takeoutCheck);
	}
	
	private void createRadioBtn(final ActivityTypeCheckListener checkListener){
		SchemeStatusRadioButton active = new SchemeStatusRadioButton(ActivityStatus.ACTIVE,"进行中",true);
		active.setBounds(800, 4, 75, 24);
		active.setBackground(Color.WHITE);
		active.addActionListener(checkListener);
		SchemeStatusRadioButton inactive = new SchemeStatusRadioButton(ActivityStatus.INACTIVE,"已结束");
		inactive.setBounds(875, 4, 75, 24);
		inactive.setBackground(Color.WHITE);
		inactive.addActionListener(checkListener);
		ButtonGroup btnGroup = new ButtonGroup();
		btnGroup.add(active);
		btnGroup.add(inactive);
		operaPane.add(active);
		operaPane.add(inactive);
	}
}
