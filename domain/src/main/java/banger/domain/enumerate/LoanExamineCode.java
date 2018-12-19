package banger.domain.enumerate;

/**
 * 提交审批，返回状态结果，只有通过才可以提交，否则，提示对应错误信息
 * Created by zhusw on 2017/6/19.
 */
public enum LoanExamineCode {
    PASS("PASS", "通过"),
    LOAN_DATA_ERROR("LOAN_DATA_ERROR","贷款数据错误或缺失"),
    LOAN_PROPOSE_AMOUNT_ERROR("LOAN_PROPOSE_AMOUNT_ERROR","贷款建议金额不能为空"),
    LOAN_IS_BLACK_CUSTOMER("LOAN_IS_BLACK_CUSTOMER","客户贷款受限"),
    LOAN_PROCESS_STEP_UNDEFINED("LOAN_PROCESS_STEP_UNDEFINED","未设置审批流程环节"),
    LOAN_CONDITION_PARAM_RANGE("LOAN_CONDITION_PARAM_RANGE","找不到匹配审批条件参数"),
    LOAN_CONDITION_PARAM_UNDEFINED("LOAN_CONDITION_PARAM_UNDEFINED","未设置审批流程条件参数"),
    LOAN_PROCESS_CONDITION_UNDEFINED("LOAN_PROCESS_CONDITION_UNDEFINED","未设置审批流程条件"),
    LOAN_PROCESS_UNDEFINED("LOAN_PROCESS_UNDEFINED","未设置审批流程"),
    REVIEW_COUNT_POSITIVE("REVIEW_COUNT_POSITIVE","审批人数必需大于0"),
    CONDITION_PARAM_POSITIVE("CONDITION_PARAM_POSITIVE","审批条件参数的值必需大于0"),
    CONDITION_PARAM_REQUIRED("CONDITION_PARAM_REQUIRED","审批条件参数的值不能为空"),
    FIND_NOT_LOAN_PROCESS("FIND_NOT_LOAN_PROCESS","找不到该贷款的审批环节"),
    LOAN_PROCCESS_APPROVAL_STATE("LOAN_PROCCESS_APPROVAL_STATE","当前贷款已在审批中,不能重复提交"),
    LOAN_PROCCESS_UNVALID_STATE("LOAN_PROCCESS_UNVALID_STATE","当前贷款阶段不正确"),
    FIND_NOT_VALID_USER("FIND_NOT_VALID_USER", "找不到符合条件的审核人员"),
    NOT_ENOUGH_VALID_USER("NOT_ENOUGH_VALID_USER","缺少符合条件的审核人员"),
    ;

    public final String code; // 选取方式类型
    public final String message; // 选取方式类型名称

    LoanExamineCode(String code,String message){
        this.code = code;
        this.message = message;
    }

    public static String getMessageByCode(String code){
        for (LoanExamineCode examineCode : LoanExamineCode.values()) {
            if (examineCode.code.equals(code)) {
                return examineCode.message;
            }
        }
        return "";
    }
}
