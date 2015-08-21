package com.rci.service.core;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.rci.bean.entity.Dish;
import com.rci.bean.entity.Stock;
import com.rci.metadata.service.IDataTransformService;
import com.rci.service.IDishService;
import com.rci.service.IDishTypeService;
import com.rci.service.IPayModeService;
import com.rci.service.IStockService;
import com.rci.service.ITableInfoService;
import com.rci.ui.swing.vos.StockVO;

@Service("MetadataService")
public class MetadataServiceImpl implements IMetadataService {
	@Resource(name="DishTypeService")
	private IDishTypeService dishtypeService;
	@Resource(name="DishService")
	private IDishService dishService;
	@Resource(name="PayModeService")
	private IPayModeService paymodeService;
	@Resource(name="TableInfoService")
	private ITableInfoService tableService;
	@Resource(name="DataTransformService")
	private IDataTransformService transformService;
	@Resource(name="StockService")
	private IStockService stockService;
	
	@Autowired
	private Mapper beanMapper;
	
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

	@Override
	public List<StockVO> displayStocks() {
		List<Stock> stocks = stockService.getAll();
		if(!CollectionUtils.isEmpty(stocks)){
			List<StockVO> stockVOs = new ArrayList<StockVO>();
			for(Stock stock:stocks){
				if(stock.getLevel() == 1){
					StockVO vo = beanMapper.map(stock, StockVO.class);
					stockVOs.add(vo);
				}
			}
			return stockVOs;
		}
		return null;
	}

	@Override
	public Dish getDishByNo(String dishNo) {
		return dishService.findDishByNo(dishNo);
	}

}
