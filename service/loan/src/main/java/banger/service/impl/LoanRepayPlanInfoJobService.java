package banger.service.impl;

import banger.dao.intf.IApplyInfoDao;
import banger.dao.intf.ILoanRepayPlanInfoJobDao;
import banger.dao.intf.IRepayPlanInfoDao;
import banger.domain.loan.LoanApplyInfo;
import banger.domain.loan.LoanRepayPlanInfo;
import banger.framework.spring.SpringContext;
import banger.moduleIntf.IBasicConfigProvider;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**贷款催收任务类
 * Created by wanggl on 2017/6/29.
 */
public class LoanRepayPlanInfoJobService implements Job {
//    @Resource
//    private IBasicConfigProvider iBasicConfigProvider;

    public void execute(JobExecutionContext context) throws JobExecutionException {

        ILoanRepayPlanInfoJobDao loanRepayPlanInfoJobDao = (ILoanRepayPlanInfoJobDao) SpringContext.instance().get("loanRepayPlanInfoJobDao");
        IRepayPlanInfoDao repayPlanInfoDao = (IRepayPlanInfoDao) SpringContext.instance().get("repayPlanInfoDao");
        IApplyInfoDao applyInfoDao = (IApplyInfoDao) SpringContext.instance().get("applyInfoDao");

        Integer day = applyInfoDao.queryRepayPlanValue();
        //获取要催收的所有贷款
        List<LoanRepayPlanInfo> list = loanRepayPlanInfoJobDao.getUpdaterRepayPlanInfoes(day);
        //更新
        if (list != null && list.size() > 0) {
            for (LoanRepayPlanInfo loanRepayPlanInfo : list) {
                //更新催收表
                loanRepayPlanInfo.setLoanCollectionState("Collection");
                //更新时间 更新用户
                loanRepayPlanInfo.setUpdateDate(new Date());
                loanRepayPlanInfo.setUpdateUser(1);
                repayPlanInfoDao.updateRepayPlanInfo(loanRepayPlanInfo);

                //更新主表
                LoanApplyInfo applyInfo = new LoanApplyInfo();
                applyInfo.setLoanId(loanRepayPlanInfo.getLoanId());
                applyInfo.setLoanCollectionState("Collection");
                applyInfo.setLoanCollectionDate(loanRepayPlanInfo.getLoanCollectionDate());
                applyInfo.setLoanRepayAmount(loanRepayPlanInfo.getLoanRepayAmount());
                applyInfo.setLoanRepayDate(loanRepayPlanInfo.getLoanRepayPlanDate());
                applyInfoDao.updateApplyInfo(applyInfo);

            }
        }
    }
}
