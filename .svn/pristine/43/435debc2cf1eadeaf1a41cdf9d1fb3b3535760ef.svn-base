package banger.dao.intf;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import banger.domain.loan.LoanRepayPlanInfoExtend;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.loan.LoanRepayPlanInfo;

/**
 * 贷款还信催款息表数据访问接口
 */
public interface IRepayPlanInfoDao {

	/**
	 * 新增贷款还信催款息表
	 * @param repayPlanInfo 实体对像
	 */
	void insertRepayPlanInfo(LoanRepayPlanInfo repayPlanInfo);

	/**
	 *修改贷款还信催款息表
	 * @param repayPlanInfo 实体对像
	 */
	void updateRepayPlanInfo(LoanRepayPlanInfo repayPlanInfo);

	/**
	 * 通过主键删除贷款还信催款息表
	 * @param id 主键Id
	 */
	void deleteRepayPlanInfoById(Integer id);

	/**
	 * 通过主键得到贷款还信催款息表
	 * @param id 主键Id
	 */
	LoanRepayPlanInfo getRepayPlanInfoById(Integer id);

	/**
	 * 得到第一批还款计录
	 * @param loanId
	 * @return
	 */
	LoanRepayPlanInfo getTopLoanRepayPlanInfo(Integer loanId);

	/**
	 * 得到还款计划
	 * @param loanId
	 * @return
	 */
	List<LoanRepayPlanInfo> getLoanRepayPlanInfoListByLoanId(Integer loanId);

	/**
	 * 查询贷款还信催款息表
	 * @param condition 查询条件
	 * @return
	 */
	List<LoanRepayPlanInfo> queryRepayPlanInfoList(Map<String, Object> condition);

	/**
	 * 分页查询贷款还信催款息表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<LoanRepayPlanInfo> queryRepayPlanInfoList(Map<String, Object> condition, IPageSize page);

	/**
	 * 查询催款列表
	 * @param loanId
	 * @param repaymentMode
     * @param processType
     * @return
	 */
    List<LoanRepayPlanInfoExtend> queryLoanRepayPlanInfoByLoanId(Integer loanId, String repaymentMode, String processType);

    List<LoanRepayPlanInfo> queryListByLoanRepayPlanDate(Map<String, Object> condition);

    List<LoanRepayPlanInfo> queryNextPlan(Map<String, Object> condition);

    List<LoanRepayPlanInfo> queryUnConllection(Map<String, Object> condition);

	List<LoanRepayPlanInfo> queryComplete(Map<String, Object> condition);

    void deleteRepayPlanInfoByLoanId(Integer loanId, String processType);

	void deleteRepayPlanInfoByLoanIdAndMode(Integer loanId, String processType, String repaymentMode);

	List<LoanRepayPlanInfo> getLoanRepayPlanInfoByLoanIdAndRepayDate(Integer loanId, Date repayDate, BigDecimal principalAmount);
}
