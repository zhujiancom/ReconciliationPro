package com.rci.service.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.rci.bean.entity.DataFetchMark;
import com.rci.enums.BusinessEnums.MarkType;
import com.rci.enums.CommonEnums;
import com.rci.service.IFetchMarkService;
import com.rci.service.base.BaseService;
import com.rci.tools.DateUtil;

@Service("FetchMarkService")
public class FetchMarkServiceImpl extends BaseService<DataFetchMark, Long>
		implements IFetchMarkService {

	@Override
	public DataFetchMark getMarkRecordByDay(String day) {
		DetachedCriteria dc = DetachedCriteria.forClass(DataFetchMark.class);
		dc.add(Restrictions.eq("rciDate", day)).add(Restrictions.eq("type", MarkType.ORDER_FETCH));
		DataFetchMark mark = baseDAO.queryUniqueByCriteria(dc);
		return mark;
	}

	@Override
	public boolean isSystemInit() {
		DetachedCriteria dc = DetachedCriteria.forClass(DataFetchMark.class);
		dc.add(Restrictions.eq("type", MarkType.SYSTEM_INIT));
		DataFetchMark mark = baseDAO.queryUniqueByCriteria(dc);
		if(mark == null){
			return false;
		}
		return true;
	}

	@Override
//	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void rwSystemInit() {
		DataFetchMark mark = new DataFetchMark(MarkType.SYSTEM_INIT);
		mark.setMarkFlag(CommonEnums.YOrN.Y);
		baseDAO.save(mark);
	}

	@Override
//	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void rwOrderMark(String day) {
		DataFetchMark mark = new DataFetchMark(MarkType.ORDER_FETCH);
		mark.setRciDate(day);
		mark.setMarkFlag(CommonEnums.YOrN.Y);
		mark.setSavepoint(DateUtil.getCurrentDate());
		baseDAO.save(mark);
	}
	
	@Override
//	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void rwUpdateMark(DataFetchMark mark){
		mark.setSavepoint(DateUtil.getCurrentDate());
		baseDAO.update(mark);
	}

	@Override
	public void rwDeleteMark(String day) {
		DataFetchMark mark = getMarkRecordByDay(day);
		baseDAO.delete(mark);
	}

}
