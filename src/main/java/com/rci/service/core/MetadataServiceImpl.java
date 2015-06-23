package com.rci.service.core;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rci.metadata.service.IDataTransformService;
import com.rci.service.IDishTypeService;
import com.rci.service.IPayModeService;
import com.rci.service.ITableInfoService;

@Service("MetadataService")
public class MetadataServiceImpl implements IMetadataService {
	@Resource(name="DishTypeService")
	private IDishTypeService dishtypeService;
	@Resource(name="PayModeService")
	private IPayModeService paymodeService;
	@Resource(name="TableInfoService")
	private ITableInfoService tableService;
	@Resource(name="DataTransformService")
	private IDataTransformService transformService;
	
	@Override
	public void resetMetadata() {
		clearMetadata();
		/* 获取菜品 */
		transformService.transformDishInfo();
		/* 获取支付方式 */
		transformService.transformPaymodeInfo();
		/* 获取桌号 */
		transformService.transformTableInfo();
	}

	@Override
	public void clearMetadata() {
		/* 清除菜品 */
		dishtypeService.deleteAll();
		
		/* 清除支付方式  */
		paymodeService.deleteAll();
		
		/* 清除桌号  */
		tableService.deleteAll();
	}

}
