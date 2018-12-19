package banger.moduleIntf;

import banger.domain.loan.LoanApplyInfo;
import banger.domain.loan.LoanRepayPlanInfo;
import banger.domain.loan.LoanRepayPlanInfoExtend;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IRepayPlanInfoProvider {


    /**
     * 根据调查信息生成调查报告
     * @param loanId
     * @param repaymentMode
     * @param proposalAmount
     * @param proposalLimit
     * @param proposalRatio
     * @param loginUserId
     * @return
     */
    List<LoanRepayPlanInfoExtend> getRepayPlanInfoListForI(String loanId, String repaymentMode, String proposalAmount, String proposalLimit, String proposalRatio, String processType, Integer loginUserId);

    /**
     * @Author: yangdw
     * @param loanRepayPlanInfo 实际还款本金,利息,时间,id
     * @param loginUserId 操作者
     * @Description:
     * @Date: 17:28 2017/10/26
     */
    void updateRepayPlanInfoById(LoanRepayPlanInfo loanRepayPlanInfo, Integer loginUserId);

    /**
     * 根据贷款id删除还款计划
     * @param loanId
     */
    void deleteRepayPlanInfoByLoanId(Integer loanId, String processType);


    String checkPlans(Integer loanId, Map<String,Object> map);

    String checkPlansLoan(Object repaymentMode,Object proposalLimit,Object money,Object loanRatioDate,Object loanBackDate,Integer loanId,LoanApplyInfo loanApplyInfo);


    /**
     * 保存贷款计划
     * @param loanRepayPlanInfos
     */
    void savePlanList(List<LoanRepayPlanInfoExtend> loanRepayPlanInfos);

    /**
     * 通过主键得到贷款申请表
     * @param loanId 主键Id
     */
    LoanApplyInfo getApplyInfoById(Integer loanId);

    List<LoanRepayPlanInfo> queryRepayPlanInfoList(Map<String, Object> condition);

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

    /**
     * 通过主键得到贷款还信催款息表
     */
    LoanRepayPlanInfo getRepayPlanInfoById(Integer id);

    public List<LoanRepayPlanInfoExtend> queryLoanRepayPlanInfoByLoanId(Integer loanId, String repaymentMode, String processType);

    List<LoanRepayPlanInfoExtend> getRepayPlanInfoListForLoan_ZS(String loanId, String repaymentMode, String proposalAmount, String proposalLimit, String proposalRatio, String ratioDate,String loanEndDate, Integer date, Integer loginUserId, String type,boolean isReset);
}
