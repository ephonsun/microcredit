package banger.dao.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.loan.LoanActionHistory;

/**
 * 贷款操作历史表数据访问接口
 */
public interface IActionHistoryDao {

	/**
	 * 新增贷款操作历史表
	 * @param actionHistory 实体对像
	 */
	void insertActionHistory(LoanActionHistory actionHistory);

	/**
	 *修改贷款操作历史表
	 * @param actionHistory 实体对像
	 */
	void updateActionHistory(LoanActionHistory actionHistory);

	/**
	 * 通过主键删除贷款操作历史表
	 * @param id 主键Id
	 */
	void deleteActionHistoryById(Integer id);

	/**
	 * 通过主键得到贷款操作历史表
	 * @param id 主键Id
	 */
	LoanActionHistory getActionHistoryById(Integer id);

	/**
	 * 工作流调整，新增退回记录
	 */
	void insertHistoryByResetFlowFlowId(Integer flowId);

	/**
	 * 工作流调整，新增退回记录
	 * @param processId
	 */
	void insertHistoryByResetLoanFlowProcessId(Integer processId);

	/**
	 * 得到贷款历史列表
	 * @param loanId
	 * @return
	 */
	List<LoanActionHistory> queryHistoryListByLoanId(Integer loanId);

	/**
	 * 查询贷款操作历史表
	 * @param condition 查询条件
	 * @return
	 */
	List<LoanActionHistory> queryActionHistoryList(Map<String, Object> condition);

	/**
	 * 分页查询贷款操作历史表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<LoanActionHistory> queryActionHistoryList(Map<String, Object> condition, IPageSize page);

}
