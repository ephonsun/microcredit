package banger.service.intf;

import banger.domain.task.TskGroupTaskAssign;
import banger.domain.task.TskTaskInfo;
import banger.domain.task.TskTeamMemberTaskAssign;

import java.util.List;

/**
 * 统计任务贷款金额
 */
public interface IMarketingTaskService {

    /**
     * 统计更新任务的分配人的贷款金额和数量
     * @param tskTaskInfo
     * @param taskAssign
     */
    void updateUserTaskFinishAmount(TskTaskInfo tskTaskInfo, TskTeamMemberTaskAssign taskAssign);

    /**
     * 统计更新任务的分配人的贷款金额和数量
     * @param tskTaskInfo
     * @param taskAssignList
     */
    void updateUserTaskFinishAmount(TskTaskInfo tskTaskInfo, List<TskTeamMemberTaskAssign> taskAssignList);

    /**
     * 统计更新任务的分配团队的贷款金额和数量
     * @param tskTaskInfo
     */
    void updateGroupTaskFinishAmount(TskTaskInfo tskTaskInfo, TskGroupTaskAssign groupAssign);

    /**
     * 统计更新任务的分配团队的贷款金额和数量
     * @param groupAssignList
     */
    void updateGroupTaskFinishAmount(TskTaskInfo tskTaskInfo, List<TskGroupTaskAssign> groupAssignList);

}
