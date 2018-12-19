package banger.moduleIntf;

import banger.domain.system.SysWorkingTable;
import banger.domain.task.TskTaskInfoQuery;

import java.util.List;
import java.util.Map; /**
 * Created by wanggl on 2017/8/1.
 */
public interface IWorkingTableProvider {

    SysWorkingTable queryCustScheduleWorkingTable(Map<String, Object> condition_cus);

    List<TskTaskInfoQuery> queryTaskInfoList(Map<String, Object> condition_task);

    SysWorkingTable queryManageCount(Map<String, Object> condition);

    Integer queryApprovalCount(Map<String, Object> condition);

    Integer querySignCount(Map<String, Object> condition);

    /**
     * 查询今日其他日程总数
     * @return
     */
    Integer queryScheduleCount(Map<String,Object> condition);
}
