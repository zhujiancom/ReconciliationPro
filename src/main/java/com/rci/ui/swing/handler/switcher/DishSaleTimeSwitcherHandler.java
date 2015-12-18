package com.rci.ui.swing.handler.switcher;

import java.awt.Color;
import java.awt.Font;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import com.rci.ui.swing.model.DishStatisticTable;
import com.rci.ui.swing.vos.DishStatisticVO;

public class DishSaleTimeSwitcherHandler extends TimeSwitcherHandler {
	private ChartPanel chartPanel;
	private JScrollPane scrollPanel;
	
	public DishSaleTimeSwitcherHandler(){
		super();
	}

	@Override
	public void doQueryAction(Date sdate, Date edate) {
		List<DishStatisticVO> dishStatisticInfoList = statisticService.getDishSaleStatisticInfo(sdate, edate);
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
		generateTable(dishStatisticInfoList);
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				displayPanel.removeAll();
				displayPanel.add(chartPanel);
				displayPanel.add(scrollPanel);
				displayPanel.updateUI();
			}
		});
	}
	
	/**
	 * 
	 * Describle(描述)： 生成饼图
	 *
	 * 方法名称：generatePie
	 *
	 * 所在类名：TimeSwitcherHandler
	 *
	 * Create Time:2015年12月9日 上午10:26:20
	 *  
	 * @param dataset
	 */
	public void generatePie(DefaultPieDataset dataset){
		StandardChartTheme standardChartTheme = new StandardChartTheme("CN");
		// 设置标题字体
//		standardChartTheme.setExtraLargeFont(new Font("隶书", Font.BOLD, 20));
		// 设置图例的字体
//		standardChartTheme.setRegularFont(new Font("宋书", Font.PLAIN, 15));
		// 设置轴向的字体
//		standardChartTheme.setLargeFont(new Font("宋书", Font.PLAIN, 15));

		JFreeChart chart = ChartFactory.createPieChart("", dataset, false, true,false);

		ChartFactory.setChartTheme(standardChartTheme);

//		TextTitle textTitle = chart.getTitle();
//		textTitle.setFont(new Font("宋体", Font.BOLD, 20));

//		LegendTitle legend = chart.getLegend();
//		if (legend != null) {
//			legend.setItemFont(new Font("宋体", Font.BOLD, 20));
//		}
		PiePlot plot = (PiePlot) chart.getPlot();
		plot.setLabelFont(new Font("雅黑", Font.BOLD, 12));
		plot.setNoDataMessage("没有数据");
		plot.setBackgroundPaint(Color.WHITE);
		plot.setNoDataMessageFont(new Font("宋体", Font.BOLD, 20));
		plot.setOutlinePaint(Color.WHITE);
		plot.setShadowPaint(Color.WHITE);
		chartPanel = new ChartPanel(chart);
	}
	

	/**
	 * 
	 * Describle(描述)：生成表格
	 *
	 * 方法名称：generateTable
	 *
	 * 所在类名：TimeSwitcherHandler
	 *
	 * Create Time:2015年12月9日 上午10:26:01
	 *  
	 * @param dishStatisticInfoList
	 */
	public void generateTable(List<DishStatisticVO> dishStatisticInfoList){
		scrollPanel = new JScrollPane();
		DishStatisticTable table = new DishStatisticTable(5);
		scrollPanel.setViewportView(table);
		scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPanel.getViewport().setBackground(Color.WHITE);
		table.reflushTable(dishStatisticInfoList);
	}
}
