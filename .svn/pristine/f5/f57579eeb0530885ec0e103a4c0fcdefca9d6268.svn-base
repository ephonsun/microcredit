package banger.domain.loan;

/**
 * 安卓端显示一审，二审 ... 图标
 * Created by zhusw on 2017/6/27.
 */
public class LoanAuditProcess {
    private Integer processId;       //环节Id
    private Integer loanId;         //贷款Id
    private Integer paramId;        //审数Id
    private Integer passCount;          //审核通过数
    private Integer totalCount;         //审核人数
    private String display;         //一审，二审的名称

    public Integer getProcessId() {
        return processId;
    }

    public void setProcessId(Integer processId) {
        this.processId = processId;
    }

    public Integer getLoanId() {
        return loanId;
    }

    public void setLoanId(Integer loanId) {
        this.loanId = loanId;
    }

    public Integer getParamId() {
        return paramId;
    }

    public void setParamId(Integer paramId) {
        this.paramId = paramId;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public Integer getPassCount() {
        return passCount;
    }

    public void setPassCount(Integer passCount) {
        this.passCount = passCount;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public boolean isPass(){
        return passCount.equals(totalCount);
    }
}
