package com.rci.ui.swing.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import com.rci.bean.entity.Order;
import com.rci.bean.entity.Stock;
import com.rci.exceptions.ServiceException;
import com.rci.exceptions.ExceptionConstant.SERVICE;
import com.rci.metadata.dto.OrderDTO;
import com.rci.metadata.dto.OrderItemDTO;
import com.rci.metadata.service.IDataFetchService;
import com.rci.service.IFetchMarkService;
import com.rci.service.IOrderService;
import com.rci.service.filter.FilterChain;
import com.rci.service.utils.IExImportService;
import com.rci.service.utils.excel.ExcelExImportService;
import com.rci.service.utils.excel.ExcelSheet;
import com.rci.tools.DateUtil;
import com.rci.tools.SpringUtils;

public class DataIOListener implements ActionListener {
	public static final int EXPORT = 0;
	public static final int IMPORT = 1;
	
	private int action;
	
	private IDataFetchService fetchService;
	
	public DataIOListener(int action){
		this.action = action;
		fetchService = (IDataFetchService) SpringUtils.getBean("DataFetchService");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(action == EXPORT){
			exportData();
		}else if(action == IMPORT){
			importData();
		}
	}
	
	/**
	 * 
	 * Describle(描述)： 数据导出
	 *
	 * 方法名称：exportData
	 *
	 * 所在类名：DataIOHandler
	 *
	 * Create Time:2015年8月25日 上午10:20:38
	 *
	 */
	public void exportData(){
		String name = DateUtil.date2Str(new Date(), "yyyyMMdd") + ".xls";
		// 构造文件保存对话框
		final JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setDialogType(JFileChooser.SAVE_DIALOG);
		chooser.setMultiSelectionEnabled(false);
		chooser.setAcceptAllFileFilterUsed(false);
		chooser.setDialogTitle("原始数据文件导出");
		// 设置文件名称
		chooser.setSelectedFile(new File(name));
		// 添加文件过滤器
		chooser.addChoosableFileFilter(new FileFilter() {

			@Override
			public boolean accept(File f) {
				return true;
			}

			@Override
			public String getDescription() {
				return "所有文件(*.*)";
			}

		});
		chooser.addChoosableFileFilter(new FileFilter() {

			@Override
			public boolean accept(File f) {
				if (f.getName().endsWith("xls") || f.getName().endsWith("xlsx") || f.isDirectory()) {
					return true;
				} else {
					return false;
				}
			}

			@Override
			public String getDescription() {
				return "Excel文件(*.xls)";
			}

		});
		int result = chooser.showSaveDialog(null);
		switch (result) {
		case JFileChooser.APPROVE_OPTION:
			System.out.println(chooser.getSelectedFile().getAbsolutePath() + "-->这是绝对路径");// 绝对路径
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					IExImportService excelService = (IExImportService) SpringUtils.getBean("ExcelExImportService");
					try {
						String fileName = chooser.getSelectedFile().getName();
						List<ExcelSheet> sheets = new ArrayList<ExcelSheet>();
						String dateStr = fileName.substring(0,fileName.indexOf("."));
						Date exportDate = DateUtil.parseDate(dateStr, "yyyyMMdd");
						BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(chooser.getSelectedFile().getAbsolutePath())));
						
						ExcelSheet sheet1 = new ExcelSheet("订单列表");
						sheet1.setClazz(OrderDTO.class);
						sheet1.setHasHeader(true);
						List<OrderDTO> orders = fetchService.fetchOrders(exportDate, DateUtil.addDays(exportDate, 1));
						sheet1.setDataset(orders);
						
						ExcelSheet sheet2 = new ExcelSheet("菜品销售列表");
						sheet2.setClazz(OrderItemDTO.class);
						sheet2.setHasHeader(true);
						List<OrderItemDTO> orderItems = fetchService.fetchOrderItems(exportDate, DateUtil.addDays(exportDate, 1));
						sheet2.setDataset(orderItems);
						
						sheets.add(sheet1);
						sheets.add(sheet2);
						ExcelExImportService excelExportService =  (ExcelExImportService) excelService;
						excelExportService.setCustomSheets(sheets);
						excelService.exportTo(bos);
						JOptionPane.showMessageDialog(null, "导出成功");
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
			}).start();
			break;
		case JFileChooser.CANCEL_OPTION:
			System.out.println("取消");
		case JFileChooser.ERROR_OPTION:
			System.out.println("Error");
		}
	}
	
	/**
	 * 
	 * Describle(描述)： 数据导入
	 *
	 * 方法名称：importData
	 *
	 * 所在类名：DataIOHandler
	 *
	 * Create Time:2015年8月25日 上午10:20:24
	 *
	 */
	public void importData(){
		// 构造文件保存对话框
		final JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setDialogType(JFileChooser.OPEN_DIALOG);
		chooser.setMultiSelectionEnabled(false);
		chooser.setAcceptAllFileFilterUsed(false);
		chooser.setDialogTitle("原始数据文件导入");
		chooser.addChoosableFileFilter(new FileFilter() {

			@Override
			public boolean accept(File f) {
				return true;
			}

			@Override
			public String getDescription() {
				return "所有文件(*.*)";
			}

		});
		chooser.addChoosableFileFilter(new FileFilter() {

			@Override
			public boolean accept(File f) {
				if (f.getName().endsWith("xls") || f.getName().endsWith("xlsx") || f.isDirectory()) {
					return true;
				} else {
					return false;
				}
			}

			@Override
			public String getDescription() {
				return "Excel文件(*.xlsx)";
			}
		});
		int result = chooser.showOpenDialog(null);
		switch (result) {
		case JFileChooser.APPROVE_OPTION:
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					IExImportService excelService = (IExImportService) SpringUtils.getBean("ExcelExImportService");
					IFetchMarkService markService = (IFetchMarkService) SpringUtils.getBean("FetchMarkService");
					IOrderService orderService = (IOrderService) SpringUtils.getBean("OrderService");
					String fileName = chooser.getSelectedFile().getName();
					String day = fileName.substring(0,fileName.indexOf("."));
					try {
						Date date = DateUtil.parseDate(day, "yyyyMMdd");
						BufferedInputStream bin = new BufferedInputStream(new FileInputStream(new File(chooser.getSelectedFile().getAbsolutePath())));
						excelService.importFrom(bin);
						markService.markOrder(day);
						
						List<Order> orders = orderService.queryOrdersByDay(day);
						//解析订单各种账户收入的金额，判断订单使用的方案
						Map<String,BigDecimal> stockMap = new HashMap<String,BigDecimal>();
						for(Order order:orders){
							parseOrder(order);
							//插入库存变更记录
							addStockOpLog(order,stockMap);
						}
						//更新库存表
						for(Iterator<Entry<String,BigDecimal>> it=stockMap.entrySet().iterator();it.hasNext();){
							Entry<String,BigDecimal> entry = it.next();
							String sno = entry.getKey();
							BigDecimal amount = entry.getValue();
//							Stock stock = stockService.getStockByDishNo(dishNo);
							Stock stock = stockService.getStockBySno(sno);
							if(stock == null){
								throw new ServiceException(SERVICE.DATA_ERROR, sno+" - 该菜品不在库存控制范围内！");
							}else{
								BigDecimal balanceAmount = stock.getBalanceAmount().subtract(amount);
								BigDecimal consumeAmount = stock.getConsumeAmount().add(amount);
								stock.setBalanceAmount(balanceAmount);
								stock.setConsumeAmount(consumeAmount);
								stockService.rwUpdate(stock);
							}
						}
						
						JOptionPane.showMessageDialog(null, "导入成功");
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				}
			}).start();
			break;
		case JFileChooser.CANCEL_OPTION:
			System.out.println("取消");
		case JFileChooser.ERROR_OPTION:
			System.out.println("Error");
		}
	}
	
	public void parseOrder(Order order) {
		FilterChain chain = new FilterChain();
		chain.addFilters(filters);
		chain.doFilter(order, chain);
	}

	public int getAction() {
		return action;
	}

	public void setAction(int action) {
		this.action = action;
	}

}
