package com.rci.ui.swing.handler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.rci.service.core.IMetadataService;
import com.rci.tools.SpringUtils;
import com.rci.ui.swing.views.QueryFormPanel;
import com.rci.ui.swing.views.builder.WindowBuilderFactory;

public class MenuItemActionHandler {
	private static IMetadataService metadataService;
	private QueryFormPanel queryPanel;
	
	static{
		metadataService = (IMetadataService) SpringUtils.getBean("MetadataService");
	}

	public QueryFormPanel getQueryPanel() {
		return queryPanel;
	}

	public void setQueryPanel(QueryFormPanel queryPanel) {
		this.queryPanel = queryPanel;
	}
	
	/**
	 * 
	 * Describle(描述)： 基础数据重置
	 *
	 * 方法名称：doBaseInfoResetAction
	 *
	 * 所在类名：MenuItemActionHandler
	 *
	 * Create Time:2015年12月3日 上午11:12:47
	 *  
	 * @return
	 */
	public ActionListener doBaseInfoResetAction(){
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						if(JOptionPane.showConfirmDialog(null, "确定重置基础数据吗？","警告",JOptionPane.YES_NO_OPTION) == 0){
							SwingUtilities.invokeLater(new Runnable() {
								
								@Override
								public void run() {
									queryPanel.displayInfoLoading("基础数据正在重置，请稍后。。。");
								}
							});
							metadataService.resetMetadata();
							SwingUtilities.invokeLater(new Runnable() {
								
								@Override
								public void run() {
									queryPanel.displayInfoDone("基础数据重置成功");
								}
							});
						}
					}
				}).start();
			}
		};
	}
	
	/**
	 * 
	 * Describle(描述)： 外送率统计
	 *
	 * 方法名称：doExpressRateStatisticAction
	 *
	 * 所在类名：MenuItemActionHandler
	 *
	 * Create Time:2015年12月3日 上午11:15:22
	 *  
	 * @return
	 */
	public ActionListener doExpressRateStatisticAction(){
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent paramActionEvent) {
				WindowBuilderFactory.createExpressReateWindow();
			}
		};
	}
	
	/**
	 * 
	 * Describle(描述)：营业额统计
	 *
	 * 方法名称：doEarningStatisticAction
	 *
	 * 所在类名：MenuItemActionHandler
	 *
	 * Create Time:2015年12月3日 上午11:18:13
	 *  
	 * @return
	 */
	public ActionListener doEarningStatisticAction(){
		return new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				WindowBuilderFactory.createTurnoverWindow();
			}
		};
	}
	
	/**
	 * 
	 * Describle(描述)： 活动设置
	 *
	 * 方法名称：doActivitySettingAction
	 *
	 * 所在类名：MenuItemActionHandler
	 *
	 * Create Time:2015年12月3日 上午11:19:10
	 *  
	 * @return
	 */
	public ActionListener doActivitySettingAction(){
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				WindowBuilderFactory.createSchemeManagementWindow();
			}
		};
	}
	
	/**
	 * 
	 * Describle(描述)： 库存管理
	 *
	 * 方法名称：doInventoryManagmentAction
	 *
	 * 所在类名：MenuItemActionHandler
	 *
	 * Create Time:2015年12月3日 上午11:22:48
	 *  
	 * @return
	 */
	public ActionListener doInventoryManagmentAction(){
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				WindowBuilderFactory.createInventoryManagementWindow();
			}
		};
	}
	
	/**
	 * 
	 * Describle(描述)： 菜品管理
	 *
	 * 方法名称：doDishManagmentAction
	 *
	 * 所在类名：MenuItemActionHandler
	 *
	 * Create Time:2015年12月3日 上午11:23:42
	 *  
	 * @return
	 */
	public ActionListener doDishManagmentAction(){
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent paramActionEvent) {
				WindowBuilderFactory.createDishManagementWindow();
			}
		};
	}
}
