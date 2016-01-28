package com.rci.service.filter.parser.mtwm;

import java.math.BigDecimal;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rci.bean.entity.Order;
import com.rci.bean.entity.OrderItem;
import com.rci.enums.BusinessEnums.AccountCode;
import com.rci.service.filter.FilterChain;
import com.rci.service.filter.parser.AbstractPlatformParser;
import com.rci.tools.DigitUtil;
import com.rci.tools.StringUtils;

/**
 * 
 * remark (备注): 2016-01-18之后使用该算法
 *
 * @author zj
 *	
 * 项目名称：ReconciliationPro
 *
 * 类名称：MTWMParser2Impl
 *
 * 包名称：com.rci.service.filter.parser.mtwm
 *
 * Create Time: 2016年1月22日 上午10:13:56
 *
 */
@Service("MtwmParser2")
public class MTWMParser2Impl extends AbstractPlatformParser {
	@Resource(name="filterChain")
	private FilterChain filterChain;
	
	@Override
	public void doParse(Order order) {
		Map<String,BigDecimal> freeMap = filterChain.getFreeOnlineMap();
		BigDecimal unAccessoryAmount = BigDecimal.ZERO;
		BigDecimal boxfeeAmount = BigDecimal.ZERO;
		for(OrderItem item:order.getItems()){
			String dishno = item.getDishNo();
			BigDecimal singlePrice = item.getPrice();
			BigDecimal count = item.getCount();
			BigDecimal countback = item.getCountback();
			BigDecimal ratepercent = item.getDiscountRate();
			BigDecimal rate = DigitUtil.precentDown(ratepercent);
			BigDecimal price = DigitUtil.mutiplyDown(DigitUtil.mutiplyDown(singlePrice, count.subtract(countback)),rate);
			if(!isBoxfeeDish(dishno) && !isTakeoutfeeDish(dishno)){
				unAccessoryAmount = unAccessoryAmount.add(price);
			}
			if(isBoxfeeDish(dishno)){
				boxfeeAmount = boxfeeAmount.add(price);
			}
		}
		/* 美团外卖拿的佣金是菜品金额的15% */
		BigDecimal commission = DigitUtil.mutiplyDown(unAccessoryAmount, DigitUtil.precentDown(new BigDecimal(15)));
		/* 到账金额 */
		BigDecimal postAmount =  unAccessoryAmount.subtract(commission).add(boxfeeAmount);
		
		if(freeMap.get(order.getPayNo()) == null){
			freeMap.put(order.getPayNo(), commission);
		}
		
		String schemeName = order.getSchemeName();
		if(StringUtils.hasText(schemeName)){
			schemeName = schemeName+",美团外卖到账"+postAmount+"元";
		}else{
			schemeName = "美团外卖到账"+postAmount+"元";
		}
		
		order.setSchemeName(schemeName);
		preserveOAR(commission,AccountCode.FREE_ONLINE,order); //餐厅补贴的金额就是给美团外卖的佣金
		preserveOAR(postAmount,AccountCode.MTWM,order);
	}

}
