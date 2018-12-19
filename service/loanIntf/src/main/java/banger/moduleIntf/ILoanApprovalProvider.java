package banger.moduleIntf;

import banger.domain.config.AutoBaseField;
import banger.domain.loan.*;
import banger.framework.collection.DataSet;
import banger.framework.collection.DataTable;
import banger.framework.pagesize.IPageSize;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface ILoanApprovalProvider {


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


	/**
	 * 根据流程id获取该审批流程的自定义字段
	 * @param processId
	 * @return
	 */
	List<LoanAuditTableFieldExtend> queryAuditTableFieldSelect(Integer processId);

	/**
	 * 根据贷款id获取审批主记录
	 * @param loanId
	 * @return
	 */
	DataTable getApprovalDataTableByLoanId(Integer loanId);

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
