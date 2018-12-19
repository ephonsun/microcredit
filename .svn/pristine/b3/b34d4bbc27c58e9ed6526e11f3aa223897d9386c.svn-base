package banger.moduleIntf;

import banger.domain.loan.LoanMonitorInfo;
import banger.domain.loan.LoanRepayPlanInfo;

import java.util.List;

/**
 * 贷后接口
 * Created by zhusw on 2017/6/16.
 */
public interface ILoanAfterProvider {

    /**
     * app修改状态
     */
    void updateLoanCollectionInfo(LoanRepayPlanInfo loanRepayPlanInfo);

    /**
     * 得到第一条，还款计划
     * @param loanId
     * @return
     */
    LoanRepayPlanInfo getTopLoanRepayPlanInfo(Integer loanId);

    /**
     * 修改监控信息
     * @param monitorInfo
     */
    void updateLoanMonitorInfo(LoanMonitorInfo monitorInfo);

    /**
     * 得到第一条，监控信息
     * @param loanId
     * @return
     */
    LoanMonitorInfo getTopLoanMonitorInfo(Integer loanId);

    /**
     * 得到监控信息
     * @param loanId
     * @return
     */
    List<LoanMonitorInfo> getLoanMonitorInfoListByLoanId(Integer loanId);

    /**
     * 得到还款计划
     * @param loanId
     * @return
     */
    List<LoanRepayPlanInfo> getLoanRepayPlanInfoListByLoanId(Integer loanId);

    /**
     * 更新贷款的监控状态
     * @param monitorInfo
     */
    void updateLoanMonitorState(LoanMonitorInfo monitorInfo);

    /**
     * 修改贷款状态
     * @param loanId
     * @param afterState
     */
    void updateLoanAfterState(Integer loanId,String afterState);

}
