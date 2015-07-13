package com.rci.service.core;

import java.math.BigDecimal;
import java.util.List;

import com.rci.bean.entity.Dish;
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
	Dish getDishByNo(String dishNo);
	
	/**
	 * 
	 * Describle(描述)：库存初始化
	 *
	 * 方法名称：stockInit
	 *
	 * 所在类名：IMetadataService
	 *
	 * Create Time:2015年7月13日 下午3:49:21
	 *  
	 * @param dishNo
	 * @param gross
	 * @param balance
	 */
	void stockInit(String dishNo,BigDecimal gross,BigDecimal balance);
	
	List<StockVO> displayStocks();
}
