package banger.dao.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.loan.LoanProfitLossInfo;

/**
 * 损益情况信息表数据访问接口
 */
public interface IProfitLossInfoDao {

	/**
	 * 新增损益情况信息表
	 * @param profitLossInfo 实体对像
	 */
	void insertProfitLossInfo(LoanProfitLossInfo profitLossInfo);

	/**
	 *修改损益情况信息表
	 * @param profitLossInfo 实体对像
	 */
	void updateProfitLossInfo(LoanProfitLossInfo profitLossInfo);

	/**
	 * 通过主键删除损益情况信息表
	 * @param id 主键Id
	 */
	void deleteProfitLossInfoById(Integer id);

	/**
	 * 通过主键得到损益情况信息表
	 * @param id 主键Id
	 */
	LoanProfitLossInfo getProfitLossInfoById(Integer id);

	/**
	 * 查询损益情况信息表
	 * @param condition 查询条件
	 * @return
	 */
	List<LoanProfitLossInfo> queryProfitLossInfoList(Map<String, Object> condition);

	/**
	 * 分页查询损益情况信息表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<LoanProfitLossInfo> queryProfitLossInfoList(Map<String, Object> condition, IPageSize page);

	/**
	 * 根据id查询损益情况主界面信息
	 * @param loanId
	 * @param loanClassId
	 * @return
	 */
	LoanProfitLossInfo queryLoanProfitLossInfoByIds(Integer loanId,Integer loanClassId);
	/**
	 * 通过贷款id得到损益情况信息表
	 * @param loanId 贷款Id
	 */
	LoanProfitLossInfo getProfitLossInfoByLoanId(Integer loanId);

	/**
	 * 通过贷款id更新损益表
	 * @param profitLossInfo
	 */
	void  updateProfitLossInfoByLoanId(LoanProfitLossInfo profitLossInfo);
}
