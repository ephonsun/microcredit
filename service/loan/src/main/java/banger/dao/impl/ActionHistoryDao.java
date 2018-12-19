package banger.dao.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.dao.PageSizeDao;
import banger.dao.intf.IActionHistoryDao;
import banger.domain.loan.LoanActionHistory;

/**
 * 贷款操作历史表数据访问类
 */
@Repository
public class ActionHistoryDao extends PageSizeDao<LoanActionHistory> implements IActionHistoryDao {

	/**
	 * 新增贷款操作历史表
	 * @param actionHistory 实体对像
	 */
	public void insertActionHistory(LoanActionHistory actionHistory){
		actionHistory.setId(this.newId().intValue());
		this.execute("insertActionHistory",actionHistory);
	}

	/**
	 *修改贷款操作历史表
	 * @param actionHistory 实体对像
	 */
	public void updateActionHistory(LoanActionHistory actionHistory){
		this.execute("updateActionHistory",actionHistory);
	}

	/**
	 * 通过主键删除贷款操作历史表
	 * @param id 主键Id
	 */
	public void deleteActionHistoryById(Integer id){
		this.execute("deleteActionHistoryById",id);
	}

	/**
	 * 通过主键得到贷款操作历史表
	 * @param id 主键Id
	 */
	public LoanActionHistory getActionHistoryById(Integer id){
		return (LoanActionHistory)this.queryEntity("getActionHistoryById",id);
	}

	/**
	 * 工作流调整，新增退回记录
	 */
	public void insertHistoryByResetFlowFlowId(Integer flowId){
		this.execute("insertHistoryByResetFlowFlowId",flowId);
	}

	/**
	 * 工作流调整，新增退回记录
	 * @param processId
	 */
	public void insertHistoryByResetLoanFlowProcessId(Integer processId){
		this.execute("insertHistoryByResetLoanFlowProcessId",processId);
	}

	/**
	 * 得到贷款历史列表
	 * @param loanId
	 * @return
	 */
	public List<LoanActionHistory> queryHistoryListByLoanId(Integer loanId){
		return (List<LoanActionHistory>)this.queryEntities("queryHistoryListByLoanId", loanId);
	}

	/**
	 * 查询贷款操作历史表
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<LoanActionHistory> queryActionHistoryList(Map<String,Object> condition){
		return (List<LoanActionHistory>)this.queryEntities("queryActionHistoryList", condition);
	}

	/**
	 * 分页查询贷款操作历史表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<LoanActionHistory> queryActionHistoryList(Map<String,Object> condition,IPageSize page){
		return (IPageList<LoanActionHistory>)this.queryEntities("queryActionHistoryList", page, condition);
	}

}
