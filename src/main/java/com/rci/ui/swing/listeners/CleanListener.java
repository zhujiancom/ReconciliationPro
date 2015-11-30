package com.rci.ui.swing.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.rci.exceptions.ServiceException;
import com.rci.ui.swing.views.ConculsionPanel;
import com.rci.ui.swing.views.ContentPanel;
import com.rci.ui.swing.views.QueryFormPanel;

public class CleanListener implements ActionListener {
	private ConculsionPanel conclusionPane;
	private ContentPanel contentPane;
	private QueryFormPanel queryPanel;
	private Icon loadingIcon;
	private Icon doneIcon;
	
	public CleanListener(ContentPanel contentPane){
		this.contentPane = contentPane;
		URL loadingIconUrl = this.getClass().getClassLoader().getResource("skin/gray/images/24x24/loading.gif");
		loadingIcon = new ImageIcon(loadingIconUrl);
		URL doneIconUrl = this.getClass().getClassLoader().getResource("skin/gray/images/24x24/done.png");
		doneIcon = new ImageIcon(doneIconUrl);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		final String time = queryPanel.getTimeInput().getText();
		new Thread(new Runnable(){

			@Override
			public void run() {
				try{
					SwingUtilities.invokeLater(new Runnable(){

						@Override
						public void run() {
							queryPanel.getActionLabel().setIcon(loadingIcon);
							queryPanel.getActionLabel().setText("正在清除 "+ time +" 数据！");
						}
						
					});
					conclusionPane.clearData();
					contentPane.clearData(time);
					SwingUtilities.invokeLater(new Runnable(){

						@Override
						public void run() {
							queryPanel.getActionLabel().setText("日期："+time+" 数据清除成功！");
							queryPanel.getActionLabel().setIcon(doneIcon);
						}
						
					});
				}catch(ServiceException se){
					JOptionPane.showMessageDialog(null, new JLabel("<html><font color='red'>"+se.getMessage()+"</font></html>"));	
				}
			}
			
		}).start();
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
