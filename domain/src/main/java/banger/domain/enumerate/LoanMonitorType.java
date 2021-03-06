package banger.domain.enumerate;

/**
 * 监控类型
 * Created by zhusw on 2017/6/15.
 */
public enum LoanMonitorType {
    FIRST_MONITOR("firstMonitor", "首次监控"),
    NORMAL_MONITOR("normalMonitor", "常规监控"),
    TEMPORARY_MONITOR("temporaryMonitor","异常监控");
//    ABNORMAL_MONITOR("abnormalMonitor","异常监控");
    public final String type; // 贷款监控类型
    public final String typeName; // 贷款监控类型名称

    LoanMonitorType(String type,String typeName){
        this.type = type;
        this.typeName = typeName;
    }

    public static LoanMonitorType valueOfType(String type){
        for (LoanMonitorType monitorType : LoanMonitorType.values()) {
            if (monitorType.type.equals(type)) {
                return monitorType;
            }
        }
        return null;
    }

    public static String getNameByType(String type){
        for (LoanMonitorType monitorType : LoanMonitorType.values()) {
            if (monitorType.type.equals(type)) {
                return monitorType.typeName;
            }
        }
        return "";
    }

}
