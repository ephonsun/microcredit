package banger.service.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.loan.LoanCrossCheckQuanyiquan;

/**
 * 交叉检验权益表业务访问接口
 */
public interface ICrossCheckQuanyiquanService {

	/**
	 * 新增交叉检验权益表
	 * @param crossCheckQuanyiquan 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void insertCrossCheckQuanyiquan(LoanCrossCheckQuanyiquan crossCheckQuanyiquan, Integer loginUserId);

	/**
	 *修改交叉检验权益表
	 * @param crossCheckQuanyiquan 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updateCrossCheckQuanyiquan(LoanCrossCheckQuanyiquan crossCheckQuanyiquan, Integer loginUserId);

	/**
	 * 通过主键删除交叉检验权益表
	 * @param id 主键Id
	 */
	void deleteCrossCheckQuanyiquanById(Integer id);

	/**
	 * 通过主键得到交叉检验权益表
	 * @param id 主键Id
	 */
	LoanCrossCheckQuanyiquan getCrossCheckQuanyiquanById(Integer id);

	/**
	 * 查询交叉检验权益表
	 * @param condition 查询条件
	 * @return
	 */
	List<LoanCrossCheckQuanyiquan> queryCrossCheckQuanyiquanList(Map<String, Object> condition);

	/**
	 * 分页查询交叉检验权益表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<LoanCrossCheckQuanyiquan> queryCrossCheckQuanyiquanList(Map<String, Object> condition, IPageSize page);

	/**
	 * 通过贷款id和贷款class得到交叉检验权益表
	 * @param loanId
	 */
	LoanCrossCheckQuanyiquan getCrossCheckQuanyiquanByLoanId(Integer loanId);


	/**
	 * app端保存权益检验详情（第一次时的插入表）
	 * @param crossCheckQuanyiquan 实体对象
	 * @param loginUserId 登入用户Id
	 * @return
	 */
	void saveCrossCheckQuanyiquan(LoanCrossCheckQuanyiquan crossCheckQuanyiquan, Integer loginUserId);

	/**
	 * 计算并修改交叉检验权益表偏差率 （用在检查权益表发生改变时使用）
	 * @param loanId 贷款id
	 */
	void updateQuanyiquanDev(Integer loanId);
}
