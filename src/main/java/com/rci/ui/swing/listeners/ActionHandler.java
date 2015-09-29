package com.rci.ui.swing.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.rci.service.ISchemeService;
import com.rci.service.core.IMetadataService;
import com.rci.tools.SpringUtils;

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
public class ActionHandler extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4393899033664657099L;
	private ISchemeService schemeSverice;
	private IMetadataService metadataService;
	
	public ActionHandler(){
		schemeSverice = (ISchemeService) SpringUtils.getBean("SchemeService");
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
				if(JOptionPane.showConfirmDialog(null, "确定重置基础数据吗？","警告",JOptionPane.YES_NO_OPTION) == 0){
					metadataService.resetMetadata();
					JOptionPane.showMessageDialog(null, "基础数据重置成功！");
				}
			}
		};
	}
	

	/**
	 * 
	 * Describle(描述)：饿了么刷单补贴金额设置
	 *
	 * 方法名称：eleSdAllowanceSet
	 *
	 * 所在类名：ActionHandler
	 *
	 * Create Time:2015年7月27日 下午4:58:43
	 *  
	 * @return
	 */
	public ActionListener eleSdAllowanceSet(){
		return new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				final JFrame frame = new JFrame("每单补贴金额设置");
				JPanel subpanel = new JPanel();
				frame.add(subpanel);
				final JTextField amountInput = new JTextField(10);
				JButton saveBtn = new JButton("确定");
				saveBtn.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						frame.dispose();
					}
				});
				
				subpanel.add(amountInput);
				subpanel.add(saveBtn);
				
				
				frame.setSize(300, 100);
				frame.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosed(WindowEvent e) {
						super.windowClosed(e);
					}
				});
				frame.setLocationRelativeTo(null); // 相对居中, 在frame设置size之后
				frame.setVisible(true);
			}
			
		};
	}
	
	public ActionListener test(){
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null,metadataService.getTimerStatus());
			}
		};
	}
}
