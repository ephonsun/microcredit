package banger.service.intf;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import banger.domain.loan.LoanRepayPlanInfoExtend;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.loan.LoanRepayPlanInfo;

/**
 * 贷款还信催款息表业务访问接口
 */
public interface IRepayPlanInfoService {

	/**
	 * 保存贷款计划
	 * @param loanRepayPlanInfos
	 */
	void savePlanList(List<LoanRepayPlanInfoExtend> loanRepayPlanInfos);
	/**
	 * 新增贷款还信催款息表
	 * @param repayPlanInfo 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void insertRepayPlanInfo(LoanRepayPlanInfo repayPlanInfo, Integer loginUserId);

	/**
	 *修改贷款还信催款息表
	 * @param repayPlanInfo 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updateRepayPlanInfo(LoanRepayPlanInfo repayPlanInfo, Integer loginUserId);

	/**
	 * 通过主键删除贷款还信催款息表
	 */
	void deleteRepayPlanInfoById(Integer id);

	/**
	 * 通过主键得到贷款还信催款息表
	 */
	LoanRepayPlanInfo getRepayPlanInfoById(Integer id);

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
	 * @return
	 */
    List<LoanRepayPlanInfoExtend> queryLoanRepayPlanInfoByLoanId(Integer loanId);
	List<LoanRepayPlanInfoExtend> queryLoanRepayPlanInfoByLoanId(Integer loanId, String repaymentMode, String processType);

    List<LoanRepayPlanInfo> queryListByLoanRepayPlanDate(Map<String, Object> condition);

    List<LoanRepayPlanInfo> queryNextPlan(Map<String, Object> condition);

	List<LoanRepayPlanInfo> queryUnConllection(Map<String, Object> condition);

	List<LoanRepayPlanInfo> queryComplete(Map<String, Object> condition);

	/**
	 * 根据调查信息生成调查报告
	 * @param loanId
	 * @param repaymentMode
	 * @param proposalAmount
	 * @param proposalLimit
	 * @param proposalRatio
	 * @return
	 */
	List<LoanRepayPlanInfoExtend> getRepayPlanInfoListForI(String loanId, String repaymentMode, String proposalAmount, String proposalLimit, String proposalRatio, String processType, Integer loginUserId);

	List<LoanRepayPlanInfoExtend> getRepayPlanInfoListForL(String loanId, String repaymentMode, String proposalAmount, String proposalLimit, String proposalRatio, String ratioDate, Integer date, Integer loginUserId);

	//中山定制
	List<LoanRepayPlanInfoExtend> getRepayPlanInfoListForLoan_ZS(String loanId, String repaymentMode, String proposalAmount, String proposalLimit, String proposalRatio, String ratioDate, String loanEndDate, Integer date, Integer loginUserId, String type,boolean isReset);

	void updateRepayPlanInfoById(LoanRepayPlanInfo loanRepayPlanInfo, Integer loginUserId);

	/**
	 * 根据贷款id删除还款计划
	 * @param loanId
	 */
	void deleteRepayPlanInfoByLoanId(Integer loanId, String processType);

    String checkPlans(Integer loanId, Map<String,Object> map);

	/**
	 * @Author: yangdw
	 * @param loanId 贷款id
	 * @param repayDate 还款时间
	 * @param principalAmount 应还本金
	 * @Description:
	 * @Date: 15:36 2017/11/20
	 */
	List<LoanRepayPlanInfo> getLoanRepayPlanInfoByLoanIdAndRepayDate(Integer loanId, Date repayDate, BigDecimal principalAmount);

	/**
	 * @Author: yangdw
	 * @param loanId 贷款id
	 * @Description:根据贷款ID获取还款计划
	 * @Date: 15:36 2017/11/20
	 */
	public List<LoanRepayPlanInfo> getLoanRepayPlanInfoListByLoanId(Integer loanId);

	/**
	 * @Author: yangdw
	 * @param loanId 贷款id
	 * @Description:通过贷款ID查询还款计划,由还款计划更新贷款状态和贷款
	 * ========如有业务需要,南郊可以自行修改======
	 * @Date: 17:07 2017/11/22
	 */
	void updateLoanStateAndMoenyByRepayPlans(Integer loanId);

    void copyPlansToApproval(Integer loanId);

	void deleteRepayPlanInfoByLoanIdAndMode(String loanId, String loanProcessType, String repaymentMode);
}
