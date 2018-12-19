package banger.service.intf;

import banger.domain.loan.LoanExamine;

/**
 * 贷款审核服务
 * Created by zhusw on 2017/6/19.
 */
public interface ILoanExamineService {

    /**
     * 得到调查审核环节和人员信息
     * @param loanId 贷款Id
     * @return
     */
    LoanExamine getLoanExamine(Integer loanId);

    /**
     * 得到调查审核环节和人员信息
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


}
