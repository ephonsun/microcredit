package banger.dao.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.loan.LoanCurrentAuditStatus;

/**
 * 审批进度审核状态表数据访问接口
 */
public interface ICurrentAuditStatusDao {

	/**
	 * 新增审批进度审核状态表
	 * @param currentAuditStatus 实体对像
	 */
	void insertCurrentAuditStatus(LoanCurrentAuditStatus currentAuditStatus);

	/**
	 *修改审批进度审核状态表
	 * @param currentAuditStatus 实体对像
	 */
	void updateCurrentAuditStatus(LoanCurrentAuditStatus currentAuditStatus);

	/**
	 * 通过主键删除审批进度审核状态表
	 * @param id 主键Id
	 */
	void deleteCurrentAuditStatusById(Integer id);

	/**
	 * 通过主键得到审批进度审核状态表
	 * @param id 主键Id
	 */
	LoanCurrentAuditStatus getCurrentAuditStatusById(Integer id);

	/**
	 * 查询审批进度审核状态表
	 * @param condition 查询条件
	 * @return
	 */
	List<LoanCurrentAuditStatus> queryCurrentAuditStatusList(Map<String,Object> condition);

	/**
	 * 分页查询审批进度审核状态表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<LoanCurrentAuditStatus> queryCurrentAuditStatusList(Map<String,Object> condition,IPageSize page);

	/**
	 * 重置所有审批中的贷款，通过流程ID
	 * @param flowId
	 */
	void resetLoanFlowAuditStatusByFlowId(Integer flowId);

	/**
	 * 重置所有审批中的贷款，通过流程ID
	 * @param processId
	 */
	void resetLoanFlowAuditStatusByProcessId(Integer processId);

	/**
	 * 审批驳回时，更新审批人员的所有信息不可用
	 * @param loanId
	 */
	void updateAuditStatusByLoanId(Integer loanId);

	/**
	 * 得到当前审核状态
	 * @param loanId
	 * @param processId
	 * @return
	 */
	List<LoanCurrentAuditStatus> getLoanAuditStatusListById(Integer loanId,Integer processId);


	void deleteAuditStatusByLoanId(Integer loanId);

}
