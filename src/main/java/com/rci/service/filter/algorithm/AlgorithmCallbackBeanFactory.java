package com.rci.service.filter.algorithm;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

public class AlgorithmCallbackBeanFactory implements BeanFactoryAware {
	private BeanFactory beanFactory;
	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}
	
	public AlgorithmCallback getAlgorithmBean(String beanName){
		return (AlgorithmCallback) beanFactory.getBean(beanName);
	}

}
