package banger.dao.intf;

import banger.domain.loan.LoanStatQuery;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ILoanStatDao {
    List<LoanStatQuery> queryLoanStatListByUserId(Map map);
    List<LoanStatQuery> queryLoanStatListByUserIdAndQuarter(Map map);
    List<LoanStatQuery> queryLoanStatListByUserIdAndYear(Map map);

    List<LoanStatQuery> queryLoanStatListByTeamGroupId(Map map);
    List<LoanStatQuery> queryLoanStatListByTeamGroupIdAndQuarter(Map map);
    List<LoanStatQuery> queryLoanStatListByTeamGroupIdAndYear(Map map);
   //跨团队统计表数据
    List<LoanStatQuery> queryCrossLoanStatListByYear(Map map);
    List<LoanStatQuery> queryCrossLoanStatListByMonth(Map map);
    List<LoanStatQuery> queryCrossLoanStatListByQuarter(Map map);
   //跨团队折线图与柱状图的数据
    List<LoanStatQuery> queryCrossLoanStatByYear(Map map);
    List<LoanStatQuery> queryCrossLoanStatByMonth(Map map);
    List<LoanStatQuery> queryCrossLoanStatByQuarter(Map map);
}
