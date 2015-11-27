package com.rci.service.inventory.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.dozer.Mapper;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.ResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.rci.bean.dto.SaleLogQueryDTO;
import com.rci.bean.entity.Dish;
import com.rci.bean.entity.inventory.InventorySellLog;
import com.rci.dao.impl.SafeDetachedCriteria;
import com.rci.dao.impl.SafeRestrictions;
import com.rci.service.IDishService;
import com.rci.service.base.BaseServiceImpl;
import com.rci.service.inventory.IInventorySellLogService;
import com.rci.service.inventory.IInventoryService;
import com.rci.tools.DateUtil;
import com.rci.ui.swing.vos.SaleLogDetailVO;
import com.rci.ui.swing.vos.SaleLogVO;

@Service("InventorySellLogService")
public class InventorySellLogServiceImpl extends
		BaseServiceImpl<InventorySellLog, Long> implements
		IInventorySellLogService {
	@Resource(name="DishService")
	private IDishService dishService;
	
	@Resource(name="InventoryService")
	private IInventoryService inventoryService;
	
	@Autowired
	private Mapper beanMapper;
	
	public List<SaleLogVO> querySumSaleLog(final SaleLogQueryDTO queryDTO){
		try{
			SafeDetachedCriteria sdc = SafeDetachedCriteria.forClass(InventorySellLog.class);
			sdc.setProjection(Projections.projectionList().add(Projections.sum("consumeAmount"))
							.add(Projections.groupProperty("ino"))
							.add(Projections.groupProperty("iname"))
							.add(Projections.groupProperty("day")))
							.add(SafeRestrictions.eq("day", queryDTO.getDay()))
							.add(SafeRestrictions.likeAnyWhere("iname", queryDTO.getKeyword()))
							.setResultTransformer(new ResultTransformer() {
								
								/**
								 * 
								 */
								private static final long serialVersionUID = 8340411616704846711L;

								@Override
								public Object transformTuple(Object[] tuple, String[] aliases) {
									SaleLogVO result = new SaleLogVO((String)tuple[1],(String)tuple[2],(BigDecimal)tuple[0]);
									Date queryDate = null;
									try{
										queryDate = DateUtil.parseDate((String)tuple[3],"yyyyMMdd");
									}catch(Exception e){
										logger.debug("日期转换错误",e);
									}
									result.setSaleDate(queryDate);
									return result;
								}
								
								@SuppressWarnings("rawtypes")
								@Override
								public List transformList(List collection) {
									return collection;
								}
							});
			
			
			return baseDAO.queryListByCriteria(sdc);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	
	public List<SaleLogDetailVO> querySaleLogDetail(SaleLogQueryDTO queryDTO){
		List<SaleLogDetailVO> details = new ArrayList<SaleLogDetailVO>();
		SafeDetachedCriteria sdc = SafeDetachedCriteria.forClass(InventorySellLog.class);
		sdc.add(SafeRestrictions.eq("ino", queryDTO.getIno()))
		.add(SafeRestrictions.eq("day", queryDTO.getDay()));
		List<InventorySellLog> logs = baseDAO.queryListByCriteria(sdc);
		if(!CollectionUtils.isEmpty(logs)){
			for(InventorySellLog log:logs){
				SaleLogDetailVO detail = beanMapper.map(log, SaleLogDetailVO.class);
				Dish dish = dishService.findDishByNo(log.getDishno());
				detail.setDishname(dish.getDishName());
				details.add(detail);
			}
		}
		return details;
	}

	@Override
	public void rollbackLog(SaleLogQueryDTO queryDTO) {
		List<SaleLogVO> logs = querySumSaleLog(queryDTO);
		if(!CollectionUtils.isEmpty(logs)){
			for(SaleLogVO log:logs){
				inventoryService.rollback(log.getIno(), log.getSaleAmount());
			}
			deleteLogByDay(queryDTO.getDay());
		}
	}

	@Override
	public void deleteLogByDay(String day) {
		String hql = "delete from InventorySellLog log where log.day='"+day+"'";
		baseDAO.executeHQL(hql);
	}

}
