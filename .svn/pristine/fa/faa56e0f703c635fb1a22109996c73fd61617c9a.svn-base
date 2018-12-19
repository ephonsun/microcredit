package banger.moduleIntf;

import banger.domain.loan.LoanCurrentAuditStatus;
import banger.domain.loan.LoanExamine;

import java.util.List;
import java.util.Map;

/**
 * 调查提交审批
 * Created by zhusw on 2017/7/3.
 */
public interface ILoanAuditProvider {

    /**
     * 得到调查审核环节和人员信息
     * @param loanId 贷款Id
     * @return
     */
    LoanExamine getLoanExamine(Integer loanId);

    /**
     * 得到下一个调查审核环节和人员信息
     * @param loanId 贷款Id
     * @return
     */
    LoanExamine getNextLoanExamine(Integer loanId);
    /**
     * 得到调查审核环节和人员信息
     * @param loanId 贷款Id
     * @param filterSameUsers 是否过滤重复的人
     * @return
     */
    LoanExamine getNextLoanExamine(Integer loanId, boolean filterSameUsers);
    /**
     * 保存审批计录
     * @param auditList
     */
    void saveLoanAuditFlowInfo(List<LoanCurrentAuditStatus> auditList);

    /**
     * 根据条件查询审批记录
     * @param condition
     */
    List<LoanCurrentAuditStatus> queryLoanAuditByCondition(Map<String, Object> condition);

}
