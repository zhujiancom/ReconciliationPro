package com.rci;

import java.awt.Font;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

public class TestEntry {

	public static void main(String[] args) {
		DefaultPieDataset dpd = new DefaultPieDataset();
		dpd.setValue("管理人员", 25);  //输入数据       
		dpd.setValue("市场人员", 25);        
		dpd.setValue("开发人员", 45);        
		dpd.setValue("其他人员", 10);
		
		DefaultCategoryDataset dataset = new DefaultCategoryDataset(); 
		dataset.addValue(100, "北京", "苹果");  
		   
	       dataset.addValue(100, "上海", "苹果");  
	   
	       dataset.addValue(100, "广州", "苹果");  
	   
	       dataset.addValue(200, "北京", "梨子");  
	   
	       dataset.addValue(200, "上海", "梨子");  
	   
	       dataset.addValue(200, "广州", "梨子");  
	   
	       dataset.addValue(300, "北京", "葡萄");  
	   
	       dataset.addValue(300, "上海", "葡萄");  
		
		StandardChartTheme standardChartTheme=new StandardChartTheme("CN");
		//设置标题字体  
	   standardChartTheme.setExtraLargeFont(new Font("隶书",Font.BOLD,20));  
	   //设置图例的字体  
	   standardChartTheme.setRegularFont(new Font("宋书",Font.PLAIN,15));  
	   //设置轴向的字体  
	   standardChartTheme.setLargeFont(new Font("宋书",Font.PLAIN,15));
	   
	   JFreeChart chart=ChartFactory.createPieChart("",dpd,true,true,false); 
//	   JFreeChart chart = ChartFactory.createBarChart3D("某公司人员组织数据图", "水果", "水果", dataset);
	 //应用主题样式  
	   ChartFactory.setChartTheme(standardChartTheme); 
	   
	   TextTitle textTitle = chart.getTitle();   
	   textTitle.setFont(new Font("宋体",Font.BOLD,20)); 
	   
	   LegendTitle legend = chart.getLegend();   
	   if (legend!=null) {   
	   legend.setItemFont(new Font("宋体", Font.BOLD, 20));   
	   } 
	   PiePlot plot = (PiePlot)chart.getPlot();   
	   plot.setLabelFont(new Font("宋体",Font.BOLD,15));  
	   
		ChartFrame chartFrame = new  ChartFrame("某公司人员组织数据图",chart);
		chartFrame.pack(); //以合适的大小展现图形       
		chartFrame.setVisible(true);//图形是否可见
	}

}
