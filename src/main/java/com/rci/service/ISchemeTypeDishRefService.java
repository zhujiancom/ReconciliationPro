package com.rci.service;

import java.util.List;

import com.rci.bean.entity.SchemeType;
import com.rci.bean.entity.SchemeTypeDishRef;
import com.rci.service.base.IBaseService;
import com.rci.ui.swing.vos.DishVO;

public interface ISchemeTypeDishRefService extends IBaseService<SchemeTypeDishRef, Long> {
	void rwDeleteRefByTypeno(String no);
	
	List<DishVO> queryDishesByTypeno(String typeno);
	
	SchemeType querySchemeTypeByDishno(String dishno);
	
}
