package com.rci.service.calculatecenter.filter;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rci.service.calculatecenter.ParameterValue;

@Component("defaultFilterChain")
public class DefaultPaymodeFilterChain implements PaymodeFilterChain,InitializingBean{
	private static final Log logger = LogFactory.getLog(DefaultPaymodeFilterChain.class);
	@Autowired
	private  List<PaymodeFilter> filters;
	//记录链条执行位置指针
	private int pos;
	
	@Override
	public void doFilter(ParameterValue value) {
		if(pos == filters.size()){//执行到链条末尾
			logger.debug("order["+value.getPayNo()+"] -  链条执行完成！");
			pos = 0;
			return;
		}
		PaymodeFilter filter = filters.get(pos);
		pos++;
		filter.doFilter(value,this);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Collections.sort(filters, new Comparator<Indexable>() {

			@Override
			public int compare(Indexable f1, Indexable f2) {
				if(f1.index() - f2.index() > 0){
					return 1;
				}
				return -1;
			}
		});
		for(Indexable filter:filters){
			logger.debug("初始化链条排列顺序-"+filter.getClass().getName());
		}
	}

}
