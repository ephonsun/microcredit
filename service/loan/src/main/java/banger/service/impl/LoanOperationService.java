package banger.service.impl;

import banger.dao.intf.IActionHistoryDao;
import banger.domain.enumerate.LoanOperationType;
import banger.domain.loan.LoanActionHistory;
import banger.domain.permission.PmsUser;
import banger.moduleIntf.ILoanOperationProvider;
import banger.moduleIntf.IPermissionModule;
import banger.service.intf.ILoanOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 贷款作操日志封装
 * Created by zhusw on 2017/6/12.
 */
@Repository
public class LoanOperationService implements ILoanOperationService,ILoanOperationProvider {

    @Resource
    private IPermissionModule permissionModule;

    @Autowired
    private IActionHistoryDao actionHistoryDao;

    /**
     * 新增贷款操作日志
     * @param loanId
     * @param operationType
     * @param content
     * @param userId
     */
    public void addLoanOperation(Integer loanId,LoanOperationType operationType, String content,Integer userId){
        PmsUser user = permissionModule.getPmsUserByUserId(userId);
        if(user!=null){
            LoanActionHistory action = new LoanActionHistory();
            action.setActionName(operationType.typeName);
            action.setActionContent(content);
            action.setActionUserAccount(user.getUserAccount());
            action.setActionUserName(user.getUserName());
            action.setActionDate(new Date());
            action.setLoanId(loanId);
            actionHistoryDao.insertActionHistory(action);
        }
    }

    /**
     * 新增贷款操作日志
     * @param loanId
     * @param operationName
     * @param content
     * @param userId
     */
    public void addLoanOperation(Integer loanId,String operationName, String content,Integer userId, String processType){
        PmsUser user = permissionModule.getPmsUserByUserId(userId);
        if(user!=null){
            LoanActionHistory action = new LoanActionHistory();
            action.setLoanProcessType(processType);
            action.setActionName(operationName);
            action.setActionContent(content);
            action.setActionUserAccount(user.getUserAccount());
            action.setActionUserName(user.getUserName());
            action.setActionDate(new Date());
            action.setLoanId(loanId);
            actionHistoryDao.insertActionHistory(action);
        }
    }

}
