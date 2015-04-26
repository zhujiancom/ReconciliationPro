package com.rci.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.rci.bean.entity.OrderAccountRef;
import com.rci.service.IOrderAccountRefService;
import com.rci.service.base.BaseService;

@Service("OrderAccountRefService")
public class OrderAccountRefServiceImpl extends
		BaseService<OrderAccountRef, Long> implements IOrderAccountRefService{

	@Override
	public List<OrderAccountRef> getOARef(String billno) {
		DetachedCriteria dc = DetachedCriteria.forClass(OrderAccountRef.class);
		dc.add(Restrictions.eq("orderNo", billno));
		return baseDAO.queryListByCriteria(dc);
	}

	/* 
	 * @see com.rci.service.IOrderAccountRefService#rwInsertOra(com.rci.bean.entity.OrderAccountRef)
	 */
	@Override
	public void rwInsertOar(OrderAccountRef oar) {
		rwCreate(oar);
	}

}
