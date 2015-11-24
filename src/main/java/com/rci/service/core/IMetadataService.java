package com.rci.service.core;

import java.math.BigDecimal;
import java.util.List;

import com.rci.bean.dto.SchemeQueryDTO;
import com.rci.bean.dto.SchemeTypeQueryDTO;
import com.rci.ui.swing.vos.DishSeriesVO;
import com.rci.ui.swing.vos.DishTypeVO;
import com.rci.ui.swing.vos.DishVO;
import com.rci.ui.swing.vos.InventoryVO;
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
	///////////////////////////////////////////// 基础数据操作模块   /////////////////////////////////////////////
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

	///////////////////////////////////////////// Scheme  活动管理模块     /////////////////////////////////////////////
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
	
	/////////////////////////////////////////////  SchemeType 活动类型管理模块   /////////////////////////////////////////////
	List<SchemeTypeVO> displaySchemeTypes(SchemeTypeQueryDTO queryDTO);
	void createSchemeType(SchemeTypeVO schemeTypevo);
	void updateSchemeType(SchemeTypeVO schemeTypevo);
	void activeSchemeType(Long stid);
	void inactiveSchemeType(Long stid);
	
	
	/////////////////////////////////////////////  Dish  菜品管理模块      /////////////////////////////////////////////
	DishVO getDishByNo(String dishno);
	
	List<DishSeriesVO> getAllDishSeries();
	
	List<DishTypeVO> getAllDishTypeBySeriesNo(String seriesno);
	
	List<DishVO> getAllDishByTypeNo(String typeno);
	
	///////////////////////////////////////////////  Inventory  库存管理模块   /////////////////////////////////////////////
	@Deprecated
	List<StockVO> displayStocks();
	
	List<InventoryVO> displayAllInventory();
	List<InventoryVO> queryInventory(String iname);
	
	boolean checkInventoryNoExist(String ino);
	
	void createInventory(InventoryVO inventoryvo);
	void updateInventory(InventoryVO inventoryvo);
	//停用库存产品
	void disableInventory(Long iid);
	void purchaseInventory(InventoryVO inventoryvo,BigDecimal purchaseAmount);
	
}
