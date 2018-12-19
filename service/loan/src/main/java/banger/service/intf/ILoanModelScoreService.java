package banger.service.intf;

import java.math.BigDecimal;

/**
 * @Author: yangdw
 * @Description:计算评分模型得分接口
 * @Date: Created in 15:47 2017/9/13.
 */
public interface ILoanModelScoreService {

	BigDecimal countLoanModelScoreByModeId(Integer loanId, Integer loanTypeId, String loanProcessType);
}
