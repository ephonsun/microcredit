package banger.moduleIntf;

import banger.domain.loan.*;

import java.util.List;
import java.util.Map;

/**
 * 调查流程外部接口
 * Created by huangfz .
 */
public interface ILoanSurveryFlowProvider {

    /**
     * 根据贷款类型获得贷款类型信息
     * @param typeId
     * @return
     */
     LoanType getTypeById(Integer typeId);

    /**
     * 查询流程步骤明细表
     * @param condition 查询条件
     * @return
     */
    List<LoanFlowStepItem> queryFlowStepItemList(Map<String, Object> condition);

    /**
     * 查询调查流程步骤内容表
     * @param condition 查询条件
     * @return
     */
     List<LoanFlowStepContent> queryFlowStepContentList(Map<String,Object> condition);

    /**
     * 通过主键得到贷款申请表
     * @param loanId 主键Id
     */
    LoanApplyInfo getApplyInfoById(Integer loanId);

    /**
     * 得到所有有流程步骤的贷款类型ID列表,启用缓存
     * @return
     */
    List<LoanType> getAllLoanTypeList();

}
