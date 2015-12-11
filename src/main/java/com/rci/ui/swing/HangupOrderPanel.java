package com.rci.ui.swing;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import com.rci.service.core.IMetadataService;
import com.rci.tools.SpringUtils;
import com.rci.ui.swing.views.component.slidebar.SlideBar;
import com.rci.ui.swing.views.component.slidebar.SlideElement;
import com.rci.ui.swing.vos.HangupTabelInfoVO;

public class HangupOrderPanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1960658657988367726L;

	private IMetadataService metaService;
	
	public HangupOrderPanel(){
		metaService = (IMetadataService) SpringUtils.getBean("MetadataService");
		initComponent();
	}
	
	private void initComponent(){
		setLayout(new BorderLayout(0, 0));
		add(createTableSlideBar(),BorderLayout.NORTH);
	}

	private JPanel createTableSlideBar() {
		List<HangupTabelInfoVO> tables = metaService.getHangupTablesInfo();
		List<SlideElement<HangupTabelInfoVO>> elements = new ArrayList<SlideElement<HangupTabelInfoVO>>();
		for(int i=0;i<tables.size();i++){
			HangupTabelInfoVO table = tables.get(i);
			if(i == 0){
				SlideElement<HangupTabelInfoVO> element = new SlideElement<HangupTabelInfoVO>(table.getTableName(),table,true);
				element.setIndex(i+1);
				elements.add(element);
			}else{
				SlideElement<HangupTabelInfoVO> element =new SlideElement<HangupTabelInfoVO>(table.getTableName(),table);
				element.setIndex(i+1);
				elements.add(element);
			}
		}
		return new SlideBar<HangupTabelInfoVO>(elements);
	}
	
	
}
