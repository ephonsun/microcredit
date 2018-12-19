package banger.service.impl;


import banger.dao.intf.IApplyInfoDao;
import banger.dao.intf.ILoanMonitorInfoJobDao;
import banger.domain.loan.LoanApplyInfo;
import banger.domain.loan.LoanMonitorInfo;
import banger.framework.spring.SpringContext;
import banger.framework.util.BeanUtil;
import banger.framework.util.DateUtil;
import banger.moduleIntf.IBasicConfigProvider;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 定时任务类
 * Created by wanggl on 2017/6/22.
 */
public class LoanMonitorInfoJobService implements Job {

//    private IBasicConfigProvider iBasicConfigProvider = (IBasicConfigProvider) BeanUtil.getJustOneBeanByClass(IBasicConfigProvider.class);
//    @Autowired
//    private IBasicConfigProvider iBasicConfigProvider;
    /**
     * @param context
     * @throws JobExecutionException
     */
    @SuppressWarnings("deprecation")
    public void execute(JobExecutionContext context) throws JobExecutionException {

        ILoanMonitorInfoJobDao loanMonitorInfoJobDao = (ILoanMonitorInfoJobDao) SpringContext.instance().get("loanMonitorInfoJobDao");
        IApplyInfoDao applyInfoDao = (IApplyInfoDao) SpringContext.instance().get("applyInfoDao");
        Map<String,Object> condition = new HashMap<String,Object>();

        Date now = DateUtil.getCurrentDate();

        //批处理 首次监控
        Integer firstMonitorDay = applyInfoDao.queryFirstMonitorDay();
        if (firstMonitorDay == null) firstMonitorDay = 30;
        condition.put("configValue", firstMonitorDay);
        Date firstMonitorDate = DateUtil.addDay(now,-firstMonitorDay);
        condition.put("firstMonitorDate", firstMonitorDate);

        loanMonitorInfoJobDao.batchProcessFirst(condition);
        loanMonitorInfoJobDao.updateLoanApply();
        //批处理 常规监控 贷后正常
        condition.clear();
        Integer normalMonitorDay = applyInfoDao.queryNormalMonitorDay();
        if (normalMonitorDay == null) normalMonitorDay = 30;
        condition.put("configValue", normalMonitorDay);
        Date normalMonitorDate = DateUtil.addDay(now,-normalMonitorDay);
        condition.put("normalMonitorDate", normalMonitorDate);

        loanMonitorInfoJobDao.batchProcessNormal(condition);
        loanMonitorInfoJobDao.updateLoanApply();
        //批处理 常规监控 贷后关注
        condition.clear();
        Integer concernMonitorDay = applyInfoDao.queryConcernMonitorDay();
        if (concernMonitorDay == null) concernMonitorDay = 7;
        condition.put("configValue", concernMonitorDay);
        Date concernMonitorDate = DateUtil.addDay(now,-concernMonitorDay);
        condition.put("concernMonitorDate", concernMonitorDate);

        loanMonitorInfoJobDao.batchProcessConcern(condition);
        loanMonitorInfoJobDao.updateLoanApply();

        //更新客户经理分配受限表
        loanMonitorInfoJobDao.updateLoanAllotLimit();

        //设置客户经理受限状态默认值，默认为0，不受限
        loanMonitorInfoJobDao.setDefaultLimitState();

        //更新客户经理分配受限状态
        condition.clear();
        firstMonitorDay = firstMonitorDay + 1;
        Date firstMonitorOutDate = DateUtil.addDay(now,-firstMonitorDay);
        condition.put("firstMonitorOutDate", firstMonitorOutDate);
        loanMonitorInfoJobDao.updateLoanAllotLimitState(condition);


//        if (list != null && list.size() > 0) {
//            for (LoanMonitorInfo loanMonitorInfo : list) {
//                LoanApplyInfo applyInfoById = applyInfoDao.getApplyInfoById(loanMonitorInfo.getLoanId());
//                applyInfoById.setLoanMonitorState("Monitor");
//                applyInfoById.setLoanMonitorType("firstMonitor");
//                applyInfoDao.updateApplyInfo(applyInfoById);
//            }
//        }
    }

}
