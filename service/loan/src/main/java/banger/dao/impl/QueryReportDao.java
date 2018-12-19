package banger.dao.impl;

import banger.dao.intf.ILoanStatDao;
import banger.dao.intf.IQueryReportDao;
import banger.domain.loan.LoanCountBean;
import banger.domain.loan.LoanStatQuery;
import banger.domain.loan.QueryReport_Query;
import banger.framework.dao.PageSizeDao;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class QueryReportDao extends PageSizeDao<QueryReport_Query> implements IQueryReportDao {

    @Override
    public IPageList<QueryReport_Query> queryReportByCondition(Map map,IPageSize page) {
        return (IPageList<QueryReport_Query>)this.queryEntities("queryReportByCondition",page,map);
    }
    @Override
    public List<QueryReport_Query> queryReportByCondition(Map map){
        return (List<QueryReport_Query>)this.queryEntities("queryReportByCondition",map);
    }
    @Override
    public List<LoanCountBean> getLoanCountByCondition(Map map){
        return (List<LoanCountBean>)this.queryEntities("getLoanCountByCondition",map);
    }
}
