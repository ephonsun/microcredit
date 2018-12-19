package banger.service.impl;

import java.util.List;
import java.util.Map;

import banger.moduleIntf.ILoanHistoryProvider;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import banger.framework.util.DateUtil;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.dao.intf.IActionHistoryDao;
import banger.service.intf.IActionHistoryService;
import banger.domain.loan.LoanActionHistory;

/**
 * 贷款操作历史表业务访问类
 */
@Repository
public class ActionHistoryService implements IActionHistoryService,ILoanHistoryProvider {

	@Autowired
	private IActionHistoryDao actionHistoryDao;

	/**
	 * 新增贷款操作历史表
	 * @param actionHistory 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertActionHistory(LoanActionHistory actionHistory,Integer loginUserId){
		this.actionHistoryDao.insertActionHistory(actionHistory);
	}

	/**
	 *修改贷款操作历史表
	 * @param actionHistory 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateActionHistory(LoanActionHistory actionHistory,Integer loginUserId){
		this.actionHistoryDao.updateActionHistory(actionHistory);
	}

	/**
	 * 通过主键删除贷款操作历史表
	 * @param id 主键Id
	 */
	public void deleteActionHistoryById(Integer id){
		this.actionHistoryDao.deleteActionHistoryById(id);
	}

	/**
	 * 通过主键得到贷款操作历史表
	 * @param id 主键Id
	 */
	public LoanActionHistory getActionHistoryById(Integer id){
		return this.actionHistoryDao.getActionHistoryById(id);
	}

	/**
	 * 得到贷款历史列表
	 * @param loanId
	 * @return
	 */
	public List<LoanActionHistory> queryHistoryListByLoanId(Integer loanId){
		return this.actionHistoryDao.queryHistoryListByLoanId(loanId);
	}

	/**
	 * 查询贷款操作历史表
	 * @param condition 查询条件
	 * @return
	 */
	public List<LoanActionHistory> queryActionHistoryList(Map<String,Object> condition){
		return this.actionHistoryDao.queryActionHistoryList(condition);
	}

	/**
	 * 分页查询贷款操作历史表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<LoanActionHistory> queryActionHistoryList(Map<String,Object> condition,IPageSize page){
		return this.actionHistoryDao.queryActionHistoryList(condition,page);
	}

}
