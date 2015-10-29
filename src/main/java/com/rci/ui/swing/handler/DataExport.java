package com.rci.ui.swing.handler;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import com.rci.exceptions.ServiceException;
import com.rci.service.utils.IExImportService;
import com.rci.service.utils.excel.ExcelSheet;
import com.rci.ui.swing.views.PopWindow;

public class DataExport implements Runnable{
	private int amount;
	
	private int current;
	
	private JFileChooser chooser;
	
	private PopWindow progressBarWin;
	
	private IExImportService excelService;
	
	private List<ExcelSheet> sheets;
	
	public DataExport(JFileChooser chooser,PopWindow progressBarWin ,IExImportService excelService){
		this.chooser = chooser;
		this.progressBarWin = progressBarWin;
		this.excelService = excelService;
		amount = 100;
		current = 0;
	}
	
	@Override
	public void run() {
		try {
			excelService.setCustomSheet(sheets);
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
		}catch (ServiceException se){
			JOptionPane.showMessageDialog(null, se.getMessage());
		} catch(Exception e){
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace(); 
		}
	}

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

	public JFileChooser getChooser() {
		return chooser;
	}

	public void setChooser(JFileChooser chooser) {
		this.chooser = chooser;
	}

	public PopWindow getProgressBarWin() {
		return progressBarWin;
	}

	public void setProgressBarWin(PopWindow progressBarWin) {
		this.progressBarWin = progressBarWin;
	}

	public List<ExcelSheet> getSheets() {
		return sheets;
	}

	public void setSheets(List<ExcelSheet> sheets) {
		this.sheets = sheets;
	}
}
