package banger.dao.impl;

import banger.dao.intf.ILoanMonitorInfoJobDao;
import banger.domain.loan.LoanMonitorInfo;
import banger.framework.dao.PageSizeDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wanggl on 2017/6/26.
 */
public class LoanMonitorInfoJobDao extends PageSizeDao<LoanMonitorInfo> implements ILoanMonitorInfoJobDao{

//    public void batchProcess() {
//        Map<String,Object> condition = new HashMap<String, Object>();
//        this.execute("monitorBatchProcess",condition);
//    }

    public void batchProcessFirst(Map<String, Object> condition) {
        try {
            this.execute("monitorBatchProcessFirst",condition);
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    public void updateLoanApply() {
        this.execute("UpdateLoanApply");
    }

    public void batchProcessNormal(Map<String, Object> condition) {
        try {
            Thread.sleep(30000);
            this.execute("monitorBatchProcessNormal",condition);
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void batchProcessConcern(Map<String, Object> condition) {
        try {
            Thread.sleep(30000);
            this.execute("monitorBatchProcessConcern",condition);
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * 更新客户经理分配受限表
     */
    @Override
    public void updateLoanAllotLimit() {
        this.execute("updateLoanAllotLimit");
    }

    /**
     * 设置客户经理受限状态默认值，默认为0，不受限
     */
    @Override
    public void setDefaultLimitState() {
        this.execute("setDefaultLimitState");
    }

    /**
     * 更新客户经理分配受限状态
     */
    @Override
    public void updateLoanAllotLimitState(Map<String, Object> condition) {
        this.execute("updateLoanAllotLimitState",condition);
    }


}
