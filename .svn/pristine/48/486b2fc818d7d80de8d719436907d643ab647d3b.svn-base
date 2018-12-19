/*
 * banger Inc.
 * Copyright (c) 2009-2012 All Rights Reserved.
 * ToDo       :This is Class Description...
 * Author     :liyb
 * Create Date:2014-5-7
 */
package banger.quartz.job;

import java.io.File;
import java.util.Calendar;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import banger.quartz.module.QuartzJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author liyb
 * @version $Id: AutoImportJob.java,v 0.1 2014-5-7 上午10:42:39 liyb Exp $
 */
public class AutoImportJob extends QuartzJob {
    private static final Logger logger = LoggerFactory.getLogger(AutoImportJob.class);

    /**
     * @param arg0
     * @throws JobExecutionException
     * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
     */
    @SuppressWarnings({ "deprecation", "unused" })
	public void execute(JobExecutionContext context) throws JobExecutionException {
        String txt="In　QuartzJob　-　executing　its　JOB　at "
            + Calendar.getInstance().getTime().toLocaleString().toString()+" by "+context.getTrigger().getName();
        logger.info(txt);
        File file = new File("F:\\SVN.txt");
        boolean b=file.exists();
//        CallBackJob(context,b);
    }
}
