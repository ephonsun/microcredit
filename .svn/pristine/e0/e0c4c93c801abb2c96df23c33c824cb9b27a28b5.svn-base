package banger.service.intf;

import banger.domain.loan.LoanCountBean;
import banger.domain.loan.LoanStatQuery;
import banger.domain.loan.QueryReport_Query;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface IQueryReportService {
    IPageList<QueryReport_Query> queryReportByCondition(Map map,IPageSize page);

    List<QueryReport_Query> queryReportByCondition(Map map);

    List<LoanCountBean> getLoanCountByCondition(Map map);
}
