/**
 * 
 */
package com.rci.tools;

import java.lang.reflect.Field;

import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.AopProxy;

/**
 * 
 * remark (备注):判断一个Bean是否是AOP代理对象
 *
 * @author zj
 *	
 * 项目名称：ReconciliationPro
 *
 * 类名称：AOPTargetUtil
 *
 * 包名称：com.rci.tools
 *
 * Create Time: 2015年6月23日 上午9:56:43
 *
 */
public class AOPTargetUtil{
	/**
	 * 
	 * @Function 获取由CGLIB代理的目标类
	 * @param proxy
	 * @return
	 * @throws Exception
	 * @author zj
	 * @Date 2014年7月29日
	 */
	public static Object getCglibProxyTargetObject(Object proxy) throws Exception {
		Field h = proxy.getClass().getDeclaredField("CGLIB$CALLBACK_0");
		h.setAccessible(true);
		Object dynamicAdvisedInterceptor = h.get(proxy);

		Field advised = dynamicAdvisedInterceptor.getClass().getDeclaredField("advised");
		advised.setAccessible(true);

		Object target = ((AdvisedSupport)advised.get(dynamicAdvisedInterceptor)).getTargetSource().getTarget();

		return target;
	}

	/**
	 * 
	 * @Function 获取由JDK动态代理的目标类
	 * @param proxy
	 * @return
	 * @throws Exception
	 * @author zj
	 * @Date 2014年7月29日
	 */
	public static Object getJDKDynamicProxy(Object proxy) throws Exception{
		Field h = proxy.getClass().getSuperclass().getDeclaredField("h");
		h.setAccessible(true);
		AopProxy aopProxy = (AopProxy) h.get(proxy);

		Field advised = aopProxy.getClass().getDeclaredField("advised");
		advised.setAccessible(true);

		Object target = ((AdvisedSupport)advised.get(aopProxy)).getTargetSource().getTarget();

		return target;
	}
}
