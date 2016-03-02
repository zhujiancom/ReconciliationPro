package com.rci.service.calculatecenter.filter.impl;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.rci.bean.entity.Order;
import com.rci.bean.entity.OrderAccountRef;
import com.rci.bean.entity.account.Account;
import com.rci.enums.BusinessEnums.PaymodeCode;
import com.rci.service.calculatecenter.ParameterValue;
import com.rci.service.calculatecenter.filter.AbstractPaymodeFilter;

/**
 * 
 * remark (备注): 最后将数据保存到数据库节点， 该节点是链条上的最后一个节点，一定要执行，所以support永远返回true;
 *
 * @author zj
 *	
 * 项目名称：ReconciliationPro
 *
 * 类名称：PreserveFilter
 *
 * 包名称：com.rci.service.calculatecenter.filter.impl
 *
 * Create Time: 2016年2月29日 下午5:55:10
 *
 */
public class PreserveFilter extends AbstractPaymodeFilter {

	@Override
	public boolean support(Map<PaymodeCode, BigDecimal> paymodeMapping) {
		return true;
	}

	@Override
	protected void doExtractOrderInfo(ParameterValue value) {
		Order order = (Order) value.getSourceData();
		//保存order 以及相关信息， 关联数据从ParameterValue中获取
		OrderAccountRef oar = new OrderAccountRef();
		//订单支付方式映射表
		Map<PaymodeCode,BigDecimal> payInfo = value.getPayInfo();
		for(Iterator<Entry<PaymodeCode,BigDecimal>> it = payInfo.entrySet().iterator();it.hasNext();){
			Entry<PaymodeCode,BigDecimal> entry = it.next();
			PaymodeCode paymode = entry.getKey();
			BigDecimal amount = entry.getValue();
			Account account = accService.getAccount(paymode);
			if(account == null){
				continue;
			}
			oar.setAccId(account.getAccId());
			oar.setAccNo(account.getAccNo());
			oar.setOrderNo(order.getOrderNo());
			oar.setPostTime(order.getCheckoutTime());
			oar.setRealAmount(amount);
			//保存账户入账金额
			oarService.rwCreate(oar);
			
			//更新order
			order.setSchemeName(value.joinSchemeName());//设置订单的方案信息
			order.setWarningInfo(value.joinWarningInfo());//设置订单的警告信息
			orderService.rwUpdate(order);
		}
	}

}
