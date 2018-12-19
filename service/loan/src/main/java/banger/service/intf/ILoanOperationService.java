package banger.service.intf;

import banger.domain.enumerate.LoanOperationType;

/**
 * 贷款作操日志封装
 * Created by zhusw on 2017/6/12.
 */
public interface ILoanOperationService {

    /**
     * 新增贷款操作日志
     * @param loanId
     * @param operationType
     * @param content
     * @param userId
     */
    void addLoanOperation(Integer loanId,LoanOperationType operationType,String content,Integer userId);

    /**
     * 新增贷款操作日志
     * @param loanId
     * @param operationName
     * @param content
     * @param userId
     */
    void addLoanOperation(Integer loanId,String operationName, String content,Integer userId, String processType);

}
