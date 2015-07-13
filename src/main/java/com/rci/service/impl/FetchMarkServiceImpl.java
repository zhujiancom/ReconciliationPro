package com.rci.service.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;

import com.rci.bean.entity.DataFetchMark;
import com.rci.enums.BusinessEnums.MarkType;
import com.rci.enums.CommonEnums;
import com.rci.service.IFetchMarkService;
import com.rci.service.base.BaseServiceImpl;
import com.rci.tools.DateUtil;

/**
 * 
 * remark (备注): 系统各项操作标记服务
 *
 * @author zj
 *	
 * 项目名称：ReconciliationPro
 *
 * 类名称：FetchMarkServiceImpl
 *
 * 包名称：com.rci.service.impl
 *
 * Create Time: 2015年6月23日 上午11:17:47
 *
 */
@Service("FetchMarkService")
public class FetchMarkServiceImpl extends BaseServiceImpl<DataFetchMark, Long>
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
	public void rwInitSystem() {
		DataFetchMark mark = new DataFetchMark(MarkType.SYSTEM_INIT);
		mark.setMarkFlag(CommonEnums.YOrN.Y);
		baseDAO.save(mark);
	}
	
	@Override
	public void deleteMark(String day) {
		DataFetchMark mark = getMarkRecordByDay(day);
		if(mark != null){
			((IFetchMarkService)AopContext.currentProxy()).rwDelete(mark.getMarkId());
		}
	}

	@Override
	public void markOrder(String day) {
		DataFetchMark mark = new DataFetchMark(MarkType.ORDER_FETCH);
		mark.setRciDate(day);
		mark.setMarkFlag(CommonEnums.YOrN.Y);
		mark.setSavepoint(DateUtil.getCurrentDate());
		((IFetchMarkService)AopContext.currentProxy()).rwCreate(mark);
	}

	@Override
	public void rwInitStock() {
		DataFetchMark mark = new DataFetchMark(MarkType.STOCK_INIT);
		mark.setMarkFlag(CommonEnums.YOrN.Y);
		baseDAO.save(mark);
	}

}
