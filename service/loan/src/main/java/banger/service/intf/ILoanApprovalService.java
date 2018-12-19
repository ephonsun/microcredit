package banger.service.intf;

import banger.domain.config.ModeConfigFile;
import banger.domain.loan.LoanApplyInfo;
import banger.domain.loan.LoanApplyInfo_Web_Query;
import banger.moduleIntf.IAutoFieldProvider;
import banger.moduleIntf.IConfigModule;
import banger.moduleIntf.ITableInfoProvider;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/21.
 */
public interface ILoanApprovalService {

    /**
     * 调查提交审批时，复制还款计划到审批环节，复制调查结论到审批，保存提交审批操作日志
     */
    String saveExamine(Integer loanId,String paramId,String processId,String flowId,String[] userIds,String loginUserId);


    /**
     * 提交审批保存
     * @param map 审批信息，如果有
     * @param loanId 贷款id
     * @param processId
     *@param moreReview 是否多人审批，多人审批不保存审批信息，只更新审批状态
     * @param loginUserId 操作人id   @return
     */
    boolean saveLoanApprovalInfo(Map<String, Object> map, Integer loanId, Integer processId, String moreReview, String last, Integer loginUserId);

    /**
     * 根据贷款id获取流程map【“processId”，“一”，“processId”，“二”，“processId”，“十二”】
     * @param loanId
     * @return
     */
    LinkedHashMap<String, String> getProcessMapByLoanId(Integer loanId);


    void doExportFromRow(HttpServletRequest request, HttpServletResponse response, IApplyInfoService applyInfoService, IConfigModule configModule, ITableInfoProvider tableInfoProvider, IAutoFieldProvider autoFieldProvider, IScoreDetailInfoService scoreDetailInfoService, LoanApplyInfo_Web_Query loanApplyInfo, ModeConfigFile configFile);

    /**
     * 提交审批校验还款计划
     * @param loanId
     * @param repaymentMode
     * @param proposalAmount
     * @param proposalLimit
     * @param proposalRatio
     * @return
     */
    String checkPlansForApproval(String loanId, String repaymentMode, String proposalAmount, String proposalLimit, String proposalRatio);
}
