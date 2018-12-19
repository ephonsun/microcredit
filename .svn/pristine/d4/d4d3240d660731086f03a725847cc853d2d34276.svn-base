package banger.service.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.loan.LoanCurrentAuditStatus;

/**
 * 审批进度审核状态表业务访问接口
 */
public interface ICurrentAuditStatusService {

	/**
	 * 新增审批进度审核状态表
	 * @param currentAuditStatus 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void insertCurrentAuditStatus(LoanCurrentAuditStatus currentAuditStatus,Integer loginUserId);

	/**
	 *修改审批进度审核状态表
	 * @param currentAuditStatus 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updateCurrentAuditStatus(LoanCurrentAuditStatus currentAuditStatus,Integer loginUserId);

	/**
	 * 通过主键删除审批进度审核状态表
	 * @param ID 主键Id
	 */
	void deleteCurrentAuditStatusById(Integer id);

	/**
	 * 通过主键得到审批进度审核状态表
	 * @param ID 主键Id
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
	 * 审批驳回时，更新审批人员的所有信息不可用
	 * @param loanId
	 */
	void updateAuditStatusByLoanId(Integer loanId);

	/**
	 * 清除审批进度
	 * @param loanId
	 */
	void deleteAuditStatusByLoanId(Integer loanId);

}
