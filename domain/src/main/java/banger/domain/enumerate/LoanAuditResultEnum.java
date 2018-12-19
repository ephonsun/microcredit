package banger.domain.enumerate;

/**
 * Created by Administrator on 2017/6/22.
 */
public enum LoanAuditResultEnum {
    PERSONAL(0,"待审"),
    FAMILY(1,"通过"),
    SPOUSE(2,"不通过"),
    ;

    public final Integer value;				//客户自定义表
    public final String desc;					//贷款自定义表

    LoanAuditResultEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public String getDescByValue(Integer value) {
        for (LoanAuditResultEnum auditResult : LoanAuditResultEnum.values()) {
            if (auditResult.value.intValue() == value.intValue())
                return auditResult.desc;
        }
        return "";
    }
}
