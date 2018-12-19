package banger.dao.impl;

import banger.dao.intf.IApplyInfoDao;
import banger.dao.intf.ILoanAuditProcessDao;
import banger.domain.loan.LoanApplyInfo;
import banger.domain.loan.LoanAuditProcess;
import banger.framework.dao.PageSizeDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 安卓审核状态
 * Created by zhusw on 2017/6/27.
 */
@Repository
public class LoanAuditProcessDao extends PageSizeDao<LoanAuditProcess> implements ILoanAuditProcessDao {

     /**
     * 查询审核状态
     * @param loanId
     * @return
     */
     public List<LoanAuditProcess> getLoanAuditProcessByLoanId(Integer loanId){
         return (List<LoanAuditProcess>)this.queryEntities("getLoanAuditProcessByLoanId",loanId);
     }

}
