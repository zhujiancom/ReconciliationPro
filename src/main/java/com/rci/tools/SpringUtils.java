package com.rci.tools;

import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringUtils implements ApplicationContextAware {

	private ApplicationContext applicationContext;
    private static ApplicationContext staticapplicationcontext;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;

        SpringUtils.staticapplicationcontext = applicationContext;
    }

    public static Object getBean(String name) {
        if (SpringUtils.staticapplicationcontext != null) {
            return SpringUtils.staticapplicationcontext.getBean(name);
        }
        return null;

    }

    public static <T> Map<String, T> getBeansForType(Class<T> clazz){
        if(SpringUtils.staticapplicationcontext!=null){
            return SpringUtils.staticapplicationcontext.getBeansOfType(clazz);
        }
        return null;
    }
}
