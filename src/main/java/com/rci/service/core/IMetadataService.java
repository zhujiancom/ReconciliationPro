package com.rci.service.core;


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
}
