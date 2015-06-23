package com.rci.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.ResultTransformer;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;

import com.rci.bean.entity.OrderAccountRef;
import com.rci.service.IOrderAccountRefService;
import com.rci.service.base.BaseServiceImpl;

@Service("OrderAccountRefService")
public class OrderAccountRefServiceImpl extends
		BaseServiceImpl<OrderAccountRef, Long> implements IOrderAccountRefService{

	@Override
	public List<OrderAccountRef> getOARef(String billno) {
		DetachedCriteria dc = DetachedCriteria.forClass(OrderAccountRef.class);
		dc.add(Restrictions.eq("orderNo", billno));
		return baseDAO.queryListByCriteria(dc);
	}


	@Override
	public void deleteOar(Date date) {
//		String hql = "delete OrderAccountRef oa where oa.postTime='"+DateUtil.date2Str(date)+"'";
//		int deleteFlag = baseDAO.executeHQL(hql);
//		logger().debug("删除成功标识："+deleteFlag);
		
		DetachedCriteria dc = DetachedCriteria.forClass(OrderAccountRef.class);
		dc.add(Restrictions.eq("postTime", date));
		List<OrderAccountRef> oars = baseDAO.queryListByCriteria(dc);
		((IOrderAccountRefService)AopContext.currentProxy()).rwDelete(oars.toArray(new OrderAccountRef[0]));
	}

	@Override
	public List<AccountSumResult> querySumAmount(Date postTime) {
		DetachedCriteria dc = DetachedCriteria.forClass(OrderAccountRef.class);
		dc.setProjection(Projections.projectionList().add(Projections.sum("realAmount")).add(Projections.groupProperty("accNo")).add(Projections.groupProperty("accId"))).
		   add(Restrictions.eq("postTime", postTime))
		   .setResultTransformer(new ResultTransformer() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = -1878026106020737560L;

			@Override
			public Object transformTuple(Object[] tuple, String[] aliases) {
				return new AccountSumResult((Long)tuple[2],(String)tuple[1],(BigDecimal)tuple[0]);
			}
			
			@SuppressWarnings("rawtypes")
			@Override
			public List transformList(List collection) {
				return collection;
			}
		}) ;
		return baseDAO.queryListByCriteria(dc);
	}
	
	public static class AccountSumResult{
		private Long accId;
		
		private String accNo;
		
		private BigDecimal sumAmount;

		public AccountSumResult(Long accId, String accNo, BigDecimal sumAmount) {
			super();
			this.accId = accId;
			this.accNo = accNo;
			this.sumAmount = sumAmount;
		}

		public Long getAccId() {
			return accId;
		}

		public void setAccId(Long accId) {
			this.accId = accId;
		}

		public String getAccNo() {
			return accNo;
		}

		public void setAccNo(String accNo) {
			this.accNo = accNo;
		}

		public BigDecimal getSumAmount() {
			return sumAmount;
		}

		public void setSumAmount(BigDecimal sumAmount) {
			this.sumAmount = sumAmount;
		}
	}
}
