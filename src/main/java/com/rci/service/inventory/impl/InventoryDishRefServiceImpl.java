package com.rci.service.inventory.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.rci.bean.entity.Dish;
import com.rci.bean.entity.inventory.InventoryDishRef;
import com.rci.dao.impl.SafeDetachedCriteria;
import com.rci.dao.impl.SafeRestrictions;
import com.rci.service.IDishService;
import com.rci.service.base.BaseServiceImpl;
import com.rci.service.inventory.IInventoryDishRefService;

@Service("InventoryDishRefService")
public class InventoryDishRefServiceImpl extends
		BaseServiceImpl<InventoryDishRef, Long> implements
		IInventoryDishRefService {
	@Resource(name="DishService")
	private IDishService dishService;

	@Override
	public List<Dish> queryRelatedDishes(String ino) {
		List<Dish> relatedDishes = new ArrayList<Dish>();
		List<InventoryDishRef> idrs = queryByInventoryNo(ino);
		if(!CollectionUtils.isEmpty(idrs)){
			String[] dishnos = new String[idrs.size()];
			for(int i=0;i<idrs.size();i++){
				dishnos[i] = idrs.get(i).getDishno();
			}
			return dishService.queryDishesByNos(dishnos);
		}
		return relatedDishes;
	}

	@Override
	public String queryRelatedDishNames(String ino) {
		StringBuffer sb = new StringBuffer("");
		List<InventoryDishRef> idrs = queryByInventoryNo(ino);
		if(!CollectionUtils.isEmpty(idrs)){
			for(InventoryDishRef idr:idrs){
				sb.append(",").append(idr.getDishname());
			}
			return sb.substring(1);
		}
		return sb.toString();
	}

	@Override
	public List<InventoryDishRef> queryByInventoryNo(String ino) {
		SafeDetachedCriteria sdc = SafeDetachedCriteria.forClass(InventoryDishRef.class);
		sdc.add(SafeRestrictions.eq("ino", ino));
		return baseDAO.queryListByCriteria(sdc);
	}

	@Override
	public void deleteRelatedInfo(String ino) {
		String hql = "delete from InventoryDishRef idr where idr.ino='"+ino+"'";
		baseDAO.executeHQL(hql);
	}

	@Override
	public List<InventoryDishRef> queryByDishNo(String dishno) {
		SafeDetachedCriteria sdc = SafeDetachedCriteria.forClass(InventoryDishRef.class);
		sdc.add(SafeRestrictions.eq("dishno", dishno));
		return baseDAO.queryListByCriteria(sdc);
	}

	@Override
	public InventoryDishRef queryUniqueRelationship(String ino, String dishno) {
		SafeDetachedCriteria sdc = SafeDetachedCriteria.forClass(InventoryDishRef.class);
		sdc.add(SafeRestrictions.eq("dishno", dishno))
			.add(SafeRestrictions.eq("ino", ino));
		return baseDAO.queryUniqueByCriteria(sdc);
	}

}
