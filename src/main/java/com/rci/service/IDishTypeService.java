package com.rci.service;

import java.util.List;

import com.rci.bean.entity.DishType;
import com.rci.service.base.IBaseService;

public interface IDishTypeService extends IBaseService<DishType, Long>{
	/**
	 * 
	 * Describle(描述)：删除所有菜品类型和关联的菜品
	 *
	 * 方法名称：deleteAll
	 *
	 * 所在类名：IDishTypeService
	 *
	 * Create Time:2015年6月23日 上午10:40:41
	 *
	 */
	void deleteAll();
	
	/**
	 * 
	 * Describle(描述)：根据菜品类型编号获取菜品类型
	 *
	 * 方法名称：queryDishTypeByNo
	 *
	 * 所在类名：IDishTypeService
	 *
	 * Create Time:2015年6月23日 上午10:40:58
	 *  
	 * @param no
	 * @return
	 */
	DishType queryDishTypeByNo(String no);
	
	void delete(DishType type);
	
	List<DishType> queryDishTypesBySeriesno(String seriesno);
}
