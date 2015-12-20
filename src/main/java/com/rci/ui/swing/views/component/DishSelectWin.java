package com.rci.ui.swing.views.component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.rci.service.core.IMetadataService;
import com.rci.tools.SpringUtils;
import com.rci.ui.swing.listeners.DishSelectListener;
import com.rci.ui.swing.model.ButtonFactory;
import com.rci.ui.swing.views.PopWindow;
import com.rci.ui.swing.views.component.slidebar.SeriesChooseSlideBarHandler;
import com.rci.ui.swing.views.component.slidebar.SlideBar;
import com.rci.ui.swing.views.component.slidebar.SlideElement;
import com.rci.ui.swing.views.component.slidebar.SlideElement.State;
import com.rci.ui.swing.vos.DishSeriesVO;

public class DishSelectWin extends PopWindow {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2248904392389671255L;
	
	private DishSelectListener selectListener;
	
	private IMetadataService metaService;
	
	private SeriesChooseSlideBarHandler handler;
	
	public DishSelectWin(DishSelectListener selectListener,int width,int height,String title){
		super(width,height,title);
		this.selectListener = selectListener;
		metaService = (IMetadataService) SpringUtils.getBean("MetadataService");
		createContentPane();
	}

	private void createContentPane() {
		JPanel containerPanel = this.getContainerPanel();
		containerPanel.setBackground(Color.WHITE);
		add(createTableSlideBar(),BorderLayout.NORTH);
		containerPanel.add(buildOperatePane(),BorderLayout.SOUTH);
	}
	
	private JPanel createTableSlideBar() {
		List<DishSeriesVO> seriesList = metaService.getAllDishSeries();
		List<SlideElement> elements = new ArrayList<SlideElement>();
		handler = new SeriesChooseSlideBarHandler();
		for(int i=0;i<seriesList.size();i++){
			DishSeriesVO series = seriesList.get(i);
			if(i == 0){
				SlideElement element = new SlideElement(series.getSeriesname(),series,State.SELECTED);
				element.setIndex(i);
				element.addActionListener(handler);
				elements.add(element);
				DishChooserPanel contentPanel = new DishChooserPanel(selectListener,element);
				handler.setContentPanel(contentPanel);
				add(contentPanel,BorderLayout.CENTER);
			}else{
				SlideElement element = new SlideElement(series.getSeriesname(),series);
				element.setIndex(i);
				element.addActionListener(handler);
				elements.add(element);
			}
		}
		return new SlideBar(elements);
	}

	private JPanel buildOperatePane(){
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER,20,0));
		panel.setBackground(Color.WHITE);
		JButton confirmBtn = ButtonFactory.createImageButton("确定","skin/gray/images/btn_green.png",null);
		JButton cancelBtn = ButtonFactory.createImageButton("取消","skin/gray/images/btn_purple.png",null);
		panel.add(confirmBtn);
		panel.add(cancelBtn);
		confirmBtn.addActionListener(selectListener);
		return panel;
	}

	public DishSelectListener getSelectListener() {
		return selectListener;
	}

	public void setSelectListener(DishSelectListener selectListener) {
		this.selectListener = selectListener;
	}

}
