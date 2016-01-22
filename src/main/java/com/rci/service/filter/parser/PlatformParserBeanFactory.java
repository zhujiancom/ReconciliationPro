package com.rci.service.filter.parser;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Component;

@Component("platformParserBeanFactory")
public class PlatformParserBeanFactory implements BeanFactoryAware {
	private BeanFactory beanFactory;
	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}
	
	public IPlatformParser getParser(String beanName){
		return beanFactory.getBean(beanName, IPlatformParser.class);
	}

}
