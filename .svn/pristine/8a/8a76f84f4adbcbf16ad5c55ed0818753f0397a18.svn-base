package banger.service.impl;

import banger.dao.intf.ILoanStatDao;
import banger.dao.intf.IQueryReportDao;
import banger.domain.loan.LoanCountBean;
import banger.domain.loan.LoanStatQuery;
import banger.domain.loan.QueryReport_Query;
import banger.domain.system.SysDataDictCol;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.moduleIntf.ISystemModule;
import banger.service.intf.ILoanStatService;
import banger.service.intf.IQueryReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Repository
public class QueryReportService implements IQueryReportService {
    @Autowired
    private IQueryReportDao iQueryReportDao;




    @Override
    public IPageList<QueryReport_Query> queryReportByCondition(Map map,IPageSize page) {
        return iQueryReportDao.queryReportByCondition(map, page);
    }
    @Override
    public List<QueryReport_Query> queryReportByCondition(Map map){
        return iQueryReportDao.queryReportByCondition(map);
    }
    @Override
    public List<LoanCountBean> getLoanCountByCondition(Map map){
        return iQueryReportDao.getLoanCountByCondition(map);
    }
}
