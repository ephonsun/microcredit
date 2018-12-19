package banger.dao.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.dao.PageSizeDao;
import banger.dao.intf.ICurrentAuditStatusDao;
import banger.domain.loan.LoanCurrentAuditStatus;

/**
 * 审批进度审核状态表数据访问类
 */
@Repository
public class CurrentAuditStatusDao extends PageSizeDao<LoanCurrentAuditStatus> implements ICurrentAuditStatusDao {

	/**
	 * 新增审批进度审核状态表
	 * @param currentAuditStatus 实体对像
	 */
	public void insertCurrentAuditStatus(LoanCurrentAuditStatus currentAuditStatus){
		currentAuditStatus.setId(this.newId().intValue());
		this.execute("insertCurrentAuditStatus",currentAuditStatus);
	}

	/**
	 *修改审批进度审核状态表
	 * @param currentAuditStatus 实体对像
	 */
	public void updateCurrentAuditStatus(LoanCurrentAuditStatus currentAuditStatus){
		this.execute("updateCurrentAuditStatus",currentAuditStatus);
	}

	/**
	 * 通过主键删除审批进度审核状态表
	 * @param id 主键Id
	 */
	public void deleteCurrentAuditStatusById(Integer id){
		this.execute("deleteCurrentAuditStatusById",id);
	}

	/**
	 * 通过主键得到审批进度审核状态表
	 * @param id 主键Id
	 */
	public LoanCurrentAuditStatus getCurrentAuditStatusById(Integer id){
		return (LoanCurrentAuditStatus)this.queryEntity("getCurrentAuditStatusById",id);
	}

	/**
	 * 查询审批进度审核状态表
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<LoanCurrentAuditStatus> queryCurrentAuditStatusList(Map<String,Object> condition){
		return (List<LoanCurrentAuditStatus>)this.queryEntities("queryCurrentAuditStatusList", condition);
	}

	/**
	 * 分页查询审批进度审核状态表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<LoanCurrentAuditStatus> queryCurrentAuditStatusList(Map<String,Object> condition,IPageSize page){
		return (IPageList<LoanCurrentAuditStatus>)this.queryEntities("queryCurrentAuditStatusList", page, condition);
	}

	/**
	 * 重置所有审批中的贷款，通过流程ID
	 * @param flowId
	 */
	public void resetLoanFlowAuditStatusByFlowId(Integer flowId){
		this.execute("resetLoanFlowAuditStatusByFlowId",flowId);
	}

	/**
	 * 重置所有审批中的贷款，通过流程ID
	 * @param processId
	 */
	public void resetLoanFlowAuditStatusByProcessId(Integer processId){
		this.execute("resetLoanFlowAuditStatusByProcessId",processId);
	}



	/**
	 * 审批驳回时，更新审批人员的所有信息不可用
	 * @param loanId
	 */
	@Override
	public void updateAuditStatusByLoanId(Integer loanId) {
		this.execute("updateAuditStatusByLoanId", loanId);
		this.execute("deleteApprovalResolution", loanId);
	}



	/**
	 * 得到当前审核状态
	 * @param loanId
	 * @param processId
	 * @return
	 */
	public List<LoanCurrentAuditStatus> getLoanAuditStatusListById(Integer loanId,Integer processId){
		return (List<LoanCurrentAuditStatus>)this.queryEntities("getLoanAuditStatusListById", new Object[]{loanId,processId});
	}

	@Override
	public void deleteAuditStatusByLoanId(Integer loanId) {
		this.execute("deleteAuditStatusByLoanId", loanId);
	}
}
