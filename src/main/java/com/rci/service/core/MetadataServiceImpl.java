package com.rci.service.core;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.rci.bean.dto.SchemeQueryDTO;
import com.rci.bean.dto.SchemeTypeQueryDTO;
import com.rci.bean.entity.Dish;
import com.rci.bean.entity.Scheme;
import com.rci.bean.entity.SchemeType;
import com.rci.bean.entity.Stock;
import com.rci.enums.BusinessEnums.ActivityStatus;
import com.rci.metadata.service.IDataTransformService;
import com.rci.service.IDishSeriesService;
import com.rci.service.IDishService;
import com.rci.service.IDishTypeService;
import com.rci.service.IPayModeService;
import com.rci.service.ISchemeService;
import com.rci.service.ISchemeTypeService;
import com.rci.service.IStockService;
import com.rci.service.ITableInfoService;
import com.rci.tools.EnumUtils;
import com.rci.tools.StringUtils;
import com.rci.ui.swing.vos.DishVO;
import com.rci.ui.swing.vos.SchemeTypeVO;
import com.rci.ui.swing.vos.SchemeVO;
import com.rci.ui.swing.vos.StockVO;

@Service("MetadataService")
public class MetadataServiceImpl implements IMetadataService {
	@Resource(name="DishTypeService")
	private IDishTypeService dishtypeService;
	@Resource(name="DishSeriesService")
	private IDishSeriesService dishseriesService;
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
	@Resource(name="SchemeService")
	private ISchemeService schemeService;
	@Resource(name="SchemeTypeService")
	private ISchemeTypeService schemeTypeService;
//	@Resource(name = "fetchScheduler")
//	private Scheduler scheduler;
	
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
		dishseriesService.deleteAll();
		
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
	public String getTimerStatus() {
//		try {
//			if(scheduler.isStarted() && !scheduler.isInStandbyMode()){
//				return "open";
//			}
//		} catch (SchedulerException e) {
//			e.printStackTrace();
//		}
		return "closed";
	}

	@Override
	public List<SchemeVO> displaySchemes(SchemeQueryDTO queryDTO) {
		List<SchemeVO> schemeVOs = new ArrayList<SchemeVO>();
		List<Scheme> schemes = schemeService.getSchemes(queryDTO);
		if(!CollectionUtils.isEmpty(schemes)){
			for(Scheme scheme:schemes){
				SchemeVO schemeVO = beanMapper.map(scheme, SchemeVO.class);
				schemeVO.setDishplayVendor(EnumUtils.getEnumMessage(scheme.getVendor()));
				schemeVO.setStatus(EnumUtils.getEnumMessage(scheme.getActivityStatus()));
				schemeVOs.add(schemeVO);
			}
		}
		return schemeVOs;
	}

	@Override
	public void createScheme(SchemeVO schemevo) {
		Scheme scheme = beanMapper.map(schemevo, Scheme.class);
		scheme.setActivityStatus(ActivityStatus.ACTIVE);
		schemeService.rwCreate(scheme);
	}
	
	@Override
	public void updateScheme(SchemeVO schemevo){
		Scheme scheme = schemeService.get(schemevo.getSid());
		scheme.setName(schemevo.getName());
		scheme.setTypeno(schemevo.getTypeno());
		scheme.setStartDate(schemevo.getStartDate());
		scheme.setEndDate(schemevo.getEndDate());
		scheme.setPrice(schemevo.getPrice());
		scheme.setPostPrice(schemevo.getPostPrice());
		scheme.setSpread(schemevo.getSpread());
		scheme.setFloorAmount(schemevo.getFloorAmount());
		scheme.setCeilAmount(schemevo.getCeilAmount());
		scheme.setVendor(schemevo.getVendor());
		schemeService.rwUpdate(scheme);
	}

	@Override
	public void activeScheme(Long sid) {
		Scheme scheme = schemeService.get(sid);
		scheme.setActivityStatus(ActivityStatus.ACTIVE);
		schemeService.rwUpdate(scheme);
	}

	@Override
	public void inactiveScheme(Long sid) {
		Scheme scheme = schemeService.get(sid);
		scheme.setActivityStatus(ActivityStatus.INACTIVE);
		schemeService.rwUpdate(scheme);
	}

	@Override
	public List<SchemeTypeVO> displaySchemeTypes(SchemeTypeQueryDTO queryDTO) {
		List<SchemeType> schemeTypes = schemeTypeService.getSchemeTypes(queryDTO);
		List<SchemeTypeVO> vos = new ArrayList<SchemeTypeVO>();
		if(!CollectionUtils.isEmpty(schemeTypes)){
			for(SchemeType type:schemeTypes){
				SchemeTypeVO vo = beanMapper.map(type, SchemeTypeVO.class);
				vos.add(vo); 
			}
		}
		return vos;
	}

	@Override
	public List<DishVO> displayDishSuits() {
		List<Dish> dishes = dishService.queryDishesByType("套餐");
		List<DishVO> vos = new ArrayList<DishVO>();
		if(!CollectionUtils.isEmpty(dishes)){
			for(Dish dish:dishes){
				DishVO vo = beanMapper.map(dish, DishVO.class);
				vos.add(vo);
			}
		}
		return vos;
	}

	@Override
	public void createSchemeType(SchemeTypeVO schemeTypevo) {
		SchemeType schemeType = beanMapper.map(schemeTypevo, SchemeType.class);
		schemeType.setStatus(ActivityStatus.ACTIVE);
		schemeTypeService.rwCreate(schemeType);
		String typeno = StringUtils.leftPad(schemeType.getStid().toString(), 3, '0');
		schemeType.setTypeNo(typeno);
		schemeTypeService.rwUpdate(schemeType);
	}

	@Override
	public void updateSchemeType(SchemeTypeVO schemeTypevo) {
		SchemeType schemeType = schemeTypeService.get(schemeTypevo.getStid());
		schemeType.setTypeName(schemeTypevo.getTypeName());
		schemeType.setDishNo(schemeTypevo.getDishNo());
		schemeType.setBeverageAmount(schemeTypevo.getDiscountAmount());
		schemeType.setActivity(schemeTypevo.getActivity());
		schemeType.setFloorAmount(schemeTypevo.getFloorAmount());
		schemeType.setCeilAmount(schemeTypevo.getCeilAmount());
		schemeTypeService.rwUpdate(schemeType);
	}

	@Override
	public void activeSchemeType(Long stid) {
		SchemeType schemeType = schemeTypeService.get(stid);
		schemeType.setStatus(ActivityStatus.ACTIVE);
		schemeTypeService.rwUpdate(schemeType);
	}

	@Override
	public void inactiveSchemeType(Long stid) {
		SchemeType schemeType = schemeTypeService.get(stid);
		schemeType.setStatus(ActivityStatus.INACTIVE);
		schemeTypeService.rwUpdate(schemeType);
	}

	@Override
	public DishVO getDishByNo(String dishno) {
		return dishService.queryDish(dishno);
	}

}
