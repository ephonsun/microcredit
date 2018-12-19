package banger.dao.impl;

import banger.dao.intf.ILoanStatDao;
import banger.domain.loan.LoanStatQuery;
import banger.framework.dao.PageSizeDao;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public class LoanStatDao extends PageSizeDao<LoanStatQuery> implements ILoanStatDao {
    public List<LoanStatQuery> queryLoanStatListByUserId(Map map){
        return (List<LoanStatQuery>) this.queryEntities("queryLoanStatListByUserId",map);
    }

    @Override
    public List<LoanStatQuery> queryLoanStatListByUserIdAndQuarter(Map map) {
        return (List<LoanStatQuery>) this.queryEntities("queryLoanStatListByUserIdAndQuarter",map);
    }

    @Override
    public List<LoanStatQuery> queryLoanStatListByUserIdAndYear(Map map) {
        return (List<LoanStatQuery>) this.queryEntities("queryLoanStatListByUserIdAndYear",map);
    }

    @Override
    public List<LoanStatQuery> queryLoanStatListByTeamGroupId(Map map) {
        return (List<LoanStatQuery>) this.queryEntities("queryLoanStatListByTeamGroupId",map);
    }

    @Override
    public List<LoanStatQuery> queryLoanStatListByTeamGroupIdAndQuarter(Map map) {
        return (List<LoanStatQuery>) this.queryEntities("queryLoanStatListByTeamGroupIdAndQuarter",map);
    }

    @Override
    public List<LoanStatQuery> queryLoanStatListByTeamGroupIdAndYear(Map map) {
        return (List<LoanStatQuery>) this.queryEntities("queryLoanStatListByTeamGroupIdAndYear",map);
    }

    @Override
    public List<LoanStatQuery> queryCrossLoanStatListByMonth(Map map) {
        return (List<LoanStatQuery>) this.queryEntities("queryLoanStatListByTeamGroupId",map);
    }

    @Override
    public List<LoanStatQuery> queryCrossLoanStatListByQuarter(Map map) {
        return (List<LoanStatQuery>) this.queryEntities("queryLoanStatListByTeamGroupIdAndQuarter",map);
    }

    @Override
    public List<LoanStatQuery> queryCrossLoanStatListByYear(Map map) {
        return (List<LoanStatQuery>) this.queryEntities("queryLoanStatListByTeamGroupIdAndYear",map);
    }

    @Override
    public List<LoanStatQuery> queryCrossLoanStatByYear(Map map) {
        return (List<LoanStatQuery>)this.queryEntities("queryCrossLoanStatByYear",map);
    }

    @Override
    public List<LoanStatQuery> queryCrossLoanStatByMonth(Map map) {
        return (List<LoanStatQuery>)this.queryEntities("queryCrossLoanStatByMonth",map);
    }

    @Override
    public List<LoanStatQuery> queryCrossLoanStatByQuarter(Map map) {
        return (List<LoanStatQuery>)this.queryEntities("queryCrossLoanStatByQuarter",map);
    }
}
