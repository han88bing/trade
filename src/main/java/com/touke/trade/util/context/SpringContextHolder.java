package com.touke.trade.util.context;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.context.support.ServletContextResource;

import javax.servlet.ServletContext;


/**
 * bean 容器
 */
@Slf4j
@Lazy(value = false)
public class SpringContextHolder implements ApplicationContextAware, DisposableBean {


	private static ApplicationContext applicationContext = null;

	/**
	 * 取得存储在静态变量中的ApplicationContext.
	 */
	public static ApplicationContext getApplicationContext() {
		checkApplicationContext();
		return applicationContext;
	}

	/**
	 * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
	 */
	public static <T> T getBean(String name) {
		checkApplicationContext();
		return (T) applicationContext.getBean(name);
	}

	/**
	 * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
	 */
	public static <T> T getBean(Class<T> clazz) {
		checkApplicationContext();
		return (T) applicationContext.getBean(clazz);
	}



	/**
	 * 清除applicationContext静态变量.
	 */
	public static void cleanApplicationContext() {
		applicationContext = null;
	}

	private static void checkApplicationContext() {
		if (applicationContext == null) {
			throw new IllegalStateException("pplicaitonContext未注入,请在applicationContext.xml中定义SpringContextHolder");
		}
	} 
	
	public static ServletContext getServletContext(){
		checkApplicationContext();
		return ((ServletContextResource)applicationContext.getResource("")).getServletContext();
	}

	@Override
	public void destroy() throws Exception {
		SpringContextHolder.cleanApplicationContext();
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		if (null != SpringContextHolder.applicationContext) {
			log.warn("SpringContextHolder中的ApplicationContext被覆盖, 原有ApplicationContext为:" + SpringContextHolder.applicationContext);
		}
		SpringContextHolder.applicationContext = applicationContext;
	}
}
