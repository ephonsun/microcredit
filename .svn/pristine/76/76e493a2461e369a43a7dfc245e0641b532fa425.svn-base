/*
 * banger Inc.
 * Copyright (c) 2009-2012 All Rights Reserved.
 * ToDo       :This is Class Description...
 * Author     :liyb
 * Create Date:2014-5-5
 */
package banger.quartz.listener;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.quartz.SchedulerException;
import banger.framework.spring.SpringContext;
import banger.quartz.util.QuartzUtil;

/**
 * @author liyb
 * @version $Id: QuartzJobListener.java,v 0.1 2014-5-5 下午02:08:36 liyb Exp $
 */
public class QuartzJobListener implements JobListener {
    Log logger = LogFactory.getLog(QuartzJobListener.class);

    private String listenerName = "QuartzJobListener";  
    
    public QuartzJobListener(String listenerName) {  
        this.listenerName = listenerName;  
    }  
      
    public String getName() {  
        return this.listenerName;  
    }  

    //job执行之前调用
    public void jobToBeExecuted(JobExecutionContext context) {
    }

    //Job执行被拒接时调用
    public void jobExecutionVetoed(JobExecutionContext context) {
        String jobName = context.getJobDetail().getName();
        logger.info(jobName + " was vetoed and not executed()");
    }

    //job执行之后调用
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        String jobName = context.getJobDetail().getName();
        
        if(context.getJobDetail().getJobDataMap().get(jobName+"_Flag")!=null){
            boolean b = Boolean.parseBoolean(context.getJobDetail().getJobDataMap().get(jobName+"_Flag").toString());
            //立即重新运行
            //jobException.setRefireImmediately(true);//打火策略
            String job = context.getJobDetail().getJobDataMap().get(jobName+"_bean").toString();
            String cornTime = context.getJobDetail().getJobDataMap().get(jobName+"_cornTime").toString();
            //返回job实例
            Object obj = SpringContext.instance().get(job);
            Map<Object, Object> params = new HashMap<Object, Object>();
            params.put(jobName+"_bean", job);
            params.put(jobName+"_cornTime", cornTime);
            
            //延迟处理
            String delay="";
            if(context.getJobDetail().getJobDataMap().get(jobName+"_delay")!=null){
                delay = context.getJobDetail().getJobDataMap().get(jobName+"_delay").toString();
                params.put(jobName+"_delay", delay);
            }
            
            try {
                if(!b){
                    //重设时间
                    String cron="";
                    if(delay!=""){
                        cron = "0 "+QuartzUtil.getDateMinut(Integer.parseInt(delay))+" * * ?";
                    }else{
                        cron = cornTime;
                    }
                    QuartzUtil.removeJob(jobName);
                    QuartzUtil.addJob(jobName, (Job)obj, cron, params);
                }else{
                    //恢复初始时间设定
                    QuartzUtil.removeJob(jobName);
                    QuartzUtil.addJob(jobName, (Job)obj, cornTime, params);
                }
            } catch (SchedulerException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
