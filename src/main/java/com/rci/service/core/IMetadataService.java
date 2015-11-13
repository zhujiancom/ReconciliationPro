package com.rci.service.core;

import java.util.List;

import com.rci.bean.dto.SchemeQueryDTO;
import com.rci.bean.dto.SchemeTypeQueryDTO;
import com.rci.ui.swing.vos.DishVO;
import com.rci.ui.swing.vos.SchemeTypeVO;
import com.rci.ui.swing.vos.SchemeVO;
import com.rci.ui.swing.vos.StockVO;


/**
 * 
 * remark (备注): 基础数据操作
 *
 * @author zj
 *	
 * 项目名称：ReconciliationPro
 *
 * 类名称：MetadataService
 *
 * 包名称：com.rci.service.core
 *
 * Create Time: 2015年6月23日 上午9:03:08
 *
 */
public interface IMetadataService {
	/**
	 * 
	 * Describle(描述)： 重置基础数据
	 *
	 * 方法名称：resetMetadata
	 *
	 * 所在类名：MetadataService
	 *
	 * Create Time:2015年6月23日 上午9:06:35
	 *
	 */
	void resetMetadata();
	
	/**
	 * 
	 * Describle(描述)：清空基础数据
	 *
	 * 方法名称：clearMetadata
	 *
	 * 所在类名：MetadataService
	 *
	 * Create Time:2015年6月23日 上午9:06:48
	 *
	 */
	void clearMetadata();

	/**
	 * 
	 * Describle(描述)： 查找dish
	 *
	 * 方法名称：getDishByNo
	 *
	 * 所在类名：IMetadataService
	 *
	 * Create Time:2015年7月13日 下午4:32:55
	 *  
	 * @param dishNo
	 * @return
	 */
//	Dish getDishByNo(String dishNo);
	
	List<StockVO> displayStocks();
	
	String getTimerStatus();
	
	/**
	 * 
	 * Describle(描述)：列车所有的活动
	 *
	 * 方法名称：dishplaySchemes
	 *
	 * 所在类名：IMetadataService
	 *
	 * Create Time:2015年9月29日 下午2:13:27
	 *  
	 * @return
	 */
	List<SchemeVO> displaySchemes(SchemeQueryDTO queryDTO);
	
	void createScheme(SchemeVO schemevo);
	void updateScheme(SchemeVO schemevo);
	void activeScheme(Long sid);
	void inactiveScheme(Long sid);
	
	List<SchemeTypeVO> displaySchemeTypes(SchemeTypeQueryDTO queryDTO);
	
	/**
	 * 
	 * Describle(描述)：获取套餐菜品
	 *
	 * 方法名称：displayDishSuits
	 *
	 * 所在类名：IMetadataService
	 *
	 * Create Time:2015年11月12日 下午4:26:08
	 *  
	 * @return
	 */
	List<DishVO> displayDishSuits();
	
	void createSchemeType(SchemeTypeVO schemeTypevo);
	void updateSchemeType(SchemeTypeVO schemeTypevo);
	void activeSchemeType(Long stid);
	void inactiveSchemeType(Long stid);
	
	DishVO getDishByNo(String dishno);
	
}
