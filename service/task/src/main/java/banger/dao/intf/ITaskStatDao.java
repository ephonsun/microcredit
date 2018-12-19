package banger.dao.intf;

import java.util.Date;
import java.util.List;
import java.util.Map;
import banger.domain.task.TskTaskStatQuery;

public interface ITaskStatDao {
	
	/**
	 * 查询团队任务
	 * @param condition 查询条件
	 * @return
	 */
	List<TskTaskStatQuery> queryTaskStatList(Map<String, Object> condition);

	/**
	 * 查询客户经理任务
	 */
	List<TskTaskStatQuery> queryPersonList(Map<String, Object> condition);
	/**
	 * 查询团队列表
	 * @return
	 */
	List<TskTaskStatQuery> queryGroupList();

	/**
	 * 根据团队id查询团队成员
	 */
	List<TskTaskStatQuery> queryMemberByTeamGroupId(String teamGroupId);

	/**
	 * 根据数据权限查询团队列表
	 */
	List<TskTaskStatQuery> queryGroupListByGroupPermit(String groupPermit);

	/**
	 * 查询跨团队列表
	 * @return
	 */
	List<TskTaskStatQuery> queryCrossTeamList(String groupPermit, Date startDate,Date endDate);

	Integer queryGroupAssignTargetByIds(Integer taskId,Integer teamGroupId);

	Integer queryCrossTaskTargetById(Integer taskId);

	List<TskTaskStatQuery> queryLoanTasksList(Map<String,Object> condition);

	List<TskTaskStatQuery> queryLoanTasksListForTeam(Map<String,Object> condition);
}
