package com.rci.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.dozer.Mapper;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rci.bean.dto.QueryDishDTO;
import com.rci.bean.entity.Dish;
import com.rci.dao.impl.SafeDetachedCriteria;
import com.rci.dao.impl.SafeRestrictions;
import com.rci.enums.CommonEnums.YOrN;
import com.rci.exceptions.ExceptionManage;
import com.rci.exceptions.ExceptionConstant.SERVICE;
import com.rci.service.IDishService;
import com.rci.service.base.BaseServiceImpl;
import com.rci.ui.swing.vos.DishVO;

@Service("DishService")
public class DishServiceImpl extends BaseServiceImpl<Dish, Long> implements
		IDishService {
	@Autowired
	private Mapper beanMapper;
	
	@Override
	public Dish findDishByNo(String no) {
		Dish dish = null;
		try{
			DetachedCriteria dc = DetachedCriteria.forClass(Dish.class);
			dc.add(Restrictions.eq("dishNo", no));
			dish = baseDAO.queryUniqueByCriteria(dc);
		}catch(Exception e){
			logger.error("菜品编号【"+no+"】重复", e);
			ExceptionManage.throwServiceException(SERVICE.DATA_ERROR,"菜品编号【"+no+"】重复",e);
		}
		return dish;
	}
	
	@Override
	public List<DishVO> queryDishes(boolean isStocked) {
		DetachedCriteria dc = DetachedCriteria.forClass(Dish.class);
		dc.add(Restrictions.eq("stopFlag", YOrN.N)).add(Restrictions.eq("stockFlag", YOrN.getYN(isStocked)));
		List<Dish> dishes = baseDAO.queryListByCriteria(dc);
		List<DishVO> dishVos = new ArrayList<DishVO>();
		for(Dish dish:dishes){
			DishVO dishvo = beanMapper.map(dish, DishVO.class);
			dishVos.add(dishvo);
		}
		return dishVos;
	}



	@Override
	public DishVO queryDish(String no) {
		Dish dish = findDishByNo(no);
		if(dish == null){
			return null;
		}
		return beanMapper.map(dish, DishVO.class);
	}



	@Override
	public void rwupdateDishInfo(Dish dish) {
//		baseDAO.update(dish);
//		((IDishService)AopContext.currentProxy()).rwUpdate(dish);
	}



	@Override
	public List<Dish> queryDishesBySeries(String seriesName) {
		SafeDetachedCriteria sdc = SafeDetachedCriteria.forClass(Dish.class);
		sdc.createAlias("dishSeries", "series");
		sdc.add(SafeRestrictions.eq("series.seriesname", seriesName)).add(SafeRestrictions.eq("stopFlag", YOrN.N));
		return baseDAO.queryListByCriteria(sdc);
	}



	@Override
	public List<Dish> queryDishesByTypeno(String typeno) {
		SafeDetachedCriteria sdc = SafeDetachedCriteria.forClass(Dish.class);
		sdc.createAlias("dishType", "type");
		sdc.add(SafeRestrictions.eq("type.dtNo", typeno)).add(SafeRestrictions.eq("stopFlag", YOrN.N));
		return baseDAO.queryListByCriteria(sdc);
	}
	
	@Override
	public List<Dish> queryAllValidDishes(){
		SafeDetachedCriteria sdc = SafeDetachedCriteria.forClass(Dish.class);
		sdc.add(SafeRestrictions.eq("stopFlag", YOrN.N));
		return baseDAO.queryListByCriteria(sdc);
	}



	@Override
	public List<Dish> queryDishesByNos(String[] dishnos) {
		SafeDetachedCriteria sdc = SafeDetachedCriteria.forClass(Dish.class);
		sdc.add(SafeRestrictions.in("dishNo", dishnos));
		return baseDAO.queryListByCriteria(sdc);
	}



	@Override
	public List<Dish> queryDishes(QueryDishDTO queryDTO) {
		SafeDetachedCriteria sdc = SafeDetachedCriteria.forClass(Dish.class);
		sdc.createAlias("dishType", "type");
		sdc.createAlias("dishSeries", "series");
		sdc.add(SafeRestrictions.eq("type.dtNo", queryDTO.getTno()))
			.add(SafeRestrictions.eq("stopFlag", YOrN.N))
			.add(SafeRestrictions.eq("series.seriesno", queryDTO.getSno()));
		return baseDAO.queryListByCriteria(sdc);
	}

	
}
