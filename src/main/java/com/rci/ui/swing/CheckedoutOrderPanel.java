package com.rci.ui.swing;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.KeyStroke;

import com.rci.ui.swing.handler.InventoryWarningHandler;
import com.rci.ui.swing.listeners.CleanListener;
import com.rci.ui.swing.listeners.QueryListener;
import com.rci.ui.swing.views.ConculsionPanel;
import com.rci.ui.swing.views.ContentPanel;
import com.rci.ui.swing.views.QueryFormPanel;

public class CheckedoutOrderPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1912248260264394907L;
	
	public static final String NAME="CheckedoutOrderPanel";
	
	private ConculsionPanel conclusionPane = new ConculsionPanel(); // 统计信息面板
	private QueryFormPanel queryPanel = new QueryFormPanel(); // 查询面板
	ContentPanel contentPane; // 订单数据内容展示面板
	private int width;
	private int height;
	
	public CheckedoutOrderPanel(int width,int height){
		this.width = width;
		this.height = height;
		setName(NAME);
		initComponent();
	}

	private void initComponent() {
		BorderLayout layout = new BorderLayout(0, 0);
		setLayout(layout);
		/* 绑定查询form */
		add(queryPanel,BorderLayout.NORTH);
		/* 绑定订单内容和警告日志展示列表 */
		contentPane = new ContentPanel(JSplitPane.HORIZONTAL_SPLIT,width,height);
		add(contentPane, BorderLayout.CENTER);
		/* 绑定 总结页脚 */
		add(conclusionPane, BorderLayout.SOUTH);
		/* 绑定事件 */
		bindListeners();
	}

	private void bindListeners() {
		InventoryWarningHandler.getInstance().setDisplayPanel(queryPanel);
		QueryListener listener = new QueryListener(queryPanel,contentPane);
		listener.setConclusionPane(conclusionPane);
		queryPanel.getQueryBtn().registerKeyboardAction(listener,
				KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),
				JComponent.WHEN_IN_FOCUSED_WINDOW);
		queryPanel.getQueryBtn().addActionListener(listener);

		CleanListener clistener = new CleanListener(contentPane);
		clistener.setConclusionPane(conclusionPane);
		clistener.setQueryPanel(queryPanel);
		queryPanel.getCleanBtn().addActionListener(clistener);
	}

	public ConculsionPanel getConclusionPane() {
		return conclusionPane;
	}

	public ContentPanel getContentPane() {
		return contentPane;
	}

	public void setConclusionPane(ConculsionPanel conclusionPane) {
		this.conclusionPane = conclusionPane;
	}

	public void setContentPane(ContentPanel contentPane) {
		this.contentPane = contentPane;
	}

	public QueryFormPanel getQueryPanel() {
		return queryPanel;
	}

	public void setQueryPanel(QueryFormPanel queryPanel) {
		this.queryPanel = queryPanel;
	}
}
