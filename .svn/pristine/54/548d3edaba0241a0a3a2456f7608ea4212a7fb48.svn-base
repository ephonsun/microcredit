package banger.dao.intf;

import banger.domain.system.SysWorkingTable;
import banger.domain.task.TskTaskInfoQuery;

import java.util.List;
import java.util.Map;

/**
 * Created by wanggl on 2017/7/31.
 */
public interface IWorkingTableDao {

    SysWorkingTable queryManageCount(Map<String, Object> conditiion);

    SysWorkingTable queryCustScheduleWorkingTable(Map<String, Object> condition_cus);

    List<TskTaskInfoQuery> queryTaskInfoList(Map<String, Object> condition_task);

    Integer queryApprovalCount(Map<String, Object> condition);

    Integer querySignCount(Map<String, Object> condition);

    /**
     * 查询今日其他日程总数
     * @return
     */
    Integer queryScheduleCount(Map<String, Object> condition);
}
