package com.rci.ui.swing.handler;

import java.awt.Color;
import java.awt.Font;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryAxis3D;
import org.jfree.chart.axis.NumberAxis3D;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.TextAnchor;

import com.rci.ui.swing.model.TurnoverTable;
import com.rci.ui.swing.vos.TurnoverStatisticVO;
import com.rci.ui.swing.vos.TurnoverVO;

public class TurnoverTimeSwitcherHandler extends TimeSwitcherHandler {
	private ChartPanel chartPanel;
	private JScrollPane scrollPanel;
	
	public TurnoverTimeSwitcherHandler(){
		super();
	}
	
	@Override
	public void doQueryAction(Date sdate, Date edate) {
		List<TurnoverVO> turnovers = statisticService.getTurnoverList(sdate,edate);
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		List<TurnoverStatisticVO> statisticInfos =  statisticService.getTurnoverStatisticInfo(sdate, edate);
		for(TurnoverStatisticVO info:statisticInfos){
			dataset.setValue(info.getAmount(), info.getAccountName(),"");
		}
		generateBarChart(dataset);
//		generateBarChart3D(dataset);
		generateTable(turnovers);
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
	 * Describle(描述)：创建3D柱状图
	 *
	 * 方法名称：generateBarChart3D
	 *
	 * 所在类名：TurnoverTimeSwitcherHandler
	 *
	 * Create Time:2015年12月10日 上午11:07:18
	 *  
	 * @param dataset
	 */
	public void generateBarChart3D(DefaultCategoryDataset dataset){
		JFreeChart chart = ChartFactory.createBarChart3D("营业额统计表","收银方式", "金额", dataset,PlotOrientation.VERTICAL,false,true,false);
		//处理标题乱码
		chart.getTitle().setFont(new Font("雅黑",Font.BOLD,15)); 
		CategoryPlot plot = (CategoryPlot)chart.getPlot(); 
		plot.setNoDataMessage("没有数据");
		plot.setNoDataMessageFont(new Font("宋体", Font.BOLD, 20));
		plot.setBackgroundPaint(Color.WHITE);
		//设置横线虚线可见
		plot.setRangeGridlinesVisible(true);
		plot.setRangeGridlinePaint(Color.gray);
		 //获取X轴的对象                
		CategoryAxis3D categoryAxis3D = (CategoryAxis3D)plot.getDomainAxis();
//		categoryAxis3D.setMaximumCategoryLabelWidthRatio(10f); //横轴上的 Lable是否完整显示 
		categoryAxis3D.setLowerMargin(0.02); //设置距离图片左端距离 
		categoryAxis3D.setUpperMargin(0.02);  //设置距离图片右端距离
		//处理X轴上的乱码                 
		categoryAxis3D.setTickLabelFont(new Font("雅黑",Font.BOLD,12)); 
		//处理X轴外的乱码                 
		categoryAxis3D.setLabelFont(new Font("雅黑",Font.BOLD,12));
		plot.setDomainAxis(categoryAxis3D);
		//获取Y轴的对象                
		NumberAxis3D numberAxis3D = (NumberAxis3D)plot.getRangeAxis(); 
//		numberAxis3D.setNumberFormatOverride(new DecimalFormat("#0.00"));
		//处理Y轴上的乱码                
		numberAxis3D.setTickLabelFont(new Font("雅黑",Font.BOLD,12));                 
		//处理Y轴外的乱码                 
		numberAxis3D.setLabelFont(new Font("雅黑",Font.BOLD,12)); 
		//处理Y轴上显示的刻度，以100作为1格                 
//		numberAxis3D.setAutoTickUnitSelection(false);                 
//		NumberTickUnit unit = new NumberTickUnit(3000);                 
//		numberAxis3D.setTickUnit(unit); 
		//获取绘图区域对象                
		BarRenderer3D barRenderer3D = (BarRenderer3D)plot.getRenderer();                 
		//设置柱形图的宽度                
		barRenderer3D.setMaximumBarWidth(0.07); 
		//在图形上显示数字                 
		barRenderer3D.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());                 
		barRenderer3D.setBaseItemLabelsVisible(true);                 
		barRenderer3D.setBaseItemLabelFont(new Font("黑体",Font.BOLD,12));
		barRenderer3D.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12,TextAnchor.BOTTOM_CENTER));
		barRenderer3D.setItemMargin(0.3);
		
		chartPanel = new ChartPanel(chart);
	}
	
	/**
	 * 
	 * Describle(描述)： 创建2D柱状图
	 *
	 * 方法名称：generateBarChart
	 *
	 * 所在类名：TurnoverTimeSwitcherHandler
	 *
	 * Create Time:2015年12月10日 上午11:07:37
	 *  
	 * @param dataset
	 */
	public void generateBarChart(DefaultCategoryDataset dataset){
		JFreeChart chart = ChartFactory.createBarChart("营业额统计表","收银方式", "金额", dataset,PlotOrientation.VERTICAL,false,true,false);
		//处理标题乱码
		chart.getTitle().setFont(new Font("雅黑",Font.BOLD,15)); 
		CategoryPlot plot = (CategoryPlot)chart.getPlot(); 
		plot.setNoDataMessage("没有数据");
		plot.setNoDataMessageFont(new Font("宋体", Font.BOLD, 20));
		plot.setBackgroundPaint(Color.WHITE);
		//设置横线虚线可见
		plot.setRangeGridlinesVisible(true);
		//虚线色差彩
		plot.setRangeGridlinePaint(Color.gray); 
		CategoryAxis domainAxis = plot.getDomainAxis();
		domainAxis.setLowerMargin(0.02);
		domainAxis.setUpperMargin(0.02);
		domainAxis.setTickLabelFont(new Font("雅黑",Font.BOLD,12));
		domainAxis.setLabelFont(new Font("雅黑",Font.BOLD,12));
		plot.setDomainAxis(domainAxis);
		ValueAxis rangeAxis = plot.getRangeAxis();
		rangeAxis.setTickLabelFont(new Font("雅黑",Font.BOLD,12));
		rangeAxis.setLabelFont(new Font("雅黑",Font.BOLD,12));
		 // 设置最高的一个 Item 与图片顶端的距离  
		rangeAxis.setUpperMargin(0.15);  
		// 设置最低的一个 Item 与图片底端的距离  
        rangeAxis.setLowerMargin(0.15);  
        plot.setRangeAxis(rangeAxis);  
        
		BarRenderer renderer = (BarRenderer) plot.getRenderer();
		renderer.setMaximumBarWidth(0.07);
		renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());                 
		renderer.setBaseItemLabelsVisible(true);                 
		renderer.setBaseItemLabelFont(new Font("黑体",Font.BOLD,12));
		renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12,TextAnchor.BOTTOM_CENTER));
		renderer.setItemMargin(0.3);
		renderer.setDrawBarOutline(true);  // 设置柱子边框肯见
		// 设置柱子边框颜色  
        renderer.setBaseOutlinePaint(Color.LIGHT_GRAY);
        
		chartPanel = new ChartPanel(chart);
	}
	public void generateTable(List<TurnoverVO> turnovers){
		scrollPanel = new JScrollPane();
		TurnoverTable table = new TurnoverTable(20);
//		table.makeStatisticRowFace();
		scrollPanel.setViewportView(table);
		scrollPanel.setBorder(BorderFactory.createEmptyBorder());
		scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPanel.getViewport().setBackground(Color.WHITE);
		table.reflushTable(turnovers);
	}

}
