package com.rci.ui.swing.handler;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Date;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.general.DefaultPieDataset;

import com.rci.service.core.StatisticCenterFacade;
import com.rci.tools.DateUtil;
import com.rci.tools.SpringUtils;
import com.rci.ui.swing.views.component.switcher.SwitcherElement;
import com.rci.ui.swing.vos.DishStatisticVO;

public class TimeSwitcherHandler extends AbstractSwitcherHandler {
	private JPanel displayPanel;
	private Icon loadingIcon;
	private StatisticCenterFacade statisticService;
	private ChartPanel chartPanel;
	
	public TimeSwitcherHandler(){
		URL loadingIconUrl = this.getClass().getClassLoader().getResource("skin/gray/images/24x24/loading.gif");
		loadingIcon = new ImageIcon(loadingIconUrl);
		statisticService = (StatisticCenterFacade) SpringUtils.getBean("StatisticCenterFacade");
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		selectedElement = (SwitcherElement) event.getSource();
		new Thread(new Runnable(){

			@Override
			public void run() {
				refreshUI();
				String actioncommand = selectedElement.getActionCommand();
				int amount = Integer.valueOf(actioncommand);
				Date edate = DateUtil.getStartTimeOfDay(DateUtil.getCurrentDate());
				Date sdate = DateUtil.addDays(edate, amount);
				SwingUtilities.invokeLater(new Runnable() {
					
					@Override
					public void run() {
						displayPanel.removeAll();
						displayPanel.add(new JLabel("Loading",loadingIcon,SwingConstants.CENTER));
						displayPanel.updateUI();
					}
				});
				List<DishStatisticVO> dishStatisticInfoList = statisticService.getDishSaleStatisticInfo(sdate, edate);
//				BigDecimal saleroom = statisticService.getSaleroom(sdate, edate);
				int index = 0;
				DefaultPieDataset dataset = new DefaultPieDataset();
				BigDecimal otherAmount = BigDecimal.ZERO;
				for(DishStatisticVO info:dishStatisticInfoList){
					if(index < 10){
						dataset.setValue(info.getDishName(), info.getSaleAmount());
						index++;
					}else{
						otherAmount = otherAmount.add(info.getSaleAmount());
					}
				}
				dataset.setValue("其他", otherAmount);
				generatePie(dataset);
				
				SwingUtilities.invokeLater(new Runnable() {
					
					@Override
					public void run() {
						displayPanel.removeAll();
						displayPanel.add(chartPanel);
						displayPanel.updateUI();
					}
				});
			}
			
		}).start();
	}

	public JPanel getDisplayPanel() {
		return displayPanel;
	}

	public void setDisplayPanel(JPanel displayPanel) {
		this.displayPanel = displayPanel;
	}
	
	public void generatePie(DefaultPieDataset dataset){
		StandardChartTheme standardChartTheme = new StandardChartTheme("CN");
		// 设置标题字体
		standardChartTheme.setExtraLargeFont(new Font("隶书", Font.BOLD, 20));
		// 设置图例的字体
		standardChartTheme.setRegularFont(new Font("宋书", Font.PLAIN, 15));
		// 设置轴向的字体
		standardChartTheme.setLargeFont(new Font("宋书", Font.PLAIN, 15));

		JFreeChart chart = ChartFactory.createPieChart("", dataset, true, true,
				false);

		ChartFactory.setChartTheme(standardChartTheme);

		TextTitle textTitle = chart.getTitle();
		textTitle.setFont(new Font("宋体", Font.BOLD, 20));

		LegendTitle legend = chart.getLegend();
		if (legend != null) {
			legend.setItemFont(new Font("宋体", Font.BOLD, 20));
		}
		PiePlot plot = (PiePlot) chart.getPlot();
		plot.setLabelFont(new Font("宋体", Font.BOLD, 15));
		chartPanel = new ChartPanel(chart);
	}
	
}
