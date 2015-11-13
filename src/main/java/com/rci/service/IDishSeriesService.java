package com.rci.service;

import com.rci.bean.entity.DishSeries;
import com.rci.service.base.IBaseService;

public interface IDishSeriesService extends IBaseService<DishSeries, Long> {
	public void deleteAll();
}
