package com.rci.ui.swing.handler;

import java.awt.Color;
import java.awt.Font;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis3D;
import org.jfree.chart.axis.NumberAxis3D;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.data.category.DefaultCategoryDataset;

import com.rci.ui.swing.model.TurnoverTable;
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
		TurnoverVO sum = statisticService.getTurnoverSum(sdate, edate);
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		if(sum != null){
			dataset.setValue(sum.getCashMachineAmount(), "收银机", "现金收入");
			dataset.setValue(-1000, "pos机", "银联支付");
			dataset.setValue(sum.getAliPayAmount(), "支付宝","支付宝");
			dataset.setValue(sum.getDptgAmount(), "大众点评", "大众点评团购");
			dataset.setValue(sum.getDpshAmount(), "大众点评", "大众点评闪惠");
			dataset.setValue(sum.getMtAmount(), "美团", "美团团购");
			dataset.setValue(sum.getMtSuperAmount(), "美团", "美团超券");
			dataset.setValue(sum.getDptgAmount(), "大众点评", "大众点评团购");
			dataset.setValue(sum.getDptgAmount(), "大众点评", "大众点评团购");
			dataset.setValue(sum.getDptgAmount(), "大众点评", "大众点评团购");
			dataset.setValue(sum.getDptgAmount(), "大众点评", "大众点评团购");
			dataset.setValue(sum.getDptgAmount(), "大众点评", "大众点评团购");
			dataset.setValue(sum.getDptgAmount(), "大众点评", "大众点评团购");
		}
//		generateBarChart(dataset);
		generateBarChart3D(dataset);
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
		//处理X轴上的乱码                 
		categoryAxis3D.setTickLabelFont(new Font("雅黑",Font.BOLD,12)); 
		//处理X轴外的乱码                 
		categoryAxis3D.setLabelFont(new Font("雅黑",Font.BOLD,12)); 
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
		barRenderer3D.setBaseItemLabelFont(new Font("宋体",Font.BOLD,15)); 
		
		chartPanel = new ChartPanel(chart);
	}
	
	public void generateBarChart(DefaultCategoryDataset dataset){
		JFreeChart chart = ChartFactory.createBarChart("营业额统计表","收银方式", "金额", dataset,PlotOrientation.VERTICAL,false,true,false);
		//处理标题乱码
		chart.getTitle().setFont(new Font("雅黑",Font.BOLD,15)); 
		chart.getTitle().setFont(new Font("雅黑",Font.BOLD,15)); 
		CategoryPlot plot = (CategoryPlot)chart.getPlot(); 
		plot.setNoDataMessage("没有数据");
		plot.setNoDataMessageFont(new Font("宋体", Font.BOLD, 20));
		plot.setBackgroundPaint(Color.WHITE);
		//设置横线虚线可见
		plot.setRangeGridlinesVisible(true);
		//虚线色差彩
		plot.setRangeGridlinePaint(Color.gray); 
		
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
