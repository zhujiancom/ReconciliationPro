package com.rci;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainServiceEntry {
	private static final Log logger = LogFactory.getLog(MainServiceEntry.class);

	public static void main(String[] args) {
		try{
			new ClassPathXmlApplicationContext("spring/spring-common.xml","spring/spring-db.xml");
		}catch(Exception ex){
			logger.error(ex);
		}
	}

}
