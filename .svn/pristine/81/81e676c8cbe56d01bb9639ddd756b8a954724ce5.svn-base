package banger.dao.intf;

import java.util.List;
import java.util.Map;

import banger.domain.loan.LoanCrossCheckQuanyiquan;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.loan.LoanCrossCheckNetProfit;

/**
 * 交叉检验净利表数据访问接口
 */
public interface ICrossCheckNetProfitDao {

	/**
	 * 新增交叉检验净利表
	 * @param crossCheckNetProfit 实体对像
	 */
	void insertCrossCheckNetProfit(LoanCrossCheckNetProfit crossCheckNetProfit);

	/**
	 *修改交叉检验净利表
	 * @param crossCheckNetProfit 实体对像
	 */
	void updateCrossCheckNetProfit(LoanCrossCheckNetProfit crossCheckNetProfit);

	/**
	 * 通过主键删除交叉检验净利表
	 * @param id 主键Id
	 */
	void deleteCrossCheckNetProfitById(Integer id);

	/**
	 * 通过主键得到交叉检验净利表
	 * @param id 主键Id
	 */
	LoanCrossCheckNetProfit getCrossCheckNetProfitById(Integer id);

	/**
	 * 查询交叉检验净利表
	 * @param condition 查询条件
	 * @return
	 */
	List<LoanCrossCheckNetProfit> queryCrossCheckNetProfitList(Map<String, Object> condition);

	/**
	 * 分页查询交叉检验净利表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<LoanCrossCheckNetProfit> queryCrossCheckNetProfitList(Map<String, Object> condition, IPageSize page);

	/**
	 * app端通过贷款id获取净利检验详情
	 * @param loanId 贷款id
	 * @return
	 */
	LoanCrossCheckNetProfit getCrossCheckNetProfitByLoanId(Integer loanId);

	/**
	 * 修改交叉检验净利表偏差字段
	 * @param condition
	 */
	void updateCroCheNetProDevRatio(Map<String,Object> condition);

	/**
	 *修改交叉检验毛利表(app端传入空值时赋值字段为null)
	 * @param crossCheckNetProfit 实体对像
	 */
	void updateNullCrossCheckNetProfit(LoanCrossCheckNetProfit crossCheckNetProfit);

}
