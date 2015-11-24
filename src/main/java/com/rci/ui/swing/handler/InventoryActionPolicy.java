package com.rci.ui.swing.handler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.rci.service.core.IMetadataService;
import com.rci.tools.SpringUtils;
import com.rci.tools.StringUtils;
import com.rci.ui.swing.listeners.DishSelectListener;
import com.rci.ui.swing.model.DishJCheckBox;
import com.rci.ui.swing.model.InventoryTable;
import com.rci.ui.swing.model.InventoryTable.InventoryTabelModel;
import com.rci.ui.swing.views.builder.InventoryAddWinBuilder;
import com.rci.ui.swing.views.builder.PopWindowBuilder;
import com.rci.ui.swing.views.component.DishSelectWin;
import com.rci.ui.swing.views.component.InventoryPurchaseWin;
import com.rci.ui.swing.vos.DishVO;
import com.rci.ui.swing.vos.InventoryVO;

public class InventoryActionPolicy extends DishSelectListener{
	private JTable table;
	private IMetadataService metaService;
	private DishSelectWin win;
	private JTextField keywordInput;	
	
	public InventoryActionPolicy(JTable table){
		this.table = table;
		metaService = (IMetadataService) SpringUtils.getBean("MetadataService");
	}
	
	/**
	 * 
	 * Describle(描述)：添加
	 *
	 * 方法名称：doAddAction
	 *
	 * 所在类名：InventoryActionPolicy
	 *
	 * Create Time:2015年11月24日 下午2:40:43
	 *  
	 * @return
	 */
	public ActionListener doAddAction(){
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent paramActionEvent) {
				PopWindowBuilder winBuilder = new InventoryAddWinBuilder(table);
				winBuilder.retrieveWindow();
			}
		};
	}
	
	/**
	 * 
	 * Describle(描述)： 停用
	 *
	 * 方法名称：doDisableAction
	 *
	 * 所在类名：InventoryActionPolicy
	 *
	 * Create Time:2015年11月24日 下午2:40:58
	 *  
	 * @return
	 */
	public ActionListener doDisableAction(){
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent paramActionEvent) {
				int selectIndex = table.getSelectedRow();
				if(selectIndex < 0){
					JOptionPane.showMessageDialog(null, "请选择一条要停用的数据！");
					return;
				}
				int operateCode = JOptionPane.showConfirmDialog(null, "<html><font color='red'>确定要停用此产品库存吗？</font></html>");
				if(operateCode == JOptionPane.CANCEL_OPTION || operateCode == JOptionPane.NO_OPTION){
					return;
				}
				InventoryTabelModel model = (InventoryTabelModel) table.getModel();
				InventoryVO inventoryvo = model.getInventory(selectIndex);
				metaService.disableInventory(inventoryvo.getIid());
				model.removeRow(selectIndex);
			}
		};
	}
	
	/**
	 * 
	 * Describle(描述)：刷新表格
	 *
	 * 方法名称：doReflushAction
	 *
	 * 所在类名：InventoryActionPolicy
	 *
	 * Create Time:2015年11月24日 下午2:43:43
	 *  
	 * @return
	 */
	public ActionListener doReflushAction(){
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent paramActionEvent) {
				((InventoryTable)table).reflush();
			}
		};
	}
	
	public ActionListener doRelateDishAction(){
		final DishSelectListener listener = this;
		return new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent paramActionEvent) {
				int selectIndex = table.getSelectedRow();
				if(selectIndex < 0){
					JOptionPane.showMessageDialog(null, "请选择一条要关联菜品的数据！");
					return;
				}
				win = new DishSelectWin(listener,900,600,"菜品选择");
			}
		};
	}
	
	/**
	 * 
	 * Describle(描述)：进货
	 *
	 * 方法名称：doPurchaseAction
	 *
	 * 所在类名：InventoryActionPolicy
	 *
	 * Create Time:2015年11月24日 下午2:51:49
	 *  
	 * @return
	 */
	public ActionListener doPurchaseAction(){
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent paramActionEvent) {
				int selectIndex = table.getSelectedRow();
				if(selectIndex < 0){
					JOptionPane.showMessageDialog(null, "请选择一条要关联菜品的数据！");
					return;
				}
				new InventoryPurchaseWin(table,300,350);
			}
		};
	}
	
	public ActionListener doQueryAction(){
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent event) {
				String keyword = keywordInput.getText();
				if(StringUtils.hasText(keyword)){
					List<InventoryVO> inventoryvos = metaService.queryInventory(keyword);
					InventoryTabelModel dm = (InventoryTabelModel) table.getModel();
					dm.setItems(inventoryvos);
					dm.fireTableDataChanged();
				}
			}
		};
	}

	@Override
	public void actionPerformed(ActionEvent paramActionEvent) {
		int selectIndex = table.getSelectedRow();
		InventoryTabelModel model = (InventoryTabelModel) table.getModel();
		InventoryVO inventoryvo = model.getInventory(selectIndex);
		List<DishVO> dishes = new ArrayList<DishVO>();
		for(DishJCheckBox selectDish:selectDishes){
			dishes.add(selectDish.getDish());
		}
		inventoryvo.setRelatedDishes(dishes);
		metaService.updateInventory(inventoryvo);
		((InventoryTable)table).reflush();
		win.close();
	}

	public JTextField getKeywordInput() {
		return keywordInput;
	}

	public void setKeywordInput(JTextField keywordInput) {
		this.keywordInput = keywordInput;
	}

}
