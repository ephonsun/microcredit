package banger.service.intf;

import java.util.List;
import java.util.Map;

import banger.domain.task.GroupUnsignedTaskBean;
import banger.domain.task.TskGroupTaskAssign;
import banger.domain.task.TskGroupTaskAssignQuery;
import banger.domain.task.TskTeamMemberTaskAssignQuery;

/**
 * 团队任务分配表业务访问接口
 */
public interface IGroupTaskAssignService {

	/**
	 * 新增团队任务分配表
	 * @param groupTaskAssign 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void insertGroupTaskAssign(TskGroupTaskAssign groupTaskAssign,Integer loginUserId);

	/**
	 *修改团队任务分配表
	 * @param groupTaskAssign 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updateGroupTaskAssign(TskGroupTaskAssign groupTaskAssign,Integer loginUserId);

	/**
	 * 通过主键删除团队任务分配表
	 * @param groupTaskAssignId 主键Id
	 */
	void deleteGroupTaskAssignById(Integer groupTaskAssignId);

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
	 * 根据任务查询团队任务分配表
	 * @param condition 查询条件
	 * @return
	 */
	List<TskGroupTaskAssignQuery> queryGroupTaskAssignByTaskId(Map<String,Object> condition,Integer taskId);


	/**
	 * 根据任务查询未分配团队
	 * @param condition
	 * @return
	 */
	List<GroupUnsignedTaskBean> queryUnsiginedGroupByTaskId(Map<String,Object> condition);
	
	
	/**
	 * 查询团队成员任务分配明细
	 * @param condition
	 * @return
	 */
	List<TskTeamMemberTaskAssignQuery> queryTeamMemberTaskAssignDetail(Map<String,Object> condition);
	
	
	/**
	 * 查询单个团队任务分配情况
	 * @param condition
	 * @return
	 */
	TskGroupTaskAssign queryGroupTaskAssignByTeam(Map<String,Object> condition);

}
