package com.rci.ui.swing.listeners;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.rci.enums.BusinessEnums.Vendor;
import com.rci.metadata.service.IDataTransformService;
import com.rci.service.ISchemeService;
import com.rci.service.core.IMetadataService;
import com.rci.tools.SpringUtils;
import com.rci.ui.swing.model.DZDPSchemeModel;
import com.rci.ui.swing.model.DZDPSchemeTable;
import com.rci.ui.swing.vos.SchemeVO;

/**
 * 
 * @author zj
 *	
 * 项目名称：ReconciliationPro
 *
 * 类名称：SystemInitHandler
 *
 * 包名称：com.rci.ui.swing.handler
 *
 * Create Time: 2015年4月24日 下午2:56:16
 *
 * remark (备注): 系统初始化
 *
 */
public class SystemInitHandler extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4393899033664657099L;
	private ISchemeService schemeSverice;
	private IDataTransformService transformSevice;
	private IMetadataService metadataService;
	
	public SystemInitHandler(){
		schemeSverice = (ISchemeService) SpringUtils.getBean("SchemeService");
		transformSevice = (IDataTransformService) SpringUtils.getBean("DataTransformService");
		metadataService = (IMetadataService)SpringUtils.getBean("MetadataService");
	}
//
//	
	public ActionListener dataInit(){
//		return new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				transformSevice.transformTableInfo();
//				JOptionPane.showMessageDialog(null, "数据初始化成功！");
//			}
//		};
		return null;
	}
	
	public ActionListener stockInit(){
		return new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame frame = new JFrame("库存设置/查看");
				Container contentPane  = frame.getContentPane();
				JPanel mainPanel = new JPanel();
				JLabel gross = new JLabel("库存总量：");
				JTextField grossInput = new JTextField(5);
				JLabel consume = new JLabel("消费数量：");
				JTextField consumeInput = new JTextField(5);
				JButton save = new JButton(new AbstractAction() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						
						
					}
				});
			}
			
		};
	}
	
	/**
	 * 
	 * Describle(描述)：基础数据重置
	 *
	 * 方法名称：baseReset
	 *
	 * 所在类名：SystemInitHandler
	 *
	 * Create Time:2015年6月23日 下午2:14:18
	 *  
	 * @return
	 */
	public ActionListener baseReset(){
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				metadataService.resetMetadata();
				JOptionPane.showMessageDialog(null, "基础数据重置成功！");
			}
		};
	}
//	
	public ActionListener settings(){
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame frame = new JFrame("方案设置");
				Container contentPane  = frame.getContentPane();
				System.out.println(contentPane.getPreferredSize().width);
				System.out.println(contentPane.getPreferredSize().height);
				System.out.println(contentPane.getWidth());
				System.out.println(contentPane.getHeight());
				//布局管理器
				GridBagLayout layout = new GridBagLayout();
				contentPane.setLayout(layout);
				
				JPanel dzdpPanel = createDZDPPanel();
				JPanel elePanel = createELEPanel();
				
				GridBagConstraints s=new GridBagConstraints();
				s.gridwidth = 1;//设置组件水平所占用的格子数，如果为0，就说明该组件是该行的最后一个
				s.weightx = 1; // 设置组件水平的拉伸幅度，如果为0就说明不拉伸，不为0就随着窗口增大进行拉伸，0到1之间
				s.weighty = 0.5; //设置组件垂直的拉伸幅度，如果为0就说明不拉伸，不为0就随着窗口增大进行拉伸，0到1之间
				s.fill = GridBagConstraints.BOTH; //用来控制添加进的组件的显示位置
				layout.setConstraints(dzdpPanel, s);
				layout.setConstraints(elePanel, s);
				
				contentPane.add(dzdpPanel);
				contentPane.add(elePanel);
				
				frame.setSize(1200, 800);
				frame.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosed(WindowEvent e) {
						super.windowClosed(e);
					}
				});
				frame.setLocationRelativeTo(null); // 相对居中, 在frame设置size之后
				frame.setVisible(true);
			}

			private JPanel createDZDPPanel() {
				JPanel p = new JPanel();
				DZDPSchemeTable view = new DZDPSchemeTable();
				List<SchemeVO> schemes = schemeSverice.getSchemeVOs(Vendor.DZDP);
				DZDPSchemeModel dataModel = new DZDPSchemeModel(schemes);
				view.setModel(dataModel);
				view.setHeaderLabel();
				JScrollPane jsp = new JScrollPane(view);
				jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				jsp.setViewportView(view);
				p.add(jsp);
				
				return p;
			}
			
			private JPanel createELEPanel(){
				JPanel p = new JPanel();
				p.setPreferredSize(new Dimension(1000,800));
				
				DZDPSchemeTable view = new DZDPSchemeTable();
				List<SchemeVO> schemes = schemeSverice.getSchemeVOs(Vendor.ELE);
				DZDPSchemeModel dataModel = new DZDPSchemeModel(schemes);
				view.setModel(dataModel);
				view.setHeaderLabel();
				JScrollPane jsp = new JScrollPane(view);
				jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				jsp.setViewportView(view);
				p.add(jsp);
				
				return p;
			}
		};
	}

}
