package banger.domain.loan;

import banger.domain.enumerate.LoanMonitorType;
import banger.framework.codetable.CodeTable;
import banger.framework.util.StringUtil;

/**
 * Created by zhusw on 2017/6/21.
 */
public class LoanMonitorInfo_Query extends LoanMonitorInfo {
    private String userName;            //监控人姓名

    @CodeTable(name = "cdDictColByName", key = "loanRevisitType", params = "CD_MONITOR_REVISIT_TYPE")
    private String loanRevisitTypeName;         //回访类型

    /**
     * 监控类型
     * @return
     */
    public String getLoanMonitorTypeName(){
        if(StringUtil.isNotEmpty(this.getLoanMonitorType())){
            return LoanMonitorType.getNameByType(this.getLoanMonitorType());
        }
        return "";
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLoanRevisitTypeName() {
        return loanRevisitTypeName;
    }

    public void setLoanRevisitTypeName(String loanRevisitTypeName) {
        this.loanRevisitTypeName = loanRevisitTypeName;
    }
}
