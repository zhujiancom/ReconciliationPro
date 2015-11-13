package com.rci.service.impl;

import java.util.List;

import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;

import com.rci.bean.entity.DishSeries;
import com.rci.service.IDishSeriesService;
import com.rci.service.base.BaseServiceImpl;

@Service("DishSeriesService")
public class DishSeriesServiceImpl extends BaseServiceImpl<DishSeries, Long> implements
		IDishSeriesService {

	@Override
	public void deleteAll() {
		List<DishSeries> seriesList = getAll();
		for(DishSeries series:seriesList){
			((IDishSeriesService)AopContext.currentProxy()).rwDelete(series.getDsid());
		}
	}

}
