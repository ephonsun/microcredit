package banger.dao.intf;

import banger.domain.loan.LoanMonitorInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by wanggl on 2017/6/26.
 */
public interface ILoanMonitorInfoJobDao {
    //首次监控批处理
    void batchProcessFirst(Map<String, Object> condition);
    //更新主表
    void updateLoanApply();
    //常规监控 正常
    void batchProcessNormal(Map<String, Object> condition);
    //常规监控 关注
    void batchProcessConcern(Map<String, Object> condition);

    /**
     * 更新客户经理分配受限表
     */
    void updateLoanAllotLimit();

    /**
     * 设置客户经理受限状态默认值，默认为0，不受限
     */
    void setDefaultLimitState();

    /**
     * 更新客户经理分配受限状态
     */
    void updateLoanAllotLimitState(Map<String, Object> condition);
}
