package banger.service.intf;

import java.util.List;
import java.util.Map;

import banger.domain.loan.LoanCrossCheckIncome;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.loan.LoanCrossCheckNetProfit;
import banger.moduleIntf.ILoanModule;

/**
 * 交叉检验净利表业务访问接口
 */
public interface ICrossCheckNetProfitService {

	/**
	 * 新增交叉检验净利表
	 * @param crossCheckNetProfit 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void insertCrossCheckNetProfit(LoanCrossCheckNetProfit crossCheckNetProfit, Integer loginUserId);

	/**
	 *修改交叉检验净利表
	 * @param crossCheckNetProfit 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updateCrossCheckNetProfit(LoanCrossCheckNetProfit crossCheckNetProfit, Integer loginUserId);

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
	 * @param loanId 贷款id ,
	 * @return
	 */
	LoanCrossCheckNetProfit getCrossCheckNetProfitByLoanId(Integer loanId);

	/**
	 * app端保存净利检验详情（第一次时的插入表）
	 * @param crossCheckNetProfit 实体对象
	 * @param loginUserId 登入用户Id
	 * @return
	 */
	void saveCrossCheckNetProfit(LoanCrossCheckNetProfit crossCheckNetProfit,Integer loginUserId);

	/**
	 *修改交叉检验净利表(app端传入空值时赋值字段为null)
	 * @param crossCheckNetProfit 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updateNullCrossCheckNetProfit(LoanCrossCheckNetProfit crossCheckNetProfit, Integer loginUserId);



}
