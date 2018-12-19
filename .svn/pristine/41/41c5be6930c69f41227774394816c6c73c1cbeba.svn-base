/*
 * banger Inc.
 * Copyright (c) 2009-2012 All Rights Reserved.
 * ToDo       :This is Class Description...
 * Author     :liyb
 * Create Date:2014-4-23
 */
package banger.quartz.job;

import java.io.File;
import java.util.Calendar;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import banger.quartz.module.QuartzJob;

/**
 * @author liyb
 * @version $Id: AutoCUstomerJob.java,v 0.1 2014-4-23 下午04:16:50 liyb Exp $
 */
public class AutoCustomerJob extends QuartzJob{

    /**
     * @param arg0
     * @throws JobExecutionException
     * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
     */
    @SuppressWarnings({ "deprecation", "unused" })
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
        System.err.println(Calendar.getInstance().getTime().toLocaleString().toString()+"--------》自动任务开始执行");
        File file = new File("F:\\SVN.txt");
        boolean b=file.exists();
//        CallBackJob(arg0,b);
    }

}
