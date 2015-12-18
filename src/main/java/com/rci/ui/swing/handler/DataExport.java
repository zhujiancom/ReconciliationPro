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
	private JFileChooser chooser;
	
	private IExImportService excelService;
	
	private List<ExcelSheet> sheets;
	
	public DataExport(JFileChooser chooser,IExImportService excelService){
		this.chooser = chooser;
		this.excelService = excelService;
	}
	
	public DataExport(JFileChooser chooser,PopWindow progressBarWin ,IExImportService excelService){
		this.chooser = chooser;
		this.excelService = excelService;
	}
	
	@Override
	public void run() {
		try {
			excelService.setCustomSheet(sheets);
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(chooser.getSelectedFile().getAbsolutePath())));
			excelService.exportTo(bos);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (ServiceException se){
			JOptionPane.showMessageDialog(null, se.getMessage());
		} catch(Exception e){
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace(); 
		}
	}

	public JFileChooser getChooser() {
		return chooser;
	}

	public void setChooser(JFileChooser chooser) {
		this.chooser = chooser;
	}

	public List<ExcelSheet> getSheets() {
		return sheets;
	}

	public void setSheets(List<ExcelSheet> sheets) {
		this.sheets = sheets;
	}
}
