package banger.dao.impl;

import banger.dao.intf.ILoanRepayPlanInfoJobDao;
import banger.domain.loan.LoanRepayPlanInfo;
import banger.framework.dao.PageSizeDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wanggl on 2017/6/29.
 */
public class LoanRepayPlanInfoJobDao extends PageSizeDao<LoanRepayPlanInfo> implements ILoanRepayPlanInfoJobDao{

    public List<LoanRepayPlanInfo> getUpdaterRepayPlanInfoes(Integer day) {
        Map<String,Object> condition = new HashMap<String,Object>();
        condition.put("configValue",day);
        return (List<LoanRepayPlanInfo>) this.queryEntities("getUpdaterRepayPlanInfoes",condition);
    }
}
