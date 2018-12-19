package banger.service.intf;

import java.util.List;
import java.util.Map;

import banger.domain.loan.LoanCrossCheckIncome;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.loan.LoanCrossCheckSale;

/**
 * 交叉检验销售额表业务访问接口
 */
public interface ICrossCheckSaleService {

	/**
	 * 新增交叉检验销售额表
	 * @param crossCheckSale 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void insertCrossCheckSale(LoanCrossCheckSale crossCheckSale, Integer loginUserId);

	/**
	 *修改交叉检验销售额表
	 * @param crossCheckSale 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updateCrossCheckSale(LoanCrossCheckSale crossCheckSale, Integer loginUserId);

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
	 * 通过贷款id和贷款class得到交叉检验权益表
	 * @param loanId
	 */
	LoanCrossCheckSale getCrossCheckSaleByLoanId(Integer loanId);

	/**
	 * app端保存销售额检验详情（第一次时的插入表）
	 * @param crossCheckSale 实体对象
	 * @param loginUserId 登入用户Id
	 * @return
	 */
	void saveCrossCheckSale(LoanCrossCheckSale crossCheckSale, Integer loginUserId);
}
