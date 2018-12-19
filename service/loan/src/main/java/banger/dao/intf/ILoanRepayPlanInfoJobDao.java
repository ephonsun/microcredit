package banger.dao.intf;

import banger.domain.loan.LoanRepayPlanInfo;

import java.util.List;

/**
 * Created by wanggl on 2017/6/29.
 */
public interface ILoanRepayPlanInfoJobDao {

    List<LoanRepayPlanInfo> getUpdaterRepayPlanInfoes(Integer day);
}
