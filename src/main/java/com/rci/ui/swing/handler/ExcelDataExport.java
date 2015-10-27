package com.rci.ui.swing.handler;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.springframework.util.CollectionUtils;

import com.rci.exceptions.ExceptionConstant.SERVICE;
import com.rci.exceptions.ExceptionManage;
import com.rci.exceptions.ServiceException;
import com.rci.metadata.dto.OrderDTO;
import com.rci.metadata.dto.OrderItemDTO;
import com.rci.metadata.service.IDataFetchService;
import com.rci.service.utils.IExImportService;
import com.rci.service.utils.excel.ExcelExImportService;
import com.rci.service.utils.excel.ExcelSheet;
import com.rci.service.utils.excel.ExcelSheetImpl;
import com.rci.service.utils.excel.IExcelSheet;
import com.rci.tools.DateUtil;
import com.rci.tools.SpringUtils;
import com.rci.ui.swing.views.PopWindow;

public class ExcelDataExport implements Runnable {
	private int amount;
	
	private int current;
	
	private JFileChooser chooser;
	
	private PopWindow progressBarWin;
	
	public ExcelDataExport(JFileChooser chooser,PopWindow progressBarWin){
		this.chooser = chooser;
		this.progressBarWin = progressBarWin;
		amount = 100;
		current = 0;
	}
	
	@Override
	public void run() {
		IExImportService excelService = (IExImportService) SpringUtils.getBean("ExcelExImportService");
		IDataFetchService fetchService = (IDataFetchService) SpringUtils.getBean("DataFetchService");
		try {
			String fileName = chooser.getSelectedFile().getName();
			List<ExcelSheet> sheets = new ArrayList<ExcelSheet>();
			String dateStr = fileName.substring(0,fileName.indexOf("."));
			Date exportDate = DateUtil.parseDate(dateStr, "yyyyMMdd");
			ExcelSheet sheet1 = new ExcelSheet("订单列表");
			sheet1.setClazz(OrderDTO.class);
			sheet1.setHasHeader(true);
			List<OrderDTO> orders = fetchService.fetchOrders(exportDate, DateUtil.addDays(exportDate, 1));
			if(CollectionUtils.isEmpty(orders)){
				ExceptionManage.throwServiceException(SERVICE.DATA_ERROR, "没有数据，备份文件失败");
			}
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
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(chooser.getSelectedFile().getAbsolutePath())));
			excelService.exportTo(bos);
			while(current < amount){
				try{
					Thread.sleep(50);
				}catch(InterruptedException ie){
					
				}
				current++;
			}
			progressBarWin.close();
			JOptionPane.showMessageDialog(null, "导出成功");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (ServiceException se){
			JOptionPane.showMessageDialog(null, se.getMessage());
		} catch(Exception e){
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

//	@Override
//	public void run() {
//		IExImportService orderExcelService = (IExImportService) SpringUtils.getBean("OrderExcelService");
//		IDataFetchService fetchService = (IDataFetchService) SpringUtils.getBean("DataFetchService");
//		try {
//			String fileName = chooser.getSelectedFile().getName();
//			List<IExcelSheet> sheets = new ArrayList<IExcelSheet>();
//			String dateStr = fileName.substring(0,fileName.indexOf("."));
//			Date exportDate = DateUtil.parseDate(dateStr, "yyyyMMdd");
//			ExcelSheetImpl<OrderDTO> sheet1 = new ExcelSheetImpl<OrderDTO>("订单列表");
//			List<OrderDTO> orders = fetchService.fetchOrders(exportDate, DateUtil.addDays(exportDate, 1));
//			if(CollectionUtils.isEmpty(orders)){
//				ExceptionManage.throwServiceException(SERVICE.DATA_ERROR, "没有数据，备份文件失败");
//			}
//			sheet1.setDataset(orders);
//			
//			ExcelSheetImpl<OrderItemDTO> sheet2 = new ExcelSheetImpl<OrderItemDTO>("菜品销售列表");
//			List<OrderItemDTO> orderItems = fetchService.fetchOrderItems(exportDate, DateUtil.addDays(exportDate, 1));
//			sheet2.setDataset(orderItems);
//			
//			sheets.add(sheet1);
//			sheets.add(sheet2);
//			orderExcelService.setCustomSheet(sheets);
//			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(chooser.getSelectedFile().getAbsolutePath())));
//			orderExcelService.exportTo(bos);
//			while(current < amount){
//				try{
//					Thread.sleep(50);
//				}catch(InterruptedException ie){
//					
//				}
//				current++;
//			}
//			progressBarWin.close();
//			JOptionPane.showMessageDialog(null, "导出成功");
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (ParseException e) {
//			e.printStackTrace();
//		} catch (ServiceException se){
//			JOptionPane.showMessageDialog(null, se.getMessage());
//		} catch(Exception e){
//			JOptionPane.showMessageDialog(null, e.getMessage());
//		}
//	}
	
	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getCurrent() {
		return current;
	}

	public void setCurrent(int current) {
		this.current = current;
	}

}
