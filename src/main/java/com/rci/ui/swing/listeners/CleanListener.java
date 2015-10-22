package com.rci.ui.swing.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.rci.exceptions.ServiceException;
import com.rci.ui.swing.views.ConculsionPanel;
import com.rci.ui.swing.views.ContentPanel;
import com.rci.ui.swing.views.QueryFormPanel;

public class CleanListener implements ActionListener {
	private ConculsionPanel conclusionPane;
	private ContentPanel contentPane;
	private QueryFormPanel queryPanel;
	
	public CleanListener(ContentPanel contentPane){
		this.contentPane = contentPane;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		final String time = queryPanel.getTimeInput().getText();
//		new Thread(new Runnable(){
//
//			@Override
//			public void run() {
//				try{
//					contentPane.clearData(time);
//					conclusionPane.clearData();
//					JOptionPane.showMessageDialog(null, "日期："+time+" 数据清除成功！");
//				}catch(ServiceException se){
//					JOptionPane.showMessageDialog(null, se.getMessage());
//				}
//			}
//			
//		}).start();
		try{
			contentPane.clearData(time);
			conclusionPane.clearData();
			JOptionPane.showMessageDialog(null, "日期："+time+" 数据清除成功！");
		}catch(ServiceException se){
			JOptionPane.showMessageDialog(null, new JLabel("<html><font color='red'>"+se.getMessage()+"</font></html>"));	
		}
	}

	public ConculsionPanel getConclusionPane() {
		return conclusionPane;
	}

	public void setConclusionPane(ConculsionPanel conclusionPane) {
		this.conclusionPane = conclusionPane;
	}

	public QueryFormPanel getQueryPanel() {
		return queryPanel;
	}

	public void setQueryPanel(QueryFormPanel queryPanel) {
		this.queryPanel = queryPanel;
	}

	public ContentPanel getContentPane() {
		return contentPane;
	}

	public void setContentPane(ContentPanel contentPane) {
		this.contentPane = contentPane;
	}

}
