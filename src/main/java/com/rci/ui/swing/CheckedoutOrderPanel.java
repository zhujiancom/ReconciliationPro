package com.rci.ui.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.KeyStroke;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.table.TableRowSorter;

import com.rci.enums.BusinessEnums.PaymodeCode;
import com.rci.tools.DateUtil;
import com.rci.ui.swing.handler.InventoryWarningHandler;
import com.rci.ui.swing.listeners.CleanListener;
import com.rci.ui.swing.listeners.QueryListener;
import com.rci.ui.swing.model.OrderTable.OrderTableModel;
import com.rci.ui.swing.views.ConculsionPanel;
import com.rci.ui.swing.views.ContentPanel;
import com.rci.ui.swing.views.QueryFormPanel;
import com.rci.ui.swing.vos.OrderVO;

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
	private QueryListener listener;
	
	public CheckedoutOrderPanel(int width,int height){
		this.width = width;
		this.height = height;
		setName(NAME);
		initComponent();
	}

	private void initComponent() {
		BorderLayout layout = new BorderLayout(0, 0);
		setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, Color.LIGHT_GRAY));
		setLayout(layout);
		/* 绑定查询form */
		SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run() {
				queryPanel.getTimeInput().setText(DateUtil.date2Str(DateUtil.getCurrentDate(), "yyyyMMdd"));
			}
			
		});
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
		listener = new QueryListener(queryPanel,contentPane);
		listener.setConclusionPane(conclusionPane);
		queryPanel.getQueryBtn().registerKeyboardAction(listener,
				KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),
				JComponent.WHEN_IN_FOCUSED_WINDOW);
		queryPanel.getQueryBtn().addActionListener(listener);
		queryPanel.getFilterBtn().addActionListener(new ActionListener() {
			
			@SuppressWarnings("unchecked")
			@Override
			public void actionPerformed(ActionEvent e) {
				final Set<PaymodeCode> paymodes = queryPanel.getPaymodes();
				((TableRowSorter<OrderTableModel>)contentPane.getMainTable().getRowSorter()).setRowFilter(new RowFilter<OrderTableModel, Integer>(){
	
					@Override
					public boolean include(
							javax.swing.RowFilter.Entry<? extends OrderTableModel, ? extends Integer> entry) {
						OrderTableModel model = entry.getModel();
						OrderVO ordervo = model.getOrderAt(entry.getIdentifier());
						String[] paymodecodes = ordervo.getPaymodecodes();
						if(paymodes.contains(PaymodeCode.UNKNOW)){
							return true;
						}else{
							for(String paymodecode:paymodecodes){
								if(paymodes.contains(PaymodeCode.paymodeCode(paymodecode))){
									return true;
								}
							}
						}
						return false;
					}
					
				});
			}
		});

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
