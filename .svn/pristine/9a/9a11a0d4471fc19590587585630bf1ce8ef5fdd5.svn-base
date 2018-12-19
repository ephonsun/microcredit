package banger.moduleIntf;

import banger.domain.loan.LoanMonitorInfo;

public interface IMonitorInfoPrivder {

    /**
     * 新增贷款监控信息表
     * @param monitorInfo 实体对像
     * @param loginUserId 登入用户Id
     */
    void insertMonitorInfo(LoanMonitorInfo monitorInfo, Integer loginUserId);

    /**
     * 得到第一条，监控信息
     * @param loanId
     * @return
     */
    LoanMonitorInfo getTopLoanMonitorInfo(Integer loanId);

    /**
     * 新增临时监控时，获取监控id
     * @return
     */
    Integer getMonitorId();

}
