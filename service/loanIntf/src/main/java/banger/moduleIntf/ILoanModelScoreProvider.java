package banger.moduleIntf;

import java.math.BigDecimal;

/**
 * @Author: yangdw
 * @Description:计算评分模型得分对外接口
 * @Date: Created in 9:55 2017/9/15.
 */
public interface ILoanModelScoreProvider {

	BigDecimal countLoanModelScoreByModeId(Integer loanId, Integer loanTypeId, String loanProcessType);
}
