package banger.dao.impl;

import banger.dao.intf.IWorkingTableDao;
import banger.domain.system.SysOpeventLog;
import banger.domain.system.SysWorkingTable;
import banger.domain.task.TskTaskInfoQuery;
import banger.framework.dao.PageSizeDao;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wanggl on 2017/7/31.
 */
@Repository
public class WorkingTableDao  extends PageSizeDao<SysOpeventLog> implements IWorkingTableDao {

    public SysWorkingTable queryManageCount(Map<String, Object> condition) {
        return ((List<SysWorkingTable>) this.queryEntities("queryManageCount",condition)).get(0);
    }

    @Override
    public SysWorkingTable queryCustScheduleWorkingTable(Map<String, Object> condition_cus) {
        return ((List<SysWorkingTable>) this.queryEntities("queryCustScheduleWorkingTable",condition_cus)).get(0);
    }

    @Override
    public List<TskTaskInfoQuery> queryTaskInfoList(Map<String, Object> condition_task) {
        return (List<TskTaskInfoQuery>) this.queryEntities("queryTaskListNum",condition_task);
    }

    @Override
    public Integer queryApprovalCount(Map<String, Object> condition) {
        return this.queryInt("queryApprovalCount",condition);
    }

    @Override
    public Integer querySignCount(Map<String, Object> condition){
        return this.queryInt("querySignCount",condition);
    }

    @Override
    public Integer queryScheduleCount(Map<String, Object> condition) {
        return this.queryInt("queryScheduleCount",condition);
    }
}
