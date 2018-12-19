package banger.service.impl;

import banger.dao.intf.IApplyInfoDao;
import banger.framework.spring.SpringContext;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Calendar;

/**
 * Created by ygb on 2018/3/18.
 */
public class OperationCodeJobService implements Job {

    @SuppressWarnings("deprecation")
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        IApplyInfoDao applyInfoDao = (IApplyInfoDao) SpringContext.instance().get("applyInfoDao");
        Calendar c = Calendar.getInstance();
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);
        if(month==1&&day==1) {
            applyInfoDao.resetOperationCode();
            applyInfoDao.resetSerialCode();
        }
    }
}
