package com.rci.service.filter;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.rci.bean.entity.Order;
import com.rci.bean.entity.SchemeType;
import com.rci.enums.BusinessEnums.OrderFramework;
import com.rci.enums.BusinessEnums.PaymodeCode;
import com.rci.service.filter.parser.IPlatformParser;
import com.rci.service.filter.parser.PlatformParserBeanFactory;
import com.rci.tools.DateUtil;

/**
 * 美团外卖
 * @author zj
 *
 */
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class MTWMFilter extends AbstractFilter {
	private static final Log logger = LogFactory.getLog(MTWMFilter.class);
	
	@Resource(name="platformParserBeanFactory")
	private PlatformParserBeanFactory beanFactory;
	
	private IPlatformParser parser;
	
	@Override
	public boolean support(Map<PaymodeCode, BigDecimal> paymodeMapping) {
		return paymodeMapping.containsKey(PaymodeCode.MTWM);
	}

	@Override
	public void generateScheme(Order order,FilterChain chain) {
		parseOrder(order,chain);
	}
	

	@Override
	protected Map<SchemeType, Integer> getSuitMap() {
		return null;
	}

	/* 
	 * @see com.rci.service.filter.AbstractFilter#validation(com.rci.bean.entity.Order)
	 */
	@Override
	protected void validation(Order order) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 该方法决定使用何种算法
	 */
	public void parseOrder(Order order, FilterChain context) {
		try{
			order.setFramework(OrderFramework.MTWM);
			String day = order.getDay();
			Date orderDate = DateUtil.parseDate(day,"yyyyMMdd");
			if(orderDate.before(DateUtil.parseDate("2016-01-18"))){
				parser = beanFactory.getParser("MtwmParser1");
			}else{
				parser = beanFactory.getParser("MtwmParser2");
			}
			parser.doParse(order);
		}catch(ParseException pe){
			logger.error("订单日期解析错误",pe);
			return;
		}
	}
}
