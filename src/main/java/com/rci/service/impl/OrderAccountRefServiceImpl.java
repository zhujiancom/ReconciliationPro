package com.rci.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.ResultTransformer;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.rci.bean.entity.OrderAccountRef;
import com.rci.bean.entity.account.Account;
import com.rci.enums.BusinessEnums.OrderFramework;
import com.rci.enums.CommonEnums.Symbol;
import com.rci.service.IAccountService;
import com.rci.service.IOrderAccountRefService;
import com.rci.service.base.BaseServiceImpl;

@Service("OrderAccountRefService")
public class OrderAccountRefServiceImpl extends
		BaseServiceImpl<OrderAccountRef, Long> implements IOrderAccountRefService{
	@Resource(name="AccountService")
	private IAccountService accountService;

	@Override
	public List<OrderAccountRef> getOARef(String billno) {
		DetachedCriteria dc = DetachedCriteria.forClass(OrderAccountRef.class);
		dc.add(Restrictions.eq("orderNo", billno));
		return baseDAO.queryListByCriteria(dc);
	}
	
	public BigDecimal getPostAmountForOrder(String billno){
		BigDecimal postAmount = BigDecimal.ZERO;
		List<OrderAccountRef> oars = getOARef(billno);
		if(!CollectionUtils.isEmpty(oars)){
			for(OrderAccountRef oar:oars){
				Account account = accountService.getAccount(oar.getAccNo());
				if(Symbol.P.equals(account.getSymbol())){
					postAmount = postAmount.add(oar.getRealAmount());
				}
			}
		}
		return postAmount;
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
				String accountNo = tuple[1].toString();
				return new AccountSumResult((Long)tuple[2],accountNo,(BigDecimal)tuple[0]);
			}
			
			@SuppressWarnings("rawtypes")
			@Override
			public List transformList(List collection) {
				return collection;
			}
		}) ;
		return baseDAO.queryListByCriteria(dc);
	}
	
	@Override
	public List<AccountSumResult> queryDateRangeSumAmount(Date sdate,Date edate) {
		DetachedCriteria dc = DetachedCriteria.forClass(OrderAccountRef.class);
		dc.setProjection(Projections.projectionList().add(Projections.sum("realAmount")).add(Projections.groupProperty("accNo")).add(Projections.groupProperty("accId"))).
		   add(Restrictions.ge("postTime", sdate)).add(Restrictions.lt("postTime", edate))
		   .setResultTransformer(new ResultTransformer() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = -1878026106020737560L;

			@Override
			public Object transformTuple(Object[] tuple, String[] aliases) {
				String accountNo = tuple[1].toString();
				return new AccountSumResult((Long)tuple[2],accountNo,(BigDecimal)tuple[0]);
			}
			
			@SuppressWarnings("rawtypes")
			@Override
			public List transformList(List collection) {
				return collection;
			}
		}) ;
		return baseDAO.queryListByCriteria(dc);
	}
	
	@Override
	public BigDecimal querySumAmount(String account,Date postTime,OrderFramework framework) {
//		String queryDate = DateUtil.date2Str(postTime, "yyyyMMdd");
//		String nativeSql = "select sum(oar.real_amount) 'amount' from bus_tb_order_account_ref oar \n"
//				+ "where oar.order_no in ( \n"
//				+ "		select o.order_no from bus_tb_order o where o.day='"+queryDate+"' and o.framework='"+framework.name()+"' \n"
//				+ ") and oar.accno='"+account.name()+"'";
//		Map<String,Object> resultMap = baseDAO.queryListBySQL(nativeSql).get(0);
//		BigDecimal amount = (BigDecimal) resultMap.get("amount");
		DetachedCriteria dc = DetachedCriteria.forClass(OrderAccountRef.class);
		dc.setProjection(Projections.projectionList().add(Projections.sum("realAmount"))).
			add(Restrictions.eq("postTime", postTime)).add(Restrictions.eq("framework", framework)).add(Restrictions.eq("accNo", account));
		BigDecimal amount = baseDAO.queryUniqueByCriteria(dc);
		return amount;
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

	@Override
	public Long getValidOrderCount(Date postTime, String account) {
		DetachedCriteria dc = DetachedCriteria.forClass(OrderAccountRef.class);
		dc.setProjection(Projections.projectionList().add(Projections.rowCount())).add(Restrictions.eq("accNo", account)).add(Restrictions.eq("postTime", postTime));
		Long count = baseDAO.getRowCount(dc);
		return count;
	}
}
