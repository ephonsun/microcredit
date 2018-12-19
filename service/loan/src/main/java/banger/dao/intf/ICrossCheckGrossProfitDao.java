package banger.dao.intf;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import banger.domain.loan.LoanCrossCheckIncome;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.loan.LoanCrossCheckGrossProfit;


/**
 * 交叉检验毛利表数据访问接口
 */
public interface ICrossCheckGrossProfitDao {

	/**
	 * 新增交叉检验毛利表
	 * @param crossCheckGrossProfit 实体对像
	 */
	void insertCrossCheckGrossProfit(LoanCrossCheckGrossProfit crossCheckGrossProfit);

	/**
	 *修改交叉检验毛利表
	 * @param crossCheckGrossProfit 实体对像
	 */
	void updateCrossCheckGrossProfit(LoanCrossCheckGrossProfit crossCheckGrossProfit);

	/**
	 * 通过主键删除交叉检验毛利表
	 * @param id 主键Id
	 */
	void deleteCrossCheckGrossProfitById(Integer id);

	/**
	 * 通过主键得到交叉检验毛利表
	 * @param id 主键Id
	 */
	LoanCrossCheckGrossProfit getCrossCheckGrossProfitById(Integer id);

	/**
	 * 查询交叉检验毛利表
	 * @param condition 查询条件
	 * @return
	 */
	List<LoanCrossCheckGrossProfit> queryCrossCheckGrossProfitList(Map<String, Object> condition);

	/**
	 * 分页查询交叉检验毛利表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<LoanCrossCheckGrossProfit> queryCrossCheckGrossProfitList(Map<String, Object> condition, IPageSize page);


	/**
	 * 根据贷款id获取毛利表信息
	 * @param loanId
	 * @return
	 */
	LoanCrossCheckGrossProfit getCrossCheckGrossProfitByLoanId(Integer loanId);

	/**
	 *更新交叉检验毛利表 偏差字段
	 * @param condition 偏差1
	 */
	void updateCrossCheckGrossProfitDeviationRatio(Map<String,Object> condition);

	/**
	 *修改交叉检验毛利表(app端传入空值时赋值字段为null)
	 * @param crossCheckGrossProfit 实体对像
	 */
	void updateNullCrossCheckGrossProfit(LoanCrossCheckGrossProfit crossCheckGrossProfit);
}
