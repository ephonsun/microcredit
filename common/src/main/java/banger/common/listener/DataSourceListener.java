/*
 * banger Inc.
 * Copyright (c) 2009-2012 All Rights Reserved.
 * ToDo       :This is Class Description...
 * Author     :huyb
 * Create Date:2014-5-23
 */
package banger.common.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;

import banger.framework.sql.util.SqlHelper;
import org.slf4j.LoggerFactory;


/**
 * @author huyb
 * @version $Id: SqlListener.java,v 0.1 2014-5-23 上午7:10:10 huyb Exp $
 * 数据 库链接初始化
 */
public class DataSourceListener implements ServletContextListener{
	
	 private static final Logger logger = LoggerFactory.getLogger(DataSourceListener.class);
	 
	/**
	 * @param arg0
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
	}

	/**
	 * @param arg0
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		logger.info("DataSourceListener initialized");
		try{
			SqlHelper.testConnection();
		}catch(Exception e){
			e.printStackTrace();
		}
		logger.info("DataSourceListener initialize done");
	}

}
