package banger.domain.loan;

import banger.domain.enumerate.LoanExamineCode;
import banger.domain.enumerate.LoanExamineRight;
import banger.domain.enumerate.LoanExamineType;
import banger.framework.util.RandomUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 审核类
 * Created by zhusw on 2017/6/19.
 */
public class LoanExamine {
    private Integer loanId;                          //贷款ID
    private Integer processId;                       //过程ID
    private Integer flowId;                          //流程ID
    private Integer paramId;                         //审批条件参数ID
    private boolean hasCondition;                       //是否有条件参数
    private boolean lastProcess;                        //是否是最后一个审批环节，如果最一个
    private Object fieldValue;                          //条件参数的值
    private List<LoanExamineUser> List;     //已经选中的人
    private LoanExamineCode examineCode;                //返回结果
    private BigDecimal proposalAmount;               //建义金额

    private List<LoanExamineReview> reviews;            //审核人员组

    public LoanExamine(){
        this.reviews = new ArrayList<LoanExamineReview>();
    }

    public List<LoanExamineReview> getReviews(){
        return this.reviews;
    }

    public Integer getLoanId() {
        return loanId;
    }

    public void setLoanId(Integer loanId) {
        this.loanId = loanId;
    }

    public Integer getProcessId() {
        return processId;
    }

    public void setProcessId(Integer processId) {
        this.processId = processId;
    }

    public BigDecimal getProposalAmount() {
        return proposalAmount;
    }

    public void setProposalAmount(BigDecimal proposalAmount) {
        this.proposalAmount = proposalAmount;
    }

    public LoanExamineCode getExamineCode() {
        return examineCode;
    }

    public void setExamineCode(LoanExamineCode examineCode) {
        this.examineCode = examineCode;
    }

    public boolean isHasCondition() {
        return hasCondition;
    }

    public void setHasCondition(boolean hasCondition) {
        this.hasCondition = hasCondition;
    }

    public Object getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(Object fieldValue) {
        this.fieldValue = fieldValue;
    }

    public Integer getFlowId() {
        return flowId;
    }

    public void setFlowId(Integer flowId) {
        this.flowId = flowId;
    }

    public Integer getParamId() {
        return paramId;
    }

    public void setParamId(Integer paramId) {
        this.paramId = paramId;
    }

    public boolean isLastProcess() {
        return lastProcess;
    }

    public void setLastProcess(boolean lastProcess) {
        this.lastProcess = lastProcess;
    }

    /**
     * 是否是随机的审核人员，如果有指定人员返回false，全是随机的返回true
     * @return
     */
    public boolean isRandom(){
        for (LoanExamineReview review : getReviews()) {
            if (LoanExamineType.SELECT_USER.type.equals(review.getType().type)) {
                return false;
            }
        }
        return true;
    }
}
