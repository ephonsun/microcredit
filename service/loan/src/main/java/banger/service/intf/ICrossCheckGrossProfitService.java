package banger.service.intf;

import java.util.List;
import java.util.Map;

import banger.domain.loan.LoanCrossCheckSale;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.loan.LoanCrossCheckGrossProfit;

/**
 * 交叉检验毛利表业务访问接口
 */
public interface ICrossCheckGrossProfitService {

	/**
	 * 新增交叉检验毛利表
	 * @param crossCheckGrossProfit 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void insertCrossCheckGrossProfit(LoanCrossCheckGrossProfit crossCheckGrossProfit, Integer loginUserId);

	/**
	 *修改交叉检验毛利表
	 * @param crossCheckGrossProfit 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updateCrossCheckGrossProfit(LoanCrossCheckGrossProfit crossCheckGrossProfit, Integer loginUserId);

	/**
	 * 通过主键删除交叉检验毛利表
	 * @param ID 主键Id
	 */
	void deleteCrossCheckGrossProfitById(Integer id);

	/**
	 * 通过主键得到交叉检验毛利表
	 * @param ID 主键Id
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
	public LoanCrossCheckGrossProfit getCrossCheckGrossProfitByLoanId(Integer loanId);


	/**
	 *保存毛利检验信息
	 *
	 * @param lccgp
	 * @param userId
	 */
	void saveLoanCrossCheckGrossProfit(LoanCrossCheckGrossProfit lccgp,Integer userId);

	/**
	 *修改交叉检验毛利表(app端传入空值时赋值字段为null)
	 * @param crossCheckGrossProfit 实体对像
	 * @param loginUserId 登入用户Id
	 */
	 void updateNullCrossCheckGrossProfit(LoanCrossCheckGrossProfit crossCheckGrossProfit,Integer loginUserId);

}
