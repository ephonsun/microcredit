/*
 * xuhj Inc.
 * Copyright (c) 2012-2100 All Rights Reserved.
 * ToDo       :异常拦截器
 * Author     :徐红军
 * Create Date: 2012-12-27
 */
package banger.common.interceptor;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.ThrowsAdvice;

public class ExceptionLogInterceptor implements ThrowsAdvice  {
	
	public void afterThrowing(Method method, Object[] args, Object target,
			Exception e) {
		Logger logger = LoggerFactory.getLogger(target.getClass());
        logger.error("错误信息："+e.getMessage(),e);
	}
	
	public void afterThrowing(Method method, Object[] args, Object target,
			NumberFormatException e) {
		Logger logger = LoggerFactory.getLogger(target.getClass());
		logger.error("错误信息："+e.getMessage(),e);
	} 
}
