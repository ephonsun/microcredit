package banger.service.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.loan.LoanProfitLossInfo;

/**
 * 损益情况信息表业务访问接口
 */
public interface IProfitLossInfoService {

	/**
	 * 分配贷款时,生成损益主表信息
	 * @param profitLossInfo
	 * @param loginUserId
	 */
	void saveProfitLossInfo(LoanProfitLossInfo profitLossInfo,Integer loginUserId);

	/**
	 * 新增损益情况信息表
	 * @param profitLossInfo 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void insertProfitLossInfo(LoanProfitLossInfo profitLossInfo, Integer loginUserId);

	/**
	 *修改损益情况信息表
	 * @param profitLossInfo 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updateProfitLossInfo(LoanProfitLossInfo profitLossInfo, Integer loginUserId);

	void updateProfitLossInfoByLoanId(LoanProfitLossInfo profitLossInfo,Integer loginUserId);

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
	 * 通过贷款主键得到损益情况信息表
	 * @param loanId 主键loanId
	 */
	LoanProfitLossInfo getProfitLossInfoByLoanId(Integer loanId);

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

}
