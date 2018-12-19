package banger.service.impl;

import banger.common.tools.StringUtil;
import banger.dao.intf.IApplyInfoDao;
import banger.dao.intf.IMonitorInfoDao;
import banger.dao.intf.IRepayPlanInfoDao;
import banger.domain.enumerate.LoanCollectionState;
import banger.domain.enumerate.LoanMonitorState;
import banger.domain.enumerate.LoanProcessTypeEnum;
import banger.domain.loan.LoanApplyInfo;
import banger.domain.loan.LoanMonitorInfo;
import banger.domain.loan.LoanRepayPlanInfo;
import banger.domain.loan.LoanRepayPlanInfoExtend;
import banger.moduleIntf.ILoanAfterProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 贷后服务类
 * Created by zhusw on 2017/6/16.
 */
@Repository
public class LoanAfterService implements ILoanAfterProvider {
    @Autowired
    private IRepayPlanInfoDao repayPlanInfoDao;

    @Autowired
    private IMonitorInfoDao monitorInfoDao;

    @Autowired
    private IApplyInfoDao applyInfoDao;

    /**
     * app修改状态
     */
    public void updateLoanCollectionInfo(LoanRepayPlanInfo loanRepayPlanInfo){
        if(LoanCollectionState.COLLECTION_COMPLETE.equalState(loanRepayPlanInfo.getLoanCollectionState()))
            loanRepayPlanInfo.setLoanCollectionDate(new Date());
        repayPlanInfoDao.updateRepayPlanInfo(loanRepayPlanInfo);
        if(LoanCollectionState.COLLECTION_COMPLETE.equalState(loanRepayPlanInfo.getLoanCollectionState())){
            this.updateLoanRepayPlanState(loanRepayPlanInfo);
        }
    }

    /**
     * 得到第一条，还款计划
     * @param loanId
     * @return
     */
    public LoanRepayPlanInfo getTopLoanRepayPlanInfo(Integer loanId){
        return repayPlanInfoDao.getTopLoanRepayPlanInfo(loanId);
    }

    /**
     * 修改监控信息
     * @param monitorInfo
     */
    public void updateLoanMonitorInfo(LoanMonitorInfo monitorInfo){
        if(LoanMonitorState.MONITOR_COMPLETE.equalState(monitorInfo.getLoanMonitorState()))
            monitorInfo.setLoanCompleteDate(new Date());
        monitorInfoDao.updateMonitorInfo(monitorInfo);
        if(LoanMonitorState.MONITOR_COMPLETE.equalState(monitorInfo.getLoanMonitorState())){
            this.updateLoanMonitorState(monitorInfo);
        }
    }

    /**
     * 得到第一条，监控信息
     * @param loanId
     * @return
     */
    public LoanMonitorInfo getTopLoanMonitorInfo(Integer loanId){
        return monitorInfoDao.getTopLoanMonitorInfo(loanId);
    }

    /**
     * 得到监控信息
     * @param loanId
     * @return
     */
    public List<LoanMonitorInfo> getLoanMonitorInfoListByLoanId(Integer loanId){
        return monitorInfoDao.getLoanMonitorInfoListByLoanId(loanId);
    }

    /**
     * 得到还款计划
     * @param loanId
     * @return
     */
    public List<LoanRepayPlanInfo> getLoanRepayPlanInfoListByLoanId(Integer loanId){
        List<LoanRepayPlanInfoExtend> repayPlanInfoExtends =  repayPlanInfoDao.queryLoanRepayPlanInfoByLoanId(loanId, null, LoanProcessTypeEnum.APPROVAL.type);
        List<LoanRepayPlanInfo> repayPlanInfos = new ArrayList<LoanRepayPlanInfo>();
        for (LoanRepayPlanInfoExtend repayPlanInfoExtend : repayPlanInfoExtends) {
            repayPlanInfos.add(repayPlanInfoExtend);
        }
        return repayPlanInfos;
    }

    /**
     * 更新贷款的监控状态
     * @param monitorInfo
     */
    public void updateLoanMonitorState(LoanMonitorInfo monitorInfo){
        Integer loanId = monitorInfo.getLoanId();
        LoanMonitorInfo topMonitorInfo = getTopLoanMonitorInfo(loanId);
        if(topMonitorInfo==null){
            applyInfoDao.updateLoanMonitorState(loanId,LoanMonitorState.MONITOR_COMPLETE.state,monitorInfo.getLoanMonitorType(),new Date());
        }else{
            applyInfoDao.updateLoanMonitorState(loanId,topMonitorInfo.getLoanMonitorState(),topMonitorInfo.getLoanMonitorType(),topMonitorInfo.getLoanMonitorDate());
        }
        //applyInfoDao.updateLoanMonitorState(loanId,LoanMonitorState.MONITOR_COMPLETE.state,monitorInfo.getLoanMonitorType(),new Date());
    }

    /**
     * 更新贷款的催收状态
     * @param repayPlanInfo
     */
    public void updateLoanRepayPlanState(LoanRepayPlanInfo repayPlanInfo){
        Integer loanId = repayPlanInfo.getLoanId();
        LoanRepayPlanInfo topRepayPlanInfo = getTopLoanRepayPlanInfo(loanId);
        if(topRepayPlanInfo==null){
            applyInfoDao.updateLoanCollectionState(loanId,LoanCollectionState.COLLECTION_COMPLETE.state,new Date(),null);
        }else{
            applyInfoDao.updateLoanCollectionState(loanId,topRepayPlanInfo.getLoanCollectionState(),topRepayPlanInfo.getLoanCollectionDate(),topRepayPlanInfo.getLoanRepayPlanDate());
        }
    }

    /**
     * 修改贷款状态
     * @param loanId
     * @param afterState
     */
    public void updateLoanAfterState(Integer loanId,String afterState){
        if(StringUtil.isNotEmpty(afterState)) {
            applyInfoDao.updateLoanAfterState(loanId, afterState);
        }
    }

}
