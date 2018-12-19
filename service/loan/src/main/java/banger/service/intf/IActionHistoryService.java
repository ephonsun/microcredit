package banger.service.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.loan.LoanActionHistory;

/**
 * 贷款操作历史表业务访问接口
 */
public interface IActionHistoryService {

	/**
	 * 新增贷款操作历史表
	 * @param actionHistory 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void insertActionHistory(LoanActionHistory actionHistory, Integer loginUserId);

	/**
	 *修改贷款操作历史表
	 * @param actionHistory 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updateActionHistory(LoanActionHistory actionHistory, Integer loginUserId);

	/**
	 * 通过主键删除贷款操作历史表
	 * @param ID 主键Id
	 */
	void deleteActionHistoryById(Integer id);

	/**
	 * 通过主键得到贷款操作历史表
	 * @param ID 主键Id
	 */
	LoanActionHistory getActionHistoryById(Integer id);

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
