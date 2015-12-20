package com.rci.ui.swing.handler.switcher;

import java.awt.Color;
import java.awt.Font;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.TextAnchor;

import com.rci.tools.DateUtil;
import com.rci.ui.swing.model.ExpressRateTabel;
import com.rci.ui.swing.vos.ExpressRateVO;

public class ExpressRateTimeSwitcherHandler extends TimeSwitcherHandler {
	private ChartPanel chartPanel;
	private JScrollPane scrollPanel;
	
	@Override
	public void doQueryAction(Date sdate, Date edate) {
		List<ExpressRateVO> rates = statisticService.getExpressRateList(sdate,edate);
		List<ExpressRateVO> dest = Arrays.asList(new ExpressRateVO[rates.size()]);
		Collections.copy(dest, rates);
		Collections.reverse(rates);
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for(ExpressRateVO rate:rates){
			dataset.addValue(rate.getExpressRate(), "rate", DateUtil.date2Str(rate.getDate()));
		}
		generateLineChart(dataset);
		generateTable(dest);
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
	
	public void generateLineChart(DefaultCategoryDataset dataset){
		JFreeChart chart = ChartFactory.createLineChart(
	            "外送率统计",   // chart title
	            "日期",                       // domain axis label
	            "外送率",                   // range axis label
	            dataset,                         // data
	            PlotOrientation.VERTICAL,        // orientation
	            false,                           // include legend
	            true,                            // include tooltips
	            false                            // urls?
	        );
		chart.getTitle().setFont(new Font("雅黑",Font.BOLD,15)); 
		CategoryPlot plot = (CategoryPlot) chart.getPlot();
		plot.setBackgroundPaint(Color.WHITE);
		plot.setRangeGridlinesVisible(true);
		plot.setRangeGridlinePaint(Color.gray);
		plot.setNoDataMessage("没有数据");
		plot.setNoDataMessageFont(new Font("宋体", Font.BOLD, 20));
		CategoryAxis domainAxis = plot.getDomainAxis();
		domainAxis.setLabelFont(new Font("雅黑",Font.BOLD,12));
		domainAxis.setMaximumCategoryLabelWidthRatio(6);
		ValueAxis rangeAxis = plot.getRangeAxis();
		rangeAxis.setLabelFont(new Font("雅黑",Font.BOLD,12));
		 // 设置最高的一个 Item 与图片顶端的距离  
		rangeAxis.setUpperMargin(0.15);  
		// 设置最低的一个 Item 与图片底端的距离  
        rangeAxis.setLowerMargin(0.15); 
        
		LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
		renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());                 
		renderer.setBaseItemLabelsVisible(true);                 
		renderer.setBaseItemLabelFont(new Font("黑体",Font.BOLD,12));
		renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12,TextAnchor.BOTTOM_CENTER));
		renderer.setItemMargin(0.3);
		
		chartPanel = new ChartPanel(chart);
	}
	
	public void generateTable(List<ExpressRateVO> rates){
		scrollPanel = new JScrollPane();
		ExpressRateTabel table = new ExpressRateTabel(3);
		scrollPanel.setViewportView(table);
		scrollPanel.setBorder(BorderFactory.createEmptyBorder());
		scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPanel.getViewport().setBackground(Color.WHITE);
		table.reflushTable(rates);
	}
}
