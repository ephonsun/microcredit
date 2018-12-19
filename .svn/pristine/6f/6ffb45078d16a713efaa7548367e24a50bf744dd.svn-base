package banger.dao.intf;

import java.util.List;
import java.util.Map;

import banger.domain.loan.LoanCrossCheckIncome;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.loan.LoanCrossCheckSale;

/**
 * 交叉检验销售额表数据访问接口
 */
public interface ICrossCheckSaleDao {

	/**
	 * 新增交叉检验销售额表
	 * @param crossCheckSale 实体对像
	 */
	void insertCrossCheckSale(LoanCrossCheckSale crossCheckSale);

	/**
	 *修改交叉检验销售额表
	 * @param crossCheckSale 实体对像
	 */
	void updateCrossCheckSale(LoanCrossCheckSale crossCheckSale);

	/**
	 * 通过主键删除交叉检验销售额表
	 * @param id 主键Id
	 */
	void deleteCrossCheckSaleById(Integer id);

	/**
	 * 通过主键得到交叉检验销售额表
	 * @param id 主键Id
	 */
	LoanCrossCheckSale getCrossCheckSaleById(Integer id);

	/**
	 * 查询交叉检验销售额表
	 * @param condition 查询条件
	 * @return
	 */
	List<LoanCrossCheckSale> queryCrossCheckSaleList(Map<String, Object> condition);

	/**
	 * 分页查询交叉检验销售额表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<LoanCrossCheckSale> queryCrossCheckSaleList(Map<String, Object> condition, IPageSize page);

	/**
	 * 通过贷款id和贷款class得到交叉检验销售表
	 * @param loanId 参数loanId
	 */
	LoanCrossCheckSale getCrossCheckSaleByLoanId(Integer loanId);

	/**
	 * 计算并修改交叉检验销售表偏差率 （用在检查销售表发生改变时使用）
	 * @param
	 */

	void updateCrossCheckSaleDevRatio(Map<String,Object> condition);
}
