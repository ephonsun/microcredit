package banger.service.intf;

import banger.domain.loan.LoanStatQuery;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ILoanStatService {
    List<LoanStatQuery> queryLoanStatListByUserId(Map map);
    List<LoanStatQuery> queryLoanStatListByUserIdAndQuarter(Map map);
    List<LoanStatQuery> queryLoanStatListByUserIdAndYear(Map map);

    List<LoanStatQuery> queryLoanStatListByTeamGroupId(Map map);
    List<LoanStatQuery> queryLoanStatListByTeamGroupIdAndQuarter(Map map);
    List<LoanStatQuery> queryLoanStatListByTeamGroupIdAndYear(Map map);

    List<LoanStatQuery> queryCrossLoanStatListByYear(Map map);
    List<LoanStatQuery> queryCrossLoanStatListByMonth(Map map);
    List<LoanStatQuery> queryCrossLoanStatListByQuarter(Map map);

    List<LoanStatQuery> queryCrossLoanStatByYear(Map map);
    List<LoanStatQuery> queryCrossLoanStatByMonth(Map map);
    List<LoanStatQuery> queryCrossLoanStatByQuarter(Map map);
    File createCSVFile(List<String> head, List<List<String>> dataList,
                       String outPutPath, String filename) throws IOException;
}
