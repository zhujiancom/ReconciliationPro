package com.rci.service.core;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.rci.bean.dto.PurchaseRecordQueryDTO;
import com.rci.bean.dto.QueryDishDTO;
import com.rci.bean.dto.SaleLogQueryDTO;
import com.rci.bean.dto.SchemeQueryDTO;
import com.rci.bean.dto.SchemeTypeQueryDTO;
import com.rci.bean.entity.Dish;
import com.rci.bean.entity.DishSeries;
import com.rci.bean.entity.DishType;
import com.rci.bean.entity.Scheme;
import com.rci.bean.entity.SchemeType;
import com.rci.bean.entity.SchemeTypeDishRef;
import com.rci.bean.entity.inventory.Inventory;
import com.rci.bean.entity.inventory.InventoryDishRef;
import com.rci.bean.entity.inventory.PurchaseRecord;
import com.rci.bean.entity.inventory.SellOffWarning;
import com.rci.enums.BusinessEnums.ActivityStatus;
import com.rci.enums.BusinessEnums.State;
import com.rci.enums.CommonEnums.YOrN;
import com.rci.metadata.service.IDataTransformService;
import com.rci.service.IDishSeriesService;
import com.rci.service.IDishService;
import com.rci.service.IDishTypeService;
import com.rci.service.IOrderService;
import com.rci.service.IPayModeService;
import com.rci.service.ISchemeService;
import com.rci.service.ISchemeTypeDishRefService;
import com.rci.service.ISchemeTypeService;
import com.rci.service.ITableInfoService;
import com.rci.service.inventory.IInventoryDishRefService;
import com.rci.service.inventory.IInventorySellLogService;
import com.rci.service.inventory.IInventoryService;
import com.rci.service.inventory.IPurchaseRecordService;
import com.rci.service.inventory.ISellOffWarningService;
import com.rci.tools.DateUtil;
import com.rci.tools.EnumUtils;
import com.rci.ui.swing.vos.DishSeriesVO;
import com.rci.ui.swing.vos.DishTypeVO;
import com.rci.ui.swing.vos.DishVO;
import com.rci.ui.swing.vos.HangupTabelInfoVO;
import com.rci.ui.swing.vos.InventoryDishRefVO;
import com.rci.ui.swing.vos.InventoryVO;
import com.rci.ui.swing.vos.OrderItemVO;
import com.rci.ui.swing.vos.PurchaseRecordVO;
import com.rci.ui.swing.vos.SaleLogDetailVO;
import com.rci.ui.swing.vos.SaleLogVO;
import com.rci.ui.swing.vos.SchemeTypeVO;
import com.rci.ui.swing.vos.SchemeVO;
import com.rci.ui.swing.vos.SellOffWarningVO;

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
	@Resource(name="SchemeService")
	private ISchemeService schemeService;
	@Resource(name="SchemeTypeService")
	private ISchemeTypeService schemeTypeService;
	@Resource(name="SchemeTypeDishRefService")
	private ISchemeTypeDishRefService sdrefService;
	@Resource(name="InventoryService")
	private IInventoryService inventoryService;
	@Resource(name="InventoryDishRefService")
	private IInventoryDishRefService idrService;
	@Resource(name="PurchaseRecordService")
	private IPurchaseRecordService purchaseService;
	@Resource(name="InventorySellLogService")
	private IInventorySellLogService sellLogService;
	@Resource(name="SellOffWarningService")
	private ISellOffWarningService sowarningService;
	@Resource(name="OrderService")
	private IOrderService orderService;
	
	@Resource(name="SellOffWarningService")
	private ISellOffWarningService inventoryWarningService;
	
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
				List<DishVO> dishes = sdrefService.queryDishesByTypeno(vo.getTypeNo());
				vo.setDishes(dishes);
				if(!CollectionUtils.isEmpty(dishes)){
					StringBuffer sb = new StringBuffer();
					for(DishVO dish:dishes){
						sb.append(",").append(dish.getDishName());
					}
					vo.setDishName(sb.substring(1));
				}
				vos.add(vo); 
			}
		}
		return vos;
	}

	@Override
	public void createSchemeType(SchemeTypeVO schemeTypevo) {
		SchemeType schemeType = beanMapper.map(schemeTypevo, SchemeType.class);
		schemeType.setStatus(ActivityStatus.ACTIVE);
		List<DishVO> dishvos = schemeTypevo.getDishes();
		List<SchemeTypeDishRef> refs = new ArrayList<SchemeTypeDishRef>(); 
		if(!CollectionUtils.isEmpty(dishvos)){
			for(DishVO dishvo:dishvos){
				SchemeTypeDishRef typedishRef= new SchemeTypeDishRef(schemeType.getTypeNo(),dishvo.getDishNo(), dishvo.getDishName());
				refs.add(typedishRef);
			}
		}
		schemeTypeService.rwCreate(schemeType);
		sdrefService.rwCreate(refs.toArray(new SchemeTypeDishRef[0]));
	}

	@Override
	public void updateSchemeType(SchemeTypeVO schemeTypevo) {
		SchemeType schemeType = schemeTypeService.get(schemeTypevo.getStid());
		schemeType.setTypeName(schemeTypevo.getTypeName());
		schemeType.setBeverageAmount(schemeTypevo.getDiscountAmount());
		schemeType.setActivity(schemeTypevo.getActivity());
		schemeType.setFloorAmount(schemeTypevo.getFloorAmount());
		schemeType.setCeilAmount(schemeTypevo.getCeilAmount());
		schemeType.setRealAmount(schemeTypevo.getRealAmount());
		schemeTypeService.rwUpdate(schemeType);
		//更新dish关联信息
		List<DishVO> dishvos = schemeTypevo.getDishes();
		sdrefService.rwDeleteRefByTypeno(schemeType.getTypeNo());
		if(!CollectionUtils.isEmpty(dishvos)){
			List<SchemeTypeDishRef> sdrefs = new ArrayList<SchemeTypeDishRef>();
			for(DishVO dishvo:dishvos){
				SchemeTypeDishRef sdref = new SchemeTypeDishRef(schemeType.getTypeNo(),dishvo.getDishNo(),dishvo.getDishName());
				sdrefs.add(sdref);
			}
			sdrefService.rwCreate(sdrefs.toArray(new SchemeTypeDishRef[0]));
		}
			
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

	@Override
	public List<DishSeriesVO> getAllDishSeries() {
		List<DishSeriesVO> result = new ArrayList<DishSeriesVO>();
		List<DishSeries> series = dishseriesService.getAll();
		if(!CollectionUtils.isEmpty(series)){
			for(DishSeries ds:series){
				result.add(beanMapper.map(ds, DishSeriesVO.class));
			}
		}
		return result;
	}

	@Override
	public List<DishTypeVO> getAllDishTypeBySeriesNo(String seriesno) {
		List<DishType> types = dishtypeService.queryDishTypesBySeriesno(seriesno);
		List<DishTypeVO> typevos = new ArrayList<DishTypeVO>();
		if(!CollectionUtils.isEmpty(types)){
			for(DishType type:types){
				typevos.add(beanMapper.map(type, DishTypeVO.class));
			}
		}
		return typevos;
	}

	@Override
	public List<DishVO> getAllDishByTypeNo(String typeno) {
		List<Dish> dishes = dishService.queryDishesByTypeno(typeno);
		List<DishVO> vos = new ArrayList<DishVO>();
		if(!CollectionUtils.isEmpty(dishes)){
			for(Dish dish:dishes){
				vos.add(beanMapper.map(dish, DishVO.class));
			}
		}
		return vos;
	}
	
	@Override
	public List<DishVO> getAllDishes() {
		List<Dish> dishes = dishService.queryAllValidDishes();
		List<DishVO> vos = new ArrayList<DishVO>();
		if(!CollectionUtils.isEmpty(dishes)){
			for(Dish dish:dishes){
				vos.add(beanMapper.map(dish, DishVO.class));
			}
		}
		return vos;
	}
	
	@Override
	public List<DishVO> queryDishes(QueryDishDTO queryDTO) {
		List<Dish> dishes = dishService.queryDishes(queryDTO);
		List<DishVO> vos = new ArrayList<DishVO>();
		if(!CollectionUtils.isEmpty(dishes)){
			for(Dish dish:dishes){
				vos.add(beanMapper.map(dish, DishVO.class));
			}
		}
		return vos;
	}

	@Override
	public List<InventoryVO> displayAllInventory() {
		List<InventoryVO> result = new ArrayList<InventoryVO>();
		List<Inventory> inventories = inventoryService.queryValidInventories();
		if(!CollectionUtils.isEmpty(inventories)){
			for(Inventory inv:inventories){
				InventoryVO invvo = beanMapper.map(inv, InventoryVO.class);
				StringBuffer sb = new StringBuffer("");
				List<Dish> dishes = idrService.queryRelatedDishes(inv.getIno());
				if(!CollectionUtils.isEmpty(dishes)){
					List<DishVO> dishvos = new ArrayList<DishVO>();
					for(Dish dish:dishes){
						dishvos.add(beanMapper.map(dish, DishVO.class));
						sb.append(",").append(dish.getDishName());
					}
					invvo.setRelatedDishes(dishvos);
					invvo.setRelatedDishNames(sb.substring(1));
				}
				result.add(invvo);
			}
		}
		return result;
	}

	@Override
	public boolean checkInventoryNoExist(String ino) {
		Inventory inventory = inventoryService.queryInventoryByNo(ino);
		if(inventory != null){
			return true;
		}
		return false;
	}

	@Override
	public void createInventory(InventoryVO inventoryvo) {
		Inventory inventory = beanMapper.map(inventoryvo, Inventory.class);
		inventory.setStatus(YOrN.Y);
		List<InventoryDishRef> idrs = new ArrayList<InventoryDishRef>();
		List<DishVO> dishes = inventoryvo.getRelatedDishes();
		if(!CollectionUtils.isEmpty(dishes)){
			for(DishVO dish:dishes){
				InventoryDishRef idr = new InventoryDishRef(inventoryvo.getIno(),dish.getDishNo(),dish.getDishName());
				idrs.add(idr);
			}
		}
		inventoryService.rwCreate(inventory);
		idrService.rwCreate(idrs.toArray(new InventoryDishRef[0]));
	}

	@Override
	public void updateInventory(InventoryVO inventoryvo) {
		Inventory inventory = inventoryService.get(inventoryvo.getIid());
		List<InventoryDishRef> idrs = new ArrayList<InventoryDishRef>();
		List<DishVO> dishes = inventoryvo.getRelatedDishes();
		if(!CollectionUtils.isEmpty(dishes)){
			idrService.deleteRelatedInfo(inventoryvo.getIno());
			for(DishVO dish:dishes){
				InventoryDishRef idr = new InventoryDishRef(inventoryvo.getIno(),dish.getDishNo(),dish.getDishName());
				idrs.add(idr);
			}
		}
		inventoryService.rwUpdate(inventory);
		idrService.rwCreate(idrs.toArray(new InventoryDishRef[0]));
	}

	@Override
	public void disableInventory(Long iid) {
		Inventory inventory = inventoryService.get(iid);
		inventory.setStatus(YOrN.N);
		inventoryService.rwUpdate(inventory);
	}

	@Override
	public void purchaseInventory(InventoryVO inventoryvo,
			BigDecimal purchaseAmount) {
		Inventory inventory = inventoryService.get(inventoryvo.getIid());
		BigDecimal cardinal = inventory.getCardinal();
		BigDecimal switchedPurAmount = purchaseAmount.multiply(cardinal);
		
		BigDecimal totalAmount = inventory.getTotalAmount() == null?BigDecimal.ZERO:inventory.getTotalAmount();
		BigDecimal balanceAmount = inventory.getBalanceAmount() == null?BigDecimal.ZERO:inventory.getBalanceAmount();
		inventory.setTotalAmount(totalAmount.add(switchedPurAmount));
		inventory.setBalanceAmount(balanceAmount.add(switchedPurAmount));
		inventoryService.rwUpdate(inventory);
		
		PurchaseRecord record = new PurchaseRecord(inventory.getIno());
		record.setIname(inventory.getIname());
		record.setPreBalanceAmount(balanceAmount);
		record.setAfterBalanceAmount(balanceAmount.add(switchedPurAmount));
		record.setPurAmount(switchedPurAmount);
		record.setPurDate(DateUtil.getCurrentDate());
		purchaseService.rwCreate(record);
		
		SellOffWarning warningInfo = inventoryWarningService.queryValidWarningInfo(inventoryvo.getIno());
		if(warningInfo!=null){
			BigDecimal amount = warningInfo.getBalanceAmount().add(purchaseAmount);
			warningInfo.setBalanceAmount(amount);
			warningInfo.setpDate(DateUtil.getCurrentDate());
			warningInfo.setPurchaseAmount(purchaseAmount);
			if(amount.compareTo(BigDecimal.ZERO) > 0){
				warningInfo.setState(State.OVERDUE);
			}
			inventoryWarningService.rwUpdate(warningInfo);
		}
	}

	@Override
	public List<InventoryVO> queryInventory(String iname) {
		List<InventoryVO> result = new ArrayList<InventoryVO>();
		List<Inventory> inventories = inventoryService.queryInventoryByName(iname);
		if(!CollectionUtils.isEmpty(inventories)){
			for(Inventory inv:inventories){
				InventoryVO invvo = beanMapper.map(inv, InventoryVO.class);
				String relatedDishNames = idrService.queryRelatedDishNames(inv.getIno());
				invvo.setRelatedDishNames(relatedDishNames);
				result.add(invvo);
			}
		}
		return result;
	}

	@Override
	public List<PurchaseRecordVO> displayAllPurchaseRecord() {
		List<PurchaseRecord> records = purchaseService.getAll();
		List<PurchaseRecordVO> vos = new ArrayList<PurchaseRecordVO>();
		if(!CollectionUtils.isEmpty(records)){
			for(PurchaseRecord record:records){
				vos.add(beanMapper.map(record, PurchaseRecordVO.class));
			}
		}
		return vos;
	}

	@Override
	public List<PurchaseRecordVO> queryRecords(PurchaseRecordQueryDTO queryDTO) {
		List<PurchaseRecord> records = purchaseService.fuzzyQuery(queryDTO);
		List<PurchaseRecordVO> vos = new ArrayList<PurchaseRecordVO>();
		if(!CollectionUtils.isEmpty(records)){
			for(PurchaseRecord record:records){
				vos.add(beanMapper.map(record, PurchaseRecordVO.class));
			}
		}
		return vos;
	}

	@Override
	public List<SaleLogVO> displaySaleLogs() {
		String queryDay = DateUtil.date2Str(DateUtil.getCurrentDate(), "yyyyMMdd");
		SaleLogQueryDTO queryDTO = new SaleLogQueryDTO();
		queryDTO.setDay(queryDay);
		List<SaleLogVO> logs = sellLogService.querySumSaleLog(queryDTO);
		if(!CollectionUtils.isEmpty(logs)){
			for(SaleLogVO log:logs){
				Inventory inv = inventoryService.queryInventoryByNo(log.getIno());
				if(inv.getCost() != null){
					log.setCostAmount(inv.getCost().multiply(log.getSaleAmount()));
				}
			}
		}
		return logs;
	}

	@Override
	public List<SaleLogDetailVO> displaySaleLogDetails(String ino) {
		SaleLogQueryDTO queryDTO = new SaleLogQueryDTO();
		queryDTO.setIno(ino);
		String queryDay = DateUtil.date2Str(DateUtil.getCurrentDate(), "yyyyMMdd");
		queryDTO.setDay(queryDay);
		return sellLogService.querySaleLogDetail(queryDTO);
	}

	@Override
	public List<SaleLogVO> querySaleLog(SaleLogQueryDTO queryDTO) {
		List<SaleLogVO> logs = sellLogService.querySumSaleLog(queryDTO);
		if(!CollectionUtils.isEmpty(logs)){
			for(SaleLogVO log:logs){
				Inventory inv = inventoryService.queryInventoryByNo(log.getIno());
				if(inv.getCost() != null){
					log.setCostAmount(inv.getCost().multiply(log.getSaleAmount()));
				}
			}
		}
		return logs;
	}
	
	@Override
	public List<SaleLogDetailVO> querySaleLogDetail(SaleLogQueryDTO queryDTO){
		return sellLogService.querySaleLogDetail(queryDTO);
	}

	@Override
	public List<SellOffWarningVO> displayAllSellOffWarning() {
		List<SellOffWarning> warnings = sowarningService.getAll();
		List<SellOffWarningVO> warningvos = new ArrayList<SellOffWarningVO>();
		if(!CollectionUtils.isEmpty(warnings)){
			for(SellOffWarning warning:warnings){
				warningvos.add(beanMapper.map(warning, SellOffWarningVO.class));
			}
		}
		return warningvos;
	}

	@Override
	public void costSetting(String ino, BigDecimal cost) {
		Inventory inventory = inventoryService.queryInventoryByNo(ino);
		inventory.setCost(cost);
		inventoryService.rwUpdate(inventory);
	}

	@Override
	public List<InventoryDishRefVO> queryInventoryDishRefByDish(String dishno) {
		List<InventoryDishRefVO> refvos = new ArrayList<InventoryDishRefVO>();
		List<InventoryDishRef> refs = idrService.queryByDishNo(dishno);
		if(!CollectionUtils.isEmpty(refs)){
			for(InventoryDishRef ref:refs){
				InventoryDishRefVO refvo = beanMapper.map(ref, InventoryDishRefVO.class);
				Inventory inventory = inventoryService.queryInventoryByNo(ref.getIno());
				refvo.setIname(inventory.getIname());
				refvos.add(refvo);
			}
		}
		return refvos;
	}

	@Override
	public void updateDishInfo(DishVO dishvo) {
		Dish dish = dishService.get(dishvo.getDid());
		dish.setCost(dishvo.getCost());
		dish.setDiscountFlag(dishvo.getDiscountFlag());
		dish.setSuitFlag(dishvo.getSuitFlag());
		dish.setStopFlag(dishvo.getStopFlag());
		dish.setDishPrice(dishvo.getDishPrice());
		dish.setStatisticFlag(dishvo.getStatisticFlag());
		dish.setBoxFeeFlag(dishvo.getBoxFeeFlag());
		dish.setTakeoutFeeFlag(dishvo.getTakeoutFeeFlag());
		dishService.rwUpdate(dish);
	}

	@Override
	public void standardSetting(InventoryDishRefVO refvo) {
		InventoryDishRef idr = idrService.get(refvo.getIdrid());
		idr.setStandard(refvo.getStandard());
		idrService.rwUpdate(idr);
	}

	@Override
	public List<OrderItemVO> queryOrderItemsByPayno(String payno) {
		return orderService.queryOrderItemVOsByPayno(payno);
	}

	@Override
	public List<HangupTabelInfoVO> getHangupTablesInfo() {
		return transformService.transformHangupTableInfo();
	}

	@Override
	public boolean hasSellOffWarningInfo() {
		List<SellOffWarning> warnings = inventoryWarningService.queryAllValidWarningInfo();
		if(!CollectionUtils.isEmpty(warnings)){
			return true;
		}
		return false;
	}

	@Override
	public void warningLineSetting(String ino, BigDecimal warningLine) {
		Inventory inventory = inventoryService.queryInventoryByNo(ino);
		BigDecimal cardinal = inventory.getCardinal();
		warningLine = warningLine.multiply(cardinal);
		inventory.setWarningLine(warningLine);
		inventoryService.rwUpdate(inventory);
	}

}
