package banger.service.impl;

import banger.dao.intf.IWorkingTableDao;
import banger.domain.system.SysWorkingTable;
import banger.domain.task.TskTaskInfoQuery;
import banger.moduleIntf.IWorkingTableProvider;
import banger.service.intf.IWorkingTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by wanggl on 2017/7/31.
 */
@Service
public class WorkingTableService implements IWorkingTableService,IWorkingTableProvider {

    @Autowired
    private IWorkingTableDao iWorkingTableDao;

    public SysWorkingTable queryManageCount(Map<String, Object> conditioin) {
        return iWorkingTableDao.queryManageCount(conditioin);
    }

    @Override
    public Integer queryApprovalCount(Map<String, Object> condition) {
        return iWorkingTableDao.queryApprovalCount(condition);
    }
    @Override
    public Integer querySignCount(Map<String, Object> condition){
        return iWorkingTableDao.querySignCount(condition);
    }

    @Override
    public Integer queryScheduleCount(Map<String,Object> condition) {
        return iWorkingTableDao.queryScheduleCount(condition);
    }

    @Override
    public SysWorkingTable queryCustScheduleWorkingTable(Map<String, Object> condition_cus) {
        return iWorkingTableDao.queryCustScheduleWorkingTable(condition_cus);
    }

    @Override
    public List<TskTaskInfoQuery> queryTaskInfoList(Map<String, Object> condition_task) {
        return iWorkingTableDao.queryTaskInfoList(condition_task);
    }
}
