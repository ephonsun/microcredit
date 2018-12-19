package banger.service.intf;

import banger.domain.system.SysWorkingTable;
import banger.domain.task.TskTaskInfoQuery;

import java.util.List;
import java.util.Map;

/**
 * Created by wanggl on 2017/7/31.
 */
public interface IWorkingTableService {

    SysWorkingTable queryManageCount(Map<String, Object> userId);

    SysWorkingTable queryCustScheduleWorkingTable(Map<String, Object> condition_cus);

    List<TskTaskInfoQuery> queryTaskInfoList(Map<String, Object> condition_task);
}
