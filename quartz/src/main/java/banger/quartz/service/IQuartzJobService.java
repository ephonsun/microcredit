/*
 * banger Inc.
 * Copyright (c) 2009-2012 All Rights Reserved.
 * ToDo       :定时任务接口
 * Author     :liyb
 * Create Date:2014-4-23
 */
package banger.quartz.service;

import org.quartz.JobExecutionContext;
import org.quartz.SchedulerException;

/**
 * @author liyb
 * @version $Id: IQuartzJobService.java,v 0.1 2014-4-23 上午09:43:45 liyb Exp $
 */
public interface IQuartzJobService {
    /**
     * 启动定时数据接口任务
     * @param config
     * @throws SchedulerException
     * @throws Exception
     */
    public void addAutoQuartzJob(String config) throws SchedulerException, Exception;
    
    /**
     * 启动时间特俗处理Job
     * @throws SchedulerException
     * @throws Exception
     */
    public void AutoReplaceCronJob(String config)throws SchedulerException, Exception;
    
    /**
     * 监听业务job处理状态
     * @param context job运行时的信息
     * @param bool 业务job处理结果
     */
    public void CallbackJob(JobExecutionContext context,boolean bool);
    
    /**
     * 停止全部Job
     */
    public void stopQuartzJob();
}
