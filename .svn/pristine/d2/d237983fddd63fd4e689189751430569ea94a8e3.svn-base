package banger.dao.intf;

import java.util.Date;
import java.util.List;
import java.util.Map;

import banger.domain.task.GroupUnsignedTaskBean;
import banger.domain.task.TskGroupTaskAssign;
import banger.domain.task.TskGroupTaskAssignQuery;

/**
 * 团队任务分配表数据访问接口
 */
public interface IGroupTaskAssignDao {

	/**
	 * 新增团队任务分配表
	 * @param groupTaskAssign 实体对像
	 */
	void insertGroupTaskAssign(TskGroupTaskAssign groupTaskAssign);

	/**
	 *修改团队任务分配表
	 * @param groupTaskAssign 实体对像
	 */
	void updateGroupTaskAssign(TskGroupTaskAssign groupTaskAssign);

	/**
	 * 通过主键删除团队任务分配表
	 * @param groupTaskAssignId 主键Id
	 */
	void deleteGroupTaskAssignById(Integer groupTaskAssignId);

	/**
	 * 通过任务ID删除团队任务分配表
	 * @param taskId
	 */
	void deleteGroupTaskAssignByTaskId(Integer taskId);

	/**
	 * 通过主键得到团队任务分配表
	 * @param groupTaskAssignId 主键Id
	 */
	TskGroupTaskAssign getGroupTaskAssignById(Integer groupTaskAssignId);

	/**
	 * 查询团队任务分配表
	 * @param condition 查询条件
	 * @return
	 */
	List<TskGroupTaskAssign> queryGroupTaskAssignList(Map<String,Object> condition);

	/**
	 * 查询团队任务分配表
	 * @param taskId 查询条件
	 * @return
	 */
	List<TskGroupTaskAssign> getGroupTaskAssignListById(Integer taskId);

	/**
	 * 根据贷款团队ID和放款日期，得到任务团队列表
	 * @param groupId
	 * @param loanDate
	 * @return
	 */
	List<TskGroupTaskAssign> getGroupTaskAssignListByGroupIdAndDate(Integer groupId,Date loanDate,Integer taskMold);

   
	/**
	 * 根据任务查询团队任务分配表
	 * @param condition 查询条件
	 * @return
	 */
	List<TskGroupTaskAssignQuery> queryGroupTaskAssignByTaskId(Map<String,Object> condition);
	
	/**
	 * 根据任务查询分配团队
	 * @param condition
	 * @return
	 */
	List<GroupUnsignedTaskBean> queryUnsiginedGroupByTaskId(Map<String,Object> condition);
	
	/**
	 * 查询单个团队任务分配情况
	 * @param condition
	 * @return
	 */
	TskGroupTaskAssign queryGroupTaskAssignByTeam(Map<String,Object> condition);
}
