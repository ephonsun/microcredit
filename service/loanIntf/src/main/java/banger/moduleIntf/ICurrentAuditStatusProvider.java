package banger.moduleIntf;

import banger.domain.loan.LoanCurrentAuditStatus;

import java.util.List;
import java.util.Map;

public interface ICurrentAuditStatusProvider {

    /**
     * 查询审批进度审核状态表
     * @param condition 查询条件
     * @return
     */
    List<LoanCurrentAuditStatus> queryCurrentAuditStatusList(Map<String,Object> condition);

    /**
     *修改审批进度审核状态表
     * @param currentAuditStatus 实体对像
     * @param loginUserId 登入用户Id
     */
    void updateCurrentAuditStatus(LoanCurrentAuditStatus currentAuditStatus,Integer loginUserId);
}
