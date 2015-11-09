package com.rci.ui.swing.views.component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
import com.rci.enums.BusinessEnums.Vendor;
import com.rci.service.ISchemeService;
import com.rci.tools.SpringUtils;
import com.rci.ui.swing.listeners.DataExportImportListener;
import com.rci.ui.swing.listeners.SchemeDataExportImportListener;
import com.rci.ui.swing.listeners.VendorCheckListener;
import com.rci.ui.swing.model.ButtonFactory;
import com.rci.ui.swing.model.SchemeStatusRadioButton;
import com.rci.ui.swing.model.SchemeTable;
import com.rci.ui.swing.model.SchemeTable.SchemeTabelModel;
import com.rci.ui.swing.model.VendorJCheckBox;
import com.rci.ui.swing.views.PopWindow;
import com.rci.ui.swing.views.builder.PopWindowBuilder;
import com.rci.ui.swing.views.builder.SchemeCreateWinBuilder;
import com.rci.ui.swing.views.builder.SchemeModifyWinBuilder;
import com.rci.ui.swing.vos.SchemeVO;

public class SchemeManagementWin extends PopWindow {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3033087901494913412L;

	private JPanel operaPane;
	private JScrollPane scrollPane;
	private JTable table;
	private JButton addBtn;
	private JButton editBtn;
	private JButton refreshBtn;
	private JButton delBtn;
	private JButton exportBtn;
	private JButton importBtn;
	
	public SchemeManagementWin(int width,int height,String title){
		super(width,height,title);
		createContentPane();
		
	}
	
	public void createContentPane() {
		JPanel containerPanel = this.getContainerPanel();
		operaPane = new JPanel();
		operaPane.setLayout(new FlowLayout(FlowLayout.LEFT));
		operaPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		operaPane.setBackground(Color.WHITE);
		
		table = new SchemeTable(10);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		final VendorCheckListener checkListener = new VendorCheckListener(table);
		
		createOperationBtn(checkListener);
		createCheckBoxBtn(checkListener);
		createRadioBtn(checkListener);
		
		scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setViewportView(table);
//		scrollPane.setBounds(50, 65, 700, 400);
		
		BorderLayout layout = (BorderLayout) containerPanel.getLayout();
		layout.setHgap(0);
		layout.setVgap(0);
		containerPanel.add(operaPane,BorderLayout.NORTH);
		containerPanel.add(scrollPane,BorderLayout.CENTER);
	}
	
	/**
	 * 
	 * Describle(描述)： 创建表格操作按钮
	 *
	 * 方法名称：createOperationBtn
	 *
	 * 所在类名：SchemeManagementWin
	 *
	 * Create Time:2015年10月30日 上午10:36:13
	 *  
	 * @param checkListener
	 */
	private void createOperationBtn(final VendorCheckListener checkListener){
		addBtn = ButtonFactory.createImageButton("skin/gray/images/24x24/addBtn_0.png", "skin/gray/images/24x24/addBtn_1.png");
		addBtn.setToolTipText("新增");
		addBtn.setBounds(4, 4, 24, 24);
		
		delBtn = ButtonFactory.createImageButton("skin/gray/images/24x24/delBtn_0.png", "skin/gray/images/24x24/delBtn_1.png");
		delBtn.setToolTipText("删除");
		delBtn.setBounds(40, 4, 24, 24);
		
		editBtn = ButtonFactory.createImageButton("skin/gray/images/24x24/editBtn_0.png", null);
		editBtn.setToolTipText("更新");
		editBtn.setBounds(80, 4, 24, 24);
		
		refreshBtn = ButtonFactory.createImageButton("skin/gray/images/24x24/refreshBtn_0.png", "skin/gray/images/24x24/refreshBtn_1.png");
		refreshBtn.setToolTipText("刷新");
		refreshBtn.setBounds(120, 4, 24, 24);

		exportBtn = ButtonFactory.createImageButton("skin/gray/images/24x24/export_3.png", null);
		exportBtn.setToolTipText("导出");
		exportBtn.setBounds(160, 4, 24, 24);
		
		importBtn = ButtonFactory.createImageButton("skin/gray/images/24x24/import_1.png", null);
		importBtn.setToolTipText("导入");
		importBtn.setBounds(200, 4, 24, 24);
		
		operaPane.add(addBtn);
		operaPane.add(delBtn);
		operaPane.add(editBtn);
		operaPane.add(refreshBtn);
		operaPane.add(exportBtn);
		operaPane.add(importBtn);
		
		//添加
		addBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				PopWindowBuilder winBuilder = new SchemeCreateWinBuilder(checkListener);
				winBuilder.retrieveWindow();
			}
		});
		
		//刷新
		refreshBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				checkListener.refreshTableData();
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
				int operateCode = JOptionPane.showConfirmDialog(null, "<html><font color='red'>确定要删除此活动吗？</font></html>");
				if(operateCode == JOptionPane.CANCEL_OPTION || operateCode == JOptionPane.NO_OPTION){
					return;
				}
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
				if(selectIndex < 0){
					JOptionPane.showMessageDialog(null, "请选择一条要修改的数据！");
					return;
				}
				PopWindowBuilder winBuilder = new SchemeModifyWinBuilder(checkListener);
				winBuilder.retrieveWindow();
			}
		});
		
		//活动数据导出
		DataExportImportListener dataExportListener = new SchemeDataExportImportListener(DataExportImportListener.EXPORT);
		exportBtn.addActionListener(dataExportListener);
		
		DataExportImportListener dataImportListener = new SchemeDataExportImportListener(DataExportImportListener.IMPORT);
		importBtn.addActionListener(dataImportListener);
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2){
					PopWindowBuilder winBuilder = new SchemeModifyWinBuilder(checkListener);
					winBuilder.retrieveWindow();
				}
			}
		});
	}

	/**
	 * 
	 * Describle(描述)： 创建checkbox选项
	 *
	 * 方法名称：createCheckBoxBtn
	 *
	 * 所在类名：SchemeManagementWin
	 *
	 * Create Time:2015年10月30日 上午10:39:34
	 *  
	 * @param checkListener
	 */
	private void createCheckBoxBtn(final VendorCheckListener checkListener){
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
		JCheckBox dptgCheck = new VendorJCheckBox(3,Vendor.DZDP,"大众点评");
		dptgCheck.addItemListener(checkListener);
		dptgCheck.setBounds(400, 4, 80, 24);
		JCheckBox wmcrCheck = new VendorJCheckBox(4,Vendor.WMCR,"外卖超人");
		wmcrCheck.addItemListener(checkListener);
		wmcrCheck.setBounds(480, 4, 80, 24);
		JCheckBox mtCheck = new VendorJCheckBox(4,Vendor.MT,"美团");
		mtCheck.addItemListener(checkListener);
		mtCheck.setBounds(560, 4, 80, 24);
		JCheckBox bdnmCheck = new VendorJCheckBox(4,Vendor.BDNM,"百度糯米");
		bdnmCheck.addItemListener(checkListener);
		bdnmCheck.setBounds(640, 4, 80, 24);
//		JCheckBox shCheck = new VendorJCheckBox(4,Vendor.SH,"闪惠");
//		shCheck.addItemListener(checkListener);
//		shCheck.setBounds(640, 4, 80, 24);
//		JCheckBox mtsuperCheck = new VendorJCheckBox(4,Vendor.MTSUPER,"美团超券");
//		mtsuperCheck.addItemListener(checkListener);
//		mtsuperCheck.setBounds(720, 4, 80, 24);
		
		ButtonGroup checkGroup = new ButtonGroup();
		checkGroup.add(allCheck);
		checkGroup.add(eleCheck);
		checkGroup.add(mtwmCheck);
		checkGroup.add(dptgCheck);
		checkGroup.add(wmcrCheck);
		checkGroup.add(mtCheck);
		checkGroup.add(bdnmCheck);
		
		operaPane.add(allCheck);
		operaPane.add(eleCheck);
		operaPane.add(mtwmCheck);
		operaPane.add(dptgCheck);
		operaPane.add(wmcrCheck);
		operaPane.add(mtCheck);
		operaPane.add(bdnmCheck);
	}

	/**
	 * 
	 * Describle(描述)： 创建radio选项
	 *
	 * 方法名称：createRadioBtn
	 *
	 * 所在类名：SchemeManagementWin
	 *
	 * Create Time:2015年10月30日 上午10:39:59
	 *  
	 * @param checkListener
	 */
	private void createRadioBtn(final VendorCheckListener checkListener){
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
