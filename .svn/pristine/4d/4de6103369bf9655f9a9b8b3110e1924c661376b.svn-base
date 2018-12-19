/*
 * banger Inc.
 * Copyright (c) 2009-2012 All Rights Reserved.
 * ToDo       :QuartzJob基类
 * Author     :liyb
 * Create Date:2014-5-6
 */
package banger.quartz.module;
import org.quartz.JobExecutionContext;
import org.quartz.StatefulJob;

import banger.framework.spring.SpringContext;
import banger.quartz.service.IQuartzJobService;

/**
 * @author liyb
 * @version $Id: QuartzJob.java,v 0.1 2014-5-6 下午02:14:10 liyb Exp $
 */
public abstract class QuartzJob implements StatefulJob {
    
    public void CallBackJob(JobExecutionContext context, boolean bool){
        IQuartzJobService quartzJobService = (IQuartzJobService) SpringContext.instance().get("quartzJobService");
        if(quartzJobService!=null){
            quartzJobService.CallbackJob(context, bool);
        }
    }
}
